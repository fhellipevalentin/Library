package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Books;
import model.services.BooksService;
import model.services.DepartmentService;

public class BooksListController implements Initializable, DataChangeListener {

	private BooksService service;

	@FXML
	private Button btNew;

	private ObservableList<Books> obsList;

	@FXML
	private TableView<Books> tableViewBooks;

	@FXML
	private TableColumn<Books, Integer> tableColumnId;

	@FXML
	private TableColumn<Books, String> tableColumnName;
	
	@FXML
	private TableColumn<Books, String> tableColumnGenre;
	
	@FXML
	private TableColumn<Books, String> tableColumnAuthor;
	
	@FXML
	private TableColumn<Books, Double> tableColumnMarketPrice;
	
	@FXML
	private TableColumn<Books, Date> tableColumnReleaseDate;
	
	@FXML
	private TableColumn<Books, Date> tableColumnDonateDate;

	@FXML
	private TableColumn<Books, Books> tableColumnEDIT;

	@FXML
	private TableColumn<Books, Books> tableColumnREMOVE;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Books obj = new Books();
		createDialogForm(obj, "/gui/BooksForm.fxml", parentStage);
	}

	public void setBooksService(BooksService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initalizeNodes();
	}

	private void initalizeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		tableColumnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		tableColumnMarketPrice.setCellValueFactory(new PropertyValueFactory<>("marketPrice"));
		tableColumnReleaseDate.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
		Utils.formatTableColumnDate(tableColumnReleaseDate, "dd/MM/yyyy");
		tableColumnDonateDate.setCellValueFactory(new PropertyValueFactory<>("donateDate"));
		Utils.formatTableColumnDate(tableColumnDonateDate, "dd/MM/yyyy");
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewBooks.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		List<Books> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewBooks.setItems(obsList);
		initEditButtons();
		initRemoveButtons();

	}

	private void createDialogForm(Books obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			BooksFormController controller = loader.getController();
			controller.setBooks(obj);
			controller.setServices(new BooksService(), new DepartmentService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Books data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Books, Books>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Books obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/BooksForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Books, Books>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Books obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
	
	private void removeEntity(Books obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
		
	}

}
