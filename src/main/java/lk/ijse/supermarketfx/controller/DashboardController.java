package lk.ijse.supermarketfx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public AnchorPane ancMainContainer;
    public void btnCustomerOnAction(ActionEvent actionEvent)  {
             navigateto("/view/CustomerPage.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           navigateto("/view/CustomerPage.fxml");
    }

    public void btnItemOnAction(ActionEvent actionEvent) {
        navigateto("/view/ItemPage.fxml");
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
        navigateto("/view/OrderPage.fxml");
    }

    private void navigateto(String path) {
        try {
            ancMainContainer.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());
            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());

            ancMainContainer.getChildren().add(anchorPane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Page not found");
            e.printStackTrace();
        }
    }


}
