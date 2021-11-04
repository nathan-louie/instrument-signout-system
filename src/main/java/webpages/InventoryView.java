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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import tune.log.classes.Instrument;
import tune.log.classes.Teacher;
import tune.log.classes.UploadedFileReader;
import tune.log.database.Database;
import tune.log.table.Helper;
import tune.log.table.InstrumentEntry;

@Route(value = "inventory", layout = MainView.class)
@PageTitle("TuneLog | Inventory")
public class InventoryView extends HorizontalLayout implements BeforeEnterObserver
{
	private final Database database = new Database();

	public InventoryView()
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

		// Create layout for the table of instruments.
		VerticalLayout vl1 = new VerticalLayout();
		vl1.setWidth("1300px");
		vl1.setHeightFull();

		Grid<InstrumentEntry> grid = new Grid<>(InstrumentEntry.class);
		vl1.add(grid);
		// Get all the instruments from the database and format them as InstrumentEntry.
		grid.setItems(Helper.getInstrumentTable(database.getAllInstruments(), database));

		vl1.setAlignItems(Alignment.CENTER);
		vl1.setJustifyContentMode(JustifyContentMode.CENTER);

		// Create layout for the import instruments function.
		VerticalLayout vl2 = new VerticalLayout();
		vl2.setWidth("600px");
		vl2.setHeightFull();

		H2 importInstrumentLabel = new H2("Import Instruments");

		// Create the upload button.
		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		upload.setMaxFiles(1);
		upload.setDropLabel(new Label("Upload a file in .csv format"));
		upload.addSucceededListener(event ->
		{
			// Get the input stream and parse the CSV file.
			InputStream file = buffer.getInputStream();
			UploadedFileReader<Instrument> ufr = new UploadedFileReader<Instrument>(file, Instrument.class);
			List<Instrument> uploadedInstruments = ufr.parse();
			for (Instrument i : uploadedInstruments) {
				database.addInstrument(i);
			}
			// Update the table of instruments.
			grid.setItems(Helper.getInstrumentTable(database.getAllInstruments(), database));
		});

		vl2.add(importInstrumentLabel, upload);

		vl2.setAlignItems(Alignment.CENTER);
		vl2.setJustifyContentMode(JustifyContentMode.CENTER);

		// Create layout for the add and remove instruments function.
		VerticalLayout vl3 = new VerticalLayout();
		vl3.setWidth("600px");
		vl3.setHeightFull();

		H2 addInstrumentLabel = new H2("Add Instrument");

		TextField instrumentId = new TextField();
		instrumentId.setLabel("Instrument ID");
		instrumentId.setPlaceholder("ex. 123456789");

		TextField instrumentName = new TextField();
		instrumentName.setLabel("Instrument Name");
		instrumentName.setPlaceholder("ex. Clarinet-1");

		Button addInstrumentButton = new Button("Add");
		addInstrumentButton.getElement().getThemeList().add("primary");
		addInstrumentButton.addClickListener(click ->
		{
			// When the add button is clicked, add the instrument to the database and update
			// the table.
			String inputInstrumentId = instrumentId.getValue();
			String inputInstrumentName = instrumentName.getValue();
			instrumentId.clear();
			instrumentName.clear();

			try {
				database.addInstrument(new Instrument(Integer.parseInt(inputInstrumentId), inputInstrumentName, 0, -1));
				grid.setItems(Helper.getInstrumentTable(database.getAllInstruments(), database));
			} catch (NumberFormatException nfe) {
				// Catch when a number is not inputted as an instrument id.
				messageText.setText("That is not a valid instrument ID.");
				message.open();
			}
		});

		H2 removeInstrumentLabel = new H2("Remove Instrument");
		removeInstrumentLabel.getElement().getStyle().set("margin-top", "100px");

		TextField removeInstrumentId = new TextField();
		removeInstrumentId.setLabel("Instrument ID");
		removeInstrumentId.setPlaceholder("ex. 0123456789");

		Button removeInstrumentButton = new Button("Remove");
		removeInstrumentButton.getElement().getThemeList().add("primary");
		removeInstrumentButton.addClickListener(click ->
		{
			// When the remove button is clicked, remove the instrument from the database
			// and update the table.
			String inputRemoveInstrumentId = removeInstrumentId.getValue();
			removeInstrumentId.clear();

			try {
				database.deleteInstrument(database.getInstrument(Integer.parseInt(inputRemoveInstrumentId)));
				grid.setItems(Helper.getInstrumentTable(database.getAllInstruments(), database));
			} catch (NumberFormatException nfe) {
				// Catch when a number is not inputted as an instrument id.
				messageText.setText("That is not a valid instrument ID.");
				message.open();
			}
		});

		vl3.add(addInstrumentLabel, instrumentId, instrumentName, addInstrumentButton, removeInstrumentLabel,
				removeInstrumentId, removeInstrumentButton);

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
