package ro.unitbv.filip.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.unitbv.filip.model.Car;
import ro.unitbv.filip.model.CarDAO;
import ro.unitbv.filip.model.User;
import ro.unitbv.filip.model.UserDAO;

public class UserController implements Initializable {
    @FXML
    private TextField textUser;

    @FXML
    private PasswordField textPassword;


    Stage dialogStage = new Stage();
    Scene scene;


    public UserController(){

    }



    @FXML
    private void handleLoginButtonAction(ActionEvent event) {

        String username = textUser.getText().toString();
        String password = textPassword.getText().toString();



        try {
            List<User> users = UserDAO.getInstance().findByName(username);

            if (users.isEmpty()) {
                infoBox("User not found", "Failed", null);
            } else {
                if (users.get(0).getUserPassword().equals(password)) {
                    User loggedInUser = users.get(0);

                    infoBox("Login Successfull", "Success", null);
                    Node source = (Node) event.getSource();
                    dialogStage = (Stage) source.getScene().getWindow();
                    dialogStage.close();

                    // Load view
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                    Parent root = loader.load();

                    // Populate User cars
                    List<Car> carList=CarDAO.getInstance().findByOwner(loggedInUser.getUserId());
                    MenuController menuController= (MenuController) loader.getController();
                    menuController.setUserId(users.get(0).getUserId());
                    menuController.getCarTable().getItems().addAll(carList);

                    // Populate username
                    menuController.getLoggedInUser().setText(
                            String.format("Logged-in User:%s",
                                    loggedInUser.getUserName()));

                    // Show
                    scene = new Scene(root);
                    dialogStage.setScene(scene);
                    dialogStage.show();

                } else {
                    infoBox("Enter a Correct Username and Password", "Failed", null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public TableView<Car> mapCarListToTableView (List<Car> carList){
        TableView<Car> carTableView=new TableView();
        carTableView.setItems(FXCollections.observableList(carList));
        return carTableView;
    }


}
