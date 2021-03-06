package ro.unitbv.filip.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import ro.unitbv.filip.model.Car;
import ro.unitbv.filip.model.CarDAO;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

public class AddMeController implements Initializable{

    @FXML
    private TextField newCarRegistrationNumber;

    @FXML
    private TextField newCarBrandName;

    @FXML
    private TextField newCarModelName;

    private int loggedUserId;

    public int getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(int loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    public AddMeController() {
    }

    /**
     * creates a car entity that will be added in the database
     *
     * TODO check if (and limit) the registration plate in already in the database
     */
    public void addCar(){
        Car car = new Car();
        car.setCarBrand(newCarBrandName.getText().toString());
        car.setCarModel(newCarModelName.getText().toString());
        car.setOwnerId(loggedUserId);
        car.setRegistrationPlate(newCarRegistrationNumber.getText().toString());
        CarDAO.getInstance().add(car);
        infoBox("Successfully added a new car", "Success", null);

    }

    /**
     * info-box logic
     */
    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
