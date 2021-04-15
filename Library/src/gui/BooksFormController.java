package gui;


import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Books;
import model.exceptions.ValidationException;
import model.services.BooksService;

public class BooksFormController implements Initializable {
	
	private Books entity;
	
	private BooksService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtGenre;
	
	@FXML
	private TextField txtAuthor;
	
	@FXML
	private TextField txtMarketPrice;
	
	@FXML
	private DatePicker dpReleaseDate;
	
	@FXML
	private DatePicker dpDonateDate;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorGenre;
	
	@FXML
	private Label labelErrorAuthor;
	
	@FXML
	private Label labelErrorMarketPrice;
	
	@FXML
	private Label labelErrorReleaseDate;
	
	@FXML
	private Label labelErrorDonateDate;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setBooks(Books entity) {
		this.entity = entity;
	}
	
	public void setBooksService(BooksService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
		entity = getFormData();
		service.saveOrUpdate(entity);
		notifyDataChangeListeners();
		Utils.currentStage(event).close();
		}
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	private Books getFormData() {
		Books obj = new Books();
		
		ValidationException exception = new ValidationException("Validation Error");
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldMaxLength(txtGenre, 15);
		Constraints.setTextFieldMaxLength(txtAuthor, 70);
		Constraints.setTextFieldDouble(txtMarketPrice);
		Utils.formatDatePicker(dpReleaseDate, "dd/MM/yyyy");
		Utils.formatDatePicker(dpDonateDate, "dd/MM/yyyy");
	}
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtGenre.setText(entity.getGenre());
		txtAuthor.setText(entity.getAuthor());
		Locale.setDefault(Locale.US);
		txtMarketPrice.setText(String.format("%.2f",entity.getMarketPrice()));
		if (entity.getDonateDate() != null) {
		dpDonateDate.setValue(LocalDate.ofInstant(entity.getDonateDate().toInstant(), ZoneId.systemDefault()));
		}
		if (entity.getReleaseDate() != null) {
		dpReleaseDate.setValue(LocalDate.ofInstant(entity.getDonateDate().toInstant(), ZoneId.systemDefault()));
		}
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}
}
