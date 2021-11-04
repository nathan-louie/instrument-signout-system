package webpages;

import java.util.ArrayList;
import java.util.Collections;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import tune.log.classes.Teacher;
import tune.log.database.Database;
import tune.log.table.EventEntry;
import tune.log.table.Helper;

@Route(value = "history", layout = MainView.class)
@PageTitle("TuneLog | History")
public class HistoryView extends VerticalLayout implements BeforeEnterObserver
{
	private final Database database = new Database();

	public HistoryView()
	{
		// Center all components in the view.
		setHeightFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		// Add table of the history.
		Grid<EventEntry> grid = new Grid<>(EventEntry.class);
		add(grid);
		ArrayList<EventEntry> temp = Helper.getEventsTable(database.getAllEvents(), database);
		Collections.reverse(temp);
		grid.setItems(temp);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event)
	{
		// Check whether the user is logged in as a teacher.
		if (ComponentUtil.getData(UI.getCurrent(), Teacher.class) == null) {
			event.rerouteTo(LoginView.class);
		}
	}
}
