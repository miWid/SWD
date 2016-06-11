package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Michal on 2016-06-11.
 */
public class CriteriaController implements Initializable {

    @FXML
    private Button cofnij0, dalej0;
    @FXML
    private Slider krytS1,krytS2,krytS3,krytS4,krytS5,krytS6,krytS7,krytS8,krytS9,krytS10;

    private Scene prevScene;
    private ArrayList<String> selectedProviders = new ArrayList<>();
    private ArrayList<Integer> selectedPreferences = new ArrayList<>();

    void initData(List<String> providers) {
        this.selectedProviders.addAll(providers);
    }


    @FXML
    private void goBack(ActionEvent event) throws IOException {

        Stage stage = (Stage) cofnij0.getScene().getWindow();
        stage.setScene(prevScene);
        stage.show();

    }

    @FXML
    private void nextPage(ActionEvent event) throws IOException {

        if(true){

            Stage stage = (Stage) dalej0.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/warianty.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            ChoicesController controller = loader.<ChoicesController>getController();
            controller.initData(selectedProviders);
            controller.setPreviousScene(dalej0.getScene());
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


    public void setPreviousScene(Scene prevScene){
        this.prevScene = prevScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
