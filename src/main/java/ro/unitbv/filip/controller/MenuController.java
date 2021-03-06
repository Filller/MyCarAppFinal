package ro.unitbv.filip.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ro.unitbv.filip.model.Car;
import ro.unitbv.filip.model.CarDAO;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    private int userId;

    @FXML
    private Label loggedInUser;

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> tableRegistrationPlate;
    @FXML
    private TableColumn<Car, String> tableBrandColumn;
    @FXML
    private TableColumn<Car, String> tableModelColumn;

    @FXML
    private TextField deleteRegistrationPlate;


    Stage dialogStage = new Stage();

    public TableView<Car> getCarTable() {
        return carTable;
    }

    public void setCarTable(TableView<Car> carTable) {
        this.carTable = carTable;
    }

    /**
     * clears and refreshes the car table either by button press or when called at the end of add and delete methods
     *
     * @param event refresh button press
     */
    @FXML
    private void refresh(ActionEvent event) {
        List<Car> carList = CarDAO.getInstance().findByOwner(getUserId());
        this.carTable.getItems().clear();

        this.carTable.getItems().addAll(carList);

    }

    /**
     * initialises the car table
     *
     * @param location url of database
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableRegistrationPlate.setCellValueFactory(new PropertyValueFactory<Car, String>("registrationPlate"));
        tableBrandColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carBrand"));
        tableModelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("carModel"));

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Label getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Label loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * login current user out, clearing the table as well as the user id;
     *
     * @param event logout button press
     * @throws IOException
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        //Cleanup
        this.carTable.getItems().clear();
        this.userId = 0;

        // Logout
        Node source = (Node) event.getSource();
        dialogStage = (Stage) source.getScene().getWindow();
        dialogStage.close();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Login.fxml")));
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.show();


    }

    /**
     * manages the setting of a new GUI window
     *
     * @param event add new car button press
     */
    @FXML
    public void add(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddMe.fxml"));
            Parent root = (Parent)loader.load();
            AddMeController addMeController= (AddMeController) loader.getController();
            addMeController.setLoggedUserId(getUserId());
            Stage stage=new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Icon2.png")));
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }


//        AddMeController addMeController= (AddMeController) loader.getController();
//        addMeController.setLoggedUserId(getUserId());
//
//        addMeController.addCar();
//        Scene scene = new Scene(root);
//        dialogStage.setScene(scene);
//        dialogStage.show();
    }

    /**
     * deletes user car by registration plate;
     *
     * @param event delete car button press
     *
     * TODO: check if empty deleteRegistrationPlate
     */
    @FXML
    public void delete(ActionEvent event) {
        if (deleteRegistrationPlate.getText() == null ||
                deleteRegistrationPlate.getText().length() == 0) {

        } else {
            CarDAO.getInstance().deleteByRegistrationPlate(deleteRegistrationPlate.getText());
            refresh(event);
        }
    }

}
