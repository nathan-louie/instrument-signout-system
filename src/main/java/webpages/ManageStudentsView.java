package webpages;

import java.io.InputStream;
import java.util.List;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import tune.log.classes.Encryption;
import tune.log.classes.Student;
import tune.log.classes.Teacher;
import tune.log.classes.UploadedFileReader;
import tune.log.database.Database;
import tune.log.table.Helper;
import tune.log.table.StudentEntry;

@Route(value = "students", layout = MainView.class)
public class ManageStudentsView extends HorizontalLayout implements BeforeEnterObserver
{
	private final Database database = new Database();

	public ManageStudentsView()
	{
		setHeightFull();

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

		// Create layout for the table of students.
		VerticalLayout vl1 = new VerticalLayout();
		vl1.setWidth("1300px");
		vl1.setHeightFull();

		Grid<StudentEntry> grid = new Grid<>(StudentEntry.class);
		vl1.add(grid);
		// Add all students from the database to the table.
		grid.setItems(Helper.getStudentTable(database.getAllStudents()));

		vl1.setAlignItems(Alignment.CENTER);
		vl1.setJustifyContentMode(JustifyContentMode.CENTER);

		// Create layout for the import students function.
		VerticalLayout vl2 = new VerticalLayout();
		vl2.setWidth("600px");
		vl2.setHeightFull();

		H2 importStudentLabel = new H2("Import Students");

		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		upload.setMaxFiles(1);
		upload.setDropLabel(new Label("Upload a file in .csv format"));
		upload.setAcceptedFileTypes("text/csv");
		upload.addSucceededListener(event ->
		{
			// Parse the uploaded CSV file of students, and update the database and table.
			InputStream file = buffer.getInputStream();
			UploadedFileReader<Student> ufr = new UploadedFileReader<Student>(file, Student.class);
			List<Student> uploadedStudents = ufr.parse();
			for (Student s : uploadedStudents) {
				database.addStudent(s);
			}
			grid.setItems(Helper.getStudentTable(database.getAllStudents()));
		});

		vl2.add(importStudentLabel, upload);

		vl2.setAlignItems(Alignment.CENTER);
		vl2.setJustifyContentMode(JustifyContentMode.CENTER);

		// Create layout for the add and remove students function.
		VerticalLayout vl3 = new VerticalLayout();
		vl3.setWidth("600px");
		vl3.setHeightFull();

		H2 addStudentLabel = new H2("Add Student");

		TextField username = new TextField();
		username.setLabel("Student ID");
		username.setPlaceholder("ex. 335516118");

		PasswordField password = new PasswordField();
		password.setLabel("Password");
		password.setPlaceholder("ex. 123abc");

		TextField firstName = new TextField();
		firstName.setLabel("First Name");
		firstName.setPlaceholder("ex. Joe");

		TextField lastName = new TextField();
		lastName.setLabel("Last Name");
		lastName.setPlaceholder("ex. Bidet");

		Button addStudentButton = new Button("Add");
		addStudentButton.getElement().getThemeList().add("primary");
		addStudentButton.addClickListener(click ->
		{
			// Add a student to the database and table given their fields from the
			// textfields.
			String inputUsername = username.getValue();
			Encryption e = new Encryption(password.getValue());
			String inputPassword = e.encrypt();
			String inputFirstName = firstName.getValue();
			String inputLastName = lastName.getValue();
			username.clear();
			password.clear();
			firstName.clear();
			lastName.clear();

			try {
				database.addStudent(
						new Student(Integer.parseInt(inputUsername), inputPassword, inputFirstName, inputLastName));
				grid.setItems(Helper.getStudentTable(database.getAllStudents()));
			} catch (NumberFormatException nfe) {
				// Catch when a number is not inputted as a student id.
				messageText.setText("That is not a valid student ID.");
				message.open();
			}
		});

		H2 removeStudentLabel = new H2("Remove Student");
		removeStudentLabel.getElement().getStyle().set("margin-top", "100px");

		TextField removeUsername = new TextField();
		removeUsername.setLabel("Student ID");
		removeUsername.setPlaceholder("ex. 335516118");

		Button removeStudentButton = new Button("Remove");
		removeStudentButton.getElement().getThemeList().add("primary");
		removeStudentButton.addClickListener(click ->
		{
			// Remove a student from the database and table.
			String inputRemoveUsername = removeUsername.getValue();
			removeUsername.clear();

			try {
				database.deleteStudent(database.getStudent(Integer.parseInt(inputRemoveUsername)));
				grid.setItems(Helper.getStudentTable(database.getAllStudents()));
			} catch (NumberFormatException nfe) {
				// Catch when a number is not inputted as a student id.
				messageText.setText("That is not a valid student ID.");
				message.open();
			}
		});

		vl3.add(addStudentLabel, username, password, firstName, lastName, addStudentButton, removeStudentLabel,
				removeUsername, removeStudentButton);

		vl3.setAlignItems(Alignment.CENTER);
		vl3.setJustifyContentMode(JustifyContentMode.CENTER);

		add(vl1, vl2, vl3);
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
