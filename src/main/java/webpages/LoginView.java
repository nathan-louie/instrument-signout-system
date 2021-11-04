package webpages;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import tune.log.classes.Encryption;
import tune.log.classes.Student;
import tune.log.classes.Teacher;
import tune.log.database.Database;

@PWA(name = "Tune Log by RPS Enterprise", shortName = "Tune Log", description = "Tune Log by RPS Enterprise is a modern webapp for the SVS Music department to manage students signing out instruments.")
@Route(value = "")
@Theme(value = Material.class, variant = Material.DARK)
@PageTitle("TuneLog | Login")
public class LoginView extends VerticalLayout implements PageConfigurator
{
	private final Database database = new Database();

	public LoginView()
	{
		// Create image.
		Image logo = new Image("https://i.imgur.com/qp1H3yT.png", "TuneLog Logo");
		logo.setHeight("200px");

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

		// Create dropdown select for student or teacher.
		Select<String> userSelect = new Select<>();
		userSelect.setItems("Student", "Teacher");
		userSelect.setValue("Student");

		// Create input field for username.
		TextField username = new TextField();
		username.setLabel("Username");
		username.setPlaceholder("ex. 335516118");

		// Create password field for password.
		PasswordField password = new PasswordField();
		password.setLabel("Password");
		password.setPlaceholder("ex. 123abc");

		// Create login button.
		Button login = new Button("Login");
		login.getElement().getThemeList().add("primary");
		login.addClickShortcut(Key.ENTER);
		login.addClickListener(click ->
		{
			// When the login button is clicked, check the user's login credentials.
			String inputUsername = username.getValue();
			Encryption e = new Encryption(password.getValue());
			String inputPassword = e.encrypt();
			username.clear();
			password.clear();

			String user = userSelect.getValue();

			// Check if the login credentials are valid for a student, and catch when a
			// number is not inputted for the student id.
			if (user.equals("Student")) {
				try {
					Student student = database.getStudent(Integer.parseInt(inputUsername));
					// Check if the classroom is closed.
					if (database.getStatus().getStatus() == 0) {
						messageText.setText("Classroom is closed. Try again later.");
						message.open();
					}
					// Check the login credentials.
					else if (student != null
							&& student.equals(new Student(Integer.parseInt(inputUsername), inputPassword))) {
						// Set the current user as the student.
						ComponentUtil.setData(UI.getCurrent(), Student.class, student);
						UI.getCurrent().navigate(StudentView.class);
					} else {
						messageText.setText("Your username or password is incorrect. Please try again.");
						message.open();
					}
				} catch (NumberFormatException nfe) {
					messageText.setText("That is not a valid student ID.");
					message.open();
				}
			} else {
				Teacher teacher = database.getTeacher(inputUsername);
				// Check the login credentials.
				if (teacher != null && teacher.equals(new Teacher(inputUsername, inputPassword))) {
					// Set the current user as the teacher.
					ComponentUtil.setData(UI.getCurrent(), Teacher.class, teacher);
					UI.getCurrent().navigate(InventoryView.class);
				} else {
					messageText.setText("Your username or password is incorrect. Please try again.");
					message.open();
				}
			}
		});

		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		add(logo, userSelect, username, password, login);
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
