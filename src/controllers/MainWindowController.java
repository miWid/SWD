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
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Michal on 2016-06-10.
 */
public class MainWindowController implements Initializable{

    @FXML
    private ComboBox provider1, provider2, provider3;
    @FXML
    private Button mainNaviButton;

    private final ObservableList<String> allProviders =
            FXCollections.observableArrayList(
                    "Provider 1",
                    "Provider 2",
                    "Provider 3",
                    "Provider 4",
                    "Provider 5"
            );

    private List<String> selectedProviders = new ArrayList();


    @FXML
    private void fillProviders(Event event) {
        ((ComboBox)event.getSource()).getItems().setAll(allProviders);
    }


    @FXML
    private void goToNextScene(ActionEvent event) throws IOException {

        if(checkProviders()){

            if(selectedProviders.isEmpty())
                selectedProviders.addAll(Arrays.asList(provider1.getValue().toString(), provider2.getValue().toString(), provider3.getValue().toString()));

            Stage stage = (Stage) mainNaviButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/kryteria.fxml"));
            Scene scene = new Scene(loader.load());
            CriteriaController controller = loader.<CriteriaController>getController();
            if(controller.currScene != null)
                stage.setScene(controller.currScene);
            else {
                controller.initData(selectedProviders);
                controller.setPreviousScene(mainNaviButton.getScene());
                stage.setScene(scene);
            }
            stage.show();

        }
        else{

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ostrzeżenie");
            alert.setHeaderText("Nie można wykonać żądanej operacji.");
            alert.setContentText("Upewnij się, że wszycy dostawcy zostali wybrani oraz nie ma żadnych powtórzeń.");

            alert.showAndWait();

        }
    }

    private boolean checkProviders() {
        if(provider1.getValue() != null && provider2.getValue() != null & provider3.getValue() != null)
            return provider1.getValue() != provider2.getValue() && provider1.getValue() != provider3.getValue() && provider2.getValue() != provider3.getValue();
        else
            return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
