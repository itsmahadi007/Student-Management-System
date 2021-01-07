
/**
 * Testing table
 * Separate
 */
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import car.Car;
import car.CarException;
import car.CarStatus;
import helper.CarFile;
import helper.Helper;
import javafx.application.Application;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ListPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

public class TableExample extends Application {

	Text availableCarLabel = new Text("Number of available cars: ");
	Label bookedCarLabel = new Label("Number of booked cars: ");
	Label occupiedCarLabel = new Label("Number of occupied cars: ");

	private final TableView<Car> table = new TableView<>();
	private ObservableList<Car> data = FXCollections.observableArrayList();
	final HBox hb = new HBox();
	final ObservableList<String> option = FXCollections.observableArrayList("Black", "White", "Red", "Green", "Blue");
	// thread needed boolean
	boolean b;
	Button display = new Button("Display");
	final Button book = new Button("Book");
	// delete
	static int num = 1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws ClassNotFoundException, CarException {
		Scene scene = new Scene(new Group());
		stage.setTitle("Table View Sample");
		stage.setWidth(700);
		stage.setHeight(680);

		// set dispaly and book buttons to be disabled
		display.disableProperty().set(true);
		book.disableProperty().set(true);

		final Label label = new Label("Address Book");
		label.setFont(new Font("Arial", 20));
		table.setEditable(true);
		// TableFile.read(data);

		TableColumn<Car, String> regNumCol = new TableColumn<>("Registration Number");
		regNumCol.setCellValueFactory(new PropertyValueFactory<>("registrationNum"));
		regNumCol.setPrefWidth(150);
		TableColumn<Car, String> firstNameCol = new TableColumn<>("Description");
		firstNameCol.setMinWidth(150);
		// firstNameCol.setCellValueFactory(
		// new PropertyValueFactory<>("description"));
		firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setOnEditCommit((CellEditEvent<Car, String> t) -> {
			((Car) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDescription(t.getNewValue());
		});

		TableColumn<Car, String> lastNameCol = new TableColumn<>("Colour");
		lastNameCol.setMinWidth(70);
		lastNameCol.setPrefWidth(80);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("colour"));
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		ComboBox<String> combo = new ComboBox<>();
		combo.setPromptText("Choose colour");
		combo.setItems(option);
		// lastNameCol.setOnEditCommit(
		// (CellEditEvent<Car, String> t) -> {
		// ((Car) t.getTableView().getItems().get(
		// t.getTablePosition().getRow())
		// ).setColour(t.getNewValue());
		// });
		lastNameCol.setCellFactory(ComboBoxTableCell.forTableColumn(option));
		lastNameCol.setOnEditCommit(e -> {
			Car tempC = table.getSelectionModel().getSelectedItem();
			System.out.println(e.getNewValue());
			String oldColour = tempC.getColour();
			tempC.setColour(e.getNewValue());
			tempC.addMessage(tempC.getNum() + ". This car's colour changed from " + oldColour + " to " + e.getNewValue()
					+ " at " + Helper.getTime());
		});

		// number
		// TableColumn<Car, Number> emailCol = new TableColumn<>("Price");
		// emailCol.setMinWidth(140);
		// emailCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		// emailCol.setCellFactory(TextFieldTableCell.forTableColumn(new
		// NumberStringConverter()));
		// emailCol.setOnEditCommit((CellEditEvent<Car, Number> t) -> {
		// ((Car)
		// t.getTableView().getItems().get(t.getTablePosition().getRow())).setPrice((double)
		// t.getNewValue());
		// });

		TableColumn<Car, Double> emailCol = new TableColumn<>("Price");
		emailCol.setMinWidth(150);
		emailCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		emailCol.setOnEditCommit((CellEditEvent<Car, Double> t) -> {
			// ((Car)
			// t.getTableView().getItems().get(t.getTablePosition().getRow())).setPrice((double)
			// t.getNewValue());
			try {
				Car tempC = t.getTableView().getItems().get(t.getTablePosition().getRow());
				Car tempC2 = t.getRowValue();
				System.out.println("First: " + tempC);
				System.out.println();
				System.out.println("Second: " + tempC2);
				double d = t.getNewValue();
				if (tempC2.getPrice() == d)
					throw new CarException("New value cannot be the same as old value");
				tempC2.addMessage(tempC2.getNum() + ". This car's price changed from " + tempC2.getPrice() + " to " + d
						+ " at " + Helper.getTime());
				tempC2.setPrice(d);
				System.out.println("Second after: " + tempC2);
				System.out.println();
				Car tempC3 = table.getSelectionModel().getSelectedItem();
				System.out.println("Third: " + tempC3);
				table.refresh();

			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setContentText("Only input numbers");
				alert.show();
			} catch (CarException e1) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText(null);
				alert.setContentText(e1.getMessage());
				alert.show();
			}
		});

		TableColumn<Car, String> rentCol = new TableColumn<>("Rent Date");
//		rentCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFromDate()));

		table.setItems(data);
		table.getColumns().addAll(regNumCol, firstNameCol, lastNameCol, emailCol, rentCol);

		final TextField regNum = new TextField();
		regNum.setPromptText("Registration Number");
		final TextField addFirstName = new TextField();
		addFirstName.setPromptText("Description");
		addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
		final TextField addLastName = new TextField();
		addLastName.setMaxWidth(lastNameCol.getPrefWidth());
		addLastName.setPromptText("Last Name");
		final TextField addEmail = new TextField();
		addEmail.setMaxWidth(emailCol.getPrefWidth());
		addEmail.setPromptText("Email");

		// table.getSelectionModel().getSelectedCells().forEach(System.out::println);
		// table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		final Button addButton = new Button("Add");
		addButton.setOnAction((ActionEvent e) -> {
			String regN = regNum.getText();
			String des = addFirstName.getText();
			String colour = combo.getSelectionModel().getSelectedItem();
			double price = Double.parseDouble(addEmail.getText());
			try {
				Car c = new Car(regN, des, colour, price);
				data.add(c);
//				display.disableProperty().set(false);
				regNum.clear();
				addFirstName.clear();
				combo.getSelectionModel().clearSelection();
				addEmail.clear();
			} catch (CarException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button save = new Button("Save");
		save.setOnAction(e -> {

		});
		Button load = new Button("Load");
		load.setOnAction(e -> {

		});
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				data.removeAll(table.getSelectionModel().getSelectedItem());
				// table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
				// .getSelectedItemse() - select multiple items
				table.getSelectionModel().clearSelection();
				// clear picks
			}

		});
		
		// 2008	
		table.getSelectionModel().selectedItemProperty().addListener((l,o,n) -> {
//			if(table.getSelectionModel().isEmpty()) {
//				book.disableProperty().set(true);
//			}
//			else {
//				book.disableProperty().set(false);
//			}
			if(l.getValue() == null) {
				book.disableProperty().set(true);
			}
			else {
				book.disableProperty().set(false);
			}
		});
		

		
		display.disableProperty().bind(Bindings.isEmpty(data));
		
		
		
		
		
		
		// System.out.println(display.disabledProperty().getValue());
		display.setOnAction(e -> {
			if (table.getSelectionModel().isEmpty()) {

				System.out.println(data);
			} else {
				int index = table.getSelectionModel().getFocusedIndex();
				System.out.println(data.get(index));
			}
		});

		book.setOnAction(e -> {
			// Car tempC = table.getSelectionModel().getSelectedItem();
			int index = table.getSelectionModel().getSelectedIndex();
			// Car tempC2 = data.get(index);
			System.out.println(index);

//			book.showStage(table);
			// System.out.println(tempC);
			// System.out.println("Using index: "+tempC2);
			// tempC.setStatus(CarStatus.BOOKED);

		});
		hb.getChildren().addAll(regNum, addFirstName, combo, addEmail, addButton, deleteButton);
		hb.setSpacing(3);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));

		final Button showCustomer = new Button("Show Customer Name");
		showCustomer.setOnAction(e -> {
//			System.out.println(table.getSelectionModel().getSelectedItem().getCustomerName());
		});
		

		// insert new button
		Button addImage = new Button("Add Image");
		addImage.setOnAction(e -> {
			Stage secondaryStage = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open");
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			File file = fileChooser.showOpenDialog(secondaryStage);
			if(file != null) {
				try(DataInputStream input = new DataInputStream(new FileInputStream(file))){
					File location = new File("src/image/a.jpg");
					Files.copy(input, location.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		final HBox buttons = new HBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(save, load, display, book, showCustomer, addImage);
		vbox.getChildren().addAll(availableCarLabel, bookedCarLabel, occupiedCarLabel, label, table, hb, buttons);
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		// scene.setOnMouseEntered(e -> {
		// b = data.isEmpty();
		// if(b) {
		// display.disableProperty().set(true);
		// }
		// else {
		// display.disableProperty().set(false);
		// }
		// });
		// replacement
		// scene.setOnMouseMoved(e -> {
		// b = data.isEmpty();
		// if(b) {
		// display.disableProperty().set(true);
		// }
		// else {
		// display.disableProperty().set(false);
		// }
		// if(table.getSelectionModel().isEmpty()) {
		// book.disableProperty().set(true);
		// }
		// else {
		// book.disableProperty().set(false);
		// }
		//// int a =0;
		//// for(int i=0; i< data.size(); i++) {
		//// if(data.get(i).getStatus() == CarStatus.AVAILALBE) {
		//// a++;
		//// }
		//// }
		//// availableCarLabel.setText("Number of available cars: "+a);
		// });
		stage.setScene(scene);
		stage.show();
		// check();
		// checkSelected();
		// checkStatus(stage);
		// checkStatus();
		populateTable();
	}

	// to chech different status of cars
	// private void checkStatus(Stage stage) {
	// Thread thread = new Thread(new Task<Void>() {
	//
	// @Override
	// protected Void call() throws Exception {
	// // TODO Auto-generated method stub
	// while(true) {
	// try {
	//
	// Platform.runLater(new Runnable() {
	// @Override public void run() {
	// int a = getA();
	// availableCarLabel.setText("Number of available cars: "+a);
	// }
	// });
	//
	//// bookedCarLabel
	//// occupiedCarLabel
	// Thread.sleep(300);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// });
	// thread.start();
	// }

	// private void checkStatus() {
	// Thread thread = new Thread(new Task<Void>() {
	//
	// @Override
	// protected Void call() throws Exception {
	// // TODO Auto-generated method stub
	// while (true) {
	// int a = getA();
	// availableCarLabel.setText("Number of available cars: "+a);
	//// rentCol.setCellValueFactory(cellData -> new
	//// SimpleStringProperty(cellData.getValue().getFromDate()));
	//// table.refresh();
	// Thread.sleep(400);
	// }
	// }
	// });
	// thread.setPriority(Thread.MIN_PRIORITY);
	// thread.start();
	// }

	private int getA() {
		int a = 0;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getStatus() == CarStatus.AVAILALBE) {
				a++;
			}
		}
		return a;
	}

	private void populateTable() throws CarException {
		for (int i = 0; i < 5; i++) {
			Car car = new Car("a" + i + "999999", i + "des", "any", i);
			data.add(car);
		}
	}

	// works
	// private void check() {
	//
	// Thread thread = new Thread(new Task<Void>() {
	//
	// @Override
	// protected Void call() throws Exception {
	// // TODO Auto-generated method stub
	// while(true) {
	//
	// b = data.isEmpty();
	// if(b) {
	// display.disableProperty().set(true);
	// }
	// else {
	// display.disableProperty().set(false);
	// }
	// }
	// }
	//
	// });
	// thread.setPriority(Thread.MIN_PRIORITY);
	// thread.start();
	// }

	// private void checkSelected() {
	// Thread thread = new Thread(new Task<Void>() {
	//
	// @Override
	// protected Void call() throws Exception {
	// // TODO Auto-generated method stub
	// while(true) {
	// if(table.getSelectionModel().isEmpty()) {
	// book.disableProperty().set(true);
	// }
	// else {
	// book.disableProperty().set(false);
	// }
	//
	// }
	// }
	//
	// });
	// thread.start();
	// }
}