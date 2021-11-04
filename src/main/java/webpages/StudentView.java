package webpages;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import tune.log.classes.Event;
import tune.log.classes.Instrument;
import tune.log.classes.Student;
import tune.log.database.Database;

@Route(value = "student")
@Theme(value = Material.class, variant = Material.DARK)
@PageTitle("TuneLog | Student Check-In/Out")
public class StudentView extends VerticalLayout implements BeforeEnterObserver
{
	private final Database database = new Database();

	// Helper class for creating the navbar layout.
	private class BaseLayout extends AppLayout implements BeforeEnterObserver, PageConfigurator
	{
		private Tabs views = new Tabs();
		private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();

		public BaseLayout()
		{
			// Add the logo to the topleft of the navbar.
			Image logo = new Image("https://i.imgur.com/qp1H3yT.png", "TuneLog Logo");
			logo.setHeight("70px");
			logo.getElement().getStyle().set("padding-left", "16px");

			// Add a logout button to the topright of the navbar.
			Button logout = new Button("Logout");
			logout.getElement().getThemeList().add("primary");
			logout.getElement().getStyle().set("margin-left", "auto");
			logout.addClickListener(click ->
			{
				// Logout from the student view, set the current user to null.
				ComponentUtil.setData(UI.getCurrent(), Student.class, null);
				UI.getCurrent().navigate(LoginView.class);
			});

			addToNavbar(logo, logout);
		}

		@Override
		public void beforeEnter(BeforeEnterEvent event)
		{
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

	public StudentView()
	{
		BaseLayout bl = new BaseLayout();

		// Create layout for the check-in/out buttons and textfield for instrument id.
		VerticalLayout hl = new VerticalLayout();
		hl.setSizeFull();
		hl.setAlignItems(Alignment.CENTER);
		hl.setJustifyContentMode(JustifyContentMode.CENTER);

		TextField instrumentId = new TextField();
		instrumentId.setLabel("Instrument Id");
		instrumentId.setPlaceholder("ex. 123456789");
		instrumentId.getElement().getStyle().set("margin", "auto");

		// Checkin the current instrument given by the textfield.
		Button checkIn = new Button("Check In");
		checkIn.getElement().getThemeList().add("primary");
		checkIn.addClickListener(click ->
		{
			updateInstrument(instrumentId, 0);
		});
		checkIn.getElement().getStyle().set("margin-bottom", "2em");

		// Checkout the current instrument given by the textfield.
		Button checkOut = new Button("Check Out");
		checkOut.getElement().getThemeList().add("primary");
		checkOut.addClickListener(click ->
		{
			updateInstrument(instrumentId, 1);
		});
		checkOut.getElement().getStyle().set("margin-top", "4.5em");

		hl.add(checkIn, instrumentId, checkOut);
		hl.getElement().getStyle().set("margin-top", "17em");
		hl.getElement().getStyle().set("margin-bottom", "auto");

		add(bl, hl);
	}

	private void updateInstrument(TextField instrumentId, int newStatus)
	{
		// Create dialog box for invalid inputs.
		Dialog message = new Dialog();
		message.setWidth("400px");
		message.setHeight("150px");

		VerticalLayout vl = new VerticalLayout();
		Label messageText = new Label();
		messageText.getElement().getStyle().set("text-align", "center");
		Button close = new Button("Close", click ->
		{
			message.close();
		});

		vl.add(messageText, close);

		vl.setSizeFull();
		vl.setAlignItems(Alignment.CENTER);
		vl.setJustifyContentMode(JustifyContentMode.CENTER);

		message.add(vl);

		// Get the instrument id from the textfield.
		String inputInstrumentId = instrumentId.getValue();
		instrumentId.clear();

		try {
			// Double check that the classroom is closed.
			if (database.getStatus().getStatus() == 0) {
				messageText.setText("The classroom is closed. Please see your teacher if you think there is an issue.");
				message.open();
			} else {
				// Update the instrument in the database, adding the action to the history.
				Instrument i = database.getInstrument(Integer.parseInt(inputInstrumentId));
				if (i.getStatus() == 0 && newStatus == 1 || i.getStatus() == 1 && newStatus == 0) {
					Student s = ComponentUtil.getData(UI.getCurrent(), Student.class);
					i.setStatus(newStatus);
					i.setCurrentUser(s.getStudentId());
					database.updateInstrument(i);
					database.addEvent(new Event(LocalDateTime.now(), Integer.parseInt(inputInstrumentId),
							s.getStudentId(), newStatus));
				} else {
					// Provide an error dialog message if the instrument is already checked in/out.
					messageText.setText("That instrument is already checked " + (newStatus == 0 ? "in." : "out."));
					message.open();
				}
			}
		} catch (NumberFormatException e) {
			// Catch when a number is not inputted as an instrument id.
			messageText.setText("That is not a valid Instrument ID.");
			message.open();
		} catch (NullPointerException e) {
			// Catch when an instrument id is not found in the database.
			messageText.setText("Instrument not found.");
			message.open();
		}
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		// Check whether the user is logged in as a teacher.
		if (ComponentUtil.getData(UI.getCurrent(), Student.class) == null) {
			event.rerouteTo(LoginView.class);
		}
	}
}
