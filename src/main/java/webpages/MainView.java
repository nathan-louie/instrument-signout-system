package webpages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import tune.log.classes.Event;
import tune.log.classes.Status;
import tune.log.classes.Teacher;
import tune.log.database.Database;
import tune.log.table.EventSummary;
import tune.log.table.Helper;

@Route(value = "mainview")
@Theme(value = Material.class, variant = Material.DARK)
public class MainView extends AppLayout implements BeforeEnterObserver, PageConfigurator
{
	private Tabs views = new Tabs();
	private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();
	private final Database database = new Database();
	private Dialog d = new Dialog();

	public MainView()
	{
		// Create closing summary dialog box.
		d.setWidthFull();
		d.setHeightFull();

		VerticalLayout vl = new VerticalLayout();

		Label messageText = new Label("Closing Summary");
		messageText.getElement().getStyle().set("text-align", "center");

		Grid<EventSummary> list = new Grid<>(EventSummary.class);
		list.setHeightFull();
		list.setWidthFull();

		Button confirmButton = new Button("Close", click ->
		{
			d.close();
		});

		vl.add(messageText, list, confirmButton);

		vl.setSizeFull();
		vl.setAlignItems(Alignment.CENTER);
		vl.setJustifyContentMode(JustifyContentMode.CENTER);

		d.add(vl);

		// Create image logo for navbar.
		Image logo = new Image("https://i.imgur.com/qp1H3yT.png", "TuneLog Logo");
		logo.setHeight("70px");
		logo.getElement().getStyle().set("padding-left", "16px");

		// Add the views to the navbar.
		addMenuTab(views, "Students", ManageStudentsView.class);
		addMenuTab(views, "Teachers", ManageTeachersView.class);
		addMenuTab(views, "Inventory", InventoryView.class);
		addMenuTab(views, "History", HistoryView.class);
		views.setOrientation(Tabs.Orientation.HORIZONTAL);
		views.getElement().getStyle().set("margin-right", "auto");

		// Create button to open/close the class.
		Status status = database.getStatus();
		Button openClass = (status.getStatus() == 0) ? new Button("Open Class") : new Button("Close Class");
		openClass.getElement().getStyle().set("margin-left", "auto");
		openClass.getElement().getThemeList().add("primary");
		openClass.addClickListener(click ->
		{
			// Toggle the classroom state.
			if (status.getStatus() == 0) {
				// Add the open classroom action to the history.
				database.addEvent(new Event(LocalDateTime.now(), -1, -1, 3));
				openClass.setText("Close Class");
				status.setStatus(1);
			} else {
				// Set table's elements to the activity from the history from the most recent
				// open classroom action.
				list.setItems(getActivity(database.getAllEvents()));
				d.open();
				// Add the close classroom action to the history.
				database.addEvent(new Event(LocalDateTime.now(), -1, -1, 2));
				openClass.setText("Open Class");
				status.setStatus(0);
			}
			database.updateStatus(status);
		});

		// Create logout button at top right of navbar.
		Button logout = new Button("Logout");
		logout.getElement().getThemeList().add("primary");
		logout.getElement().getStyle().set("margin-left", "16px");
		logout.addClickListener(click ->
		{
			// Logout the teacher, set the current user to null.
			ComponentUtil.setData(UI.getCurrent(), Teacher.class, null);
			UI.getCurrent().navigate(LoginView.class);
		});

		addToNavbar(logo, views, openClass, logout);
	}

	private void addMenuTab(Tabs tabs, String label, Class<? extends Component> target)
	{
		Tab tab = new Tab(new RouterLink(label, target));
		navigationTargetToTab.put(target, tab);
		tabs.add(tab);
	}

	private ArrayList<EventSummary> getActivity(ArrayList<Event> list)
	{
		ArrayList<Event> build = new ArrayList<Event>();

		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i).getType() != 3) {
				build.add(list.get(i));
			} else {
				break;
			}
		}

		return Helper.getEventSummaryTable(Helper.getEventsTable(build, database));
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		// Check whether the user is logged in as a teacher.
		if (ComponentUtil.getData(UI.getCurrent(), Teacher.class) == null) {
			event.rerouteTo(LoginView.class);
		}
		views.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
	}

	@Override
	public void configurePage(InitialPageSettings initialPageSettings)
	{
		// Open Graph Tags
		initialPageSettings.addMetaTag("og:title", "Tune Log");
		initialPageSettings.addMetaTag("og:type", "website");
		initialPageSettings.addMetaTag("og:locale", "en_CA");
		initialPageSettings.addMetaTag("og:site_name", "Tune Log by RPS Enterprise");
		initialPageSettings.addMetaTag("og:url", "https://tunelog.tech");
		initialPageSettings.addMetaTag("og:description",
				"Tune Log by RPS Enterprise is a modern webapp for the SVS Music department to manage students signing out instruments.");

		// Twitter Cards
		initialPageSettings.addMetaTag("twitter:card", "summary");
		initialPageSettings.addMetaTag("twitter:title", "Tune Log by RPS Enterprise");
		initialPageSettings.addMetaTag("twitter:description",
				"Tune Log by RPS Enterprise is a modern webapp for the SVS Music department to manage students signing out instruments.");
		initialPageSettings.addMetaTag("twitter:url", "https://tunelog.tech");
	}
}
