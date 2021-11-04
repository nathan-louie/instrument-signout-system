package webpages;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import tune.log.classes.Encryption;
import tune.log.classes.Teacher;
import tune.log.database.Database;
import tune.log.table.Helper;
import tune.log.table.TeacherEntry;

@Route(value = "teachers", layout = MainView.class)
@PageTitle("TuneLog | Teachers")
public class ManageTeachersView extends HorizontalLayout implements BeforeEnterObserver
{
	private final Database database = new Database();

	public ManageTeachersView()
	{
		setHeightFull();

		// Create layout for the table of teachers.
		VerticalLayout vl1 = new VerticalLayout();
		vl1.setWidth("1000px");
		vl1.setHeightFull();

		Grid<TeacherEntry> grid = new Grid<>(TeacherEntry.class);
		vl1.add(grid);
		// Get all teachers from the database and set the table.
		grid.setItems(Helper.getTeacherTable(database.getAllTeachers()));

		vl1.setAlignItems(Alignment.CENTER);
		vl1.setJustifyContentMode(JustifyContentMode.CENTER);

		// Create layout for the add and remove teachers function.
		VerticalLayout vl2 = new VerticalLayout();
		vl2.setWidth("600px");
		vl2.setHeightFull();

		H2 addTeacherLabel = new H2("Add Teacher");

		TextField username = new TextField();
		username.setLabel("Username");
		username.setPlaceholder("ex. michael.roy-diclemente");

		PasswordField password = new PasswordField();
		password.setLabel("Password");
		password.setPlaceholder("ex. 123abc");

		TextField firstName = new TextField();
		firstName.setLabel("First Name");
		firstName.setPlaceholder("ex. Michael");

		TextField lastName = new TextField();
		lastName.setLabel("Last Name");
		lastName.setPlaceholder("ex. Roy-DiClemente");

		Button addTeacherButton = new Button("Add");
		addTeacherButton.getElement().getThemeList().add("primary");
		addTeacherButton.addClickListener(click ->
		{
			// Add a teacher to the database given their fields from the textfields, and
			// update the table.
			String inputUsername = username.getValue();
			Encryption e = new Encryption(password.getValue());
			String inputPassword = e.encrypt();
			String inputFirstName = firstName.getValue();
			String inputLastName = lastName.getValue();
			username.clear();
			password.clear();
			firstName.clear();
			lastName.clear();

			database.addTeacher(new Teacher(inputUsername, inputPassword, inputFirstName, inputLastName));
			grid.setItems(Helper.getTeacherTable(database.getAllTeachers()));
		});

		H2 removeTeacherLabel = new H2("Remove Teacher");
		removeTeacherLabel.getElement().getStyle().set("margin-top", "100px");

		TextField removeUsername = new TextField();
		removeUsername.setLabel("Username");
		removeUsername.setPlaceholder("ex. michael.roy-diclemente");

		Button removeTeacherButton = new Button("Remove");
		removeTeacherButton.getElement().getThemeList().add("primary");
		removeTeacherButton.addClickListener(click ->
		{
			// Remove a teacher from the database and update the table.
			String inputRemoveUsername = removeUsername.getValue();
			removeUsername.clear();

			database.deleteTeacher(database.getTeacher(inputRemoveUsername));
			grid.setItems(Helper.getTeacherTable(database.getAllTeachers()));
		});

		vl2.add(addTeacherLabel, username, password, firstName, lastName, addTeacherButton, removeTeacherLabel,
				removeUsername, removeTeacherButton);

		vl2.setAlignItems(Alignment.CENTER);
		vl2.setJustifyContentMode(JustifyContentMode.CENTER);

		add(vl1, vl2);
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
