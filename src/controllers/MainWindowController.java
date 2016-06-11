package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * Created by Michal on 2016-06-10.
 */
public class MainWindowController implements Initializable{

    private final ObservableList<String> allProviders =
            FXCollections.observableArrayList(
                    "Provider 1",
                    "Provider 2",
                    "Provider 3",
                    "Provider 4",
                    "Provider 5"
            );

    private final ObservableList<String> allCriteria =
            FXCollections.observableArrayList(
                    "Cena",
                    "Anonimowość",
                    "Możliwości",
                    "Szyfrowanie",
                    "Dostępność"
            );

    @FXML
    private Button categoryAdd1;
    @FXML
    private Button categoryAdd2;
    @FXML
    private Button categoryRemove1;
    @FXML
    private Button categoryRemove2;
    @FXML
    private ComboBox provider1;
    @FXML
    private ComboBox provider2;
    @FXML
    private ComboBox provider3;
    @FXML
    private ComboBox category1;
    @FXML
    private ComboBox category2;
    @FXML
    private ComboBox category3;
    @FXML
    private ComboBox category4;
    @FXML
    private ComboBox category5;
    @FXML
    private Button mainNaviButton;

    @FXML
    private void handleCategoryAddButton(ActionEvent event){
        switch (((Button)event.getSource()).getId()) {
            case "categoryAdd1":
                category4.setVisible(true);
                categoryAdd2.setVisible(true);
                categoryAdd1.setVisible(false);
                categoryRemove1.setVisible(true);
                break;
            case "categoryAdd2":
                category5.setVisible(true);
                categoryAdd2.setVisible(false);
                categoryRemove1.setVisible(false);
                categoryRemove2.setVisible(true);
                break;
            default:
                break;
        }
    }

    @FXML
    private void fillProviders(Event event) {
        ((ComboBox)event.getSource()).getItems().setAll(allProviders);
    }

    @FXML
    private void fillCriteria(Event event) {
        ((ComboBox)event.getSource()).getItems().setAll(allCriteria);
    }

    @FXML
    private void handleCategoryRemove(ActionEvent event) {
        switch (((Button)event.getSource()).getId()) {
            case "categoryRemove1":
                category4.setVisible(false);
                categoryAdd2.setVisible(false);
                categoryAdd1.setVisible(true);
                categoryRemove1.setVisible(false);
                break;
            case "categoryRemove2":
                category5.setVisible(false);
                categoryAdd2.setVisible(true);
                categoryRemove2.setVisible(false);
                categoryRemove1.setVisible(true);
                break;
            default:
                break;
        }
    }

    @FXML
    private void goToNextScene(ActionEvent event) throws IOException {

        if(checkProviders() && checkCriteria()){

            Stage stage = (Stage) mainNaviButton.getScene().getWindow();;
            Parent root = FXMLLoader.load(getClass().getResource("../views/warianty.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else{

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ostrzeżenie");
            alert.setHeaderText("Nie można wykonać żądanej operacji.");
            alert.setContentText("Upewnij się, że wszystkie parametry zostały odpowiednio dobrane. Pamiętaj, że zarówno dostawcy jak i kryteria nie mogą się powtarzać.");

            alert.showAndWait();

        }
//        else{
//            stage=(Stage) mainNaviButton.getScene().getWindow();
//            root = FXMLLoader.load(getClass().getResource("../views/main2.fxml"));
//        }



    }

    private boolean checkProviders() {
        if(provider1.getValue() != null && provider2.getValue() != null & provider3.getValue() != null)
            return provider1.getValue() != provider2.getValue() && provider1.getValue() != provider3.getValue() && provider2.getValue() != provider3.getValue();
        else
            return false;
    }

    private boolean checkCriteria() {

        if(category1.getValue() == null || category2.getValue() == null || category3.getValue() == null)
            return false;

        HashSet<String> checker = new HashSet();

        checker.add(category1.getValue().toString());
        checker.add(category2.getValue().toString());
        checker.add(category3.getValue().toString());

        if(category4.isVisible() && category4.getValue() != null) {
            checker.add(category4.getValue().toString());
            return checker.size() == 4;
        }
        if(category5.isVisible()&& category5.getValue() != null) {
            checker.add(category5.getValue().toString());
            return checker.size() == 5;
        }

        return checker.size() == 3;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
