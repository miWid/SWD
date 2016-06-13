package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
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
    private Label kryteria_spojnosc;
    @FXML
    private Slider krytS1,krytS2,krytS3,krytS4,krytS5,krytS6,krytS7,krytS8,krytS9,krytS10;
    @FXML
    private SplitPane krytPane;

    public static Scene prevScene;
    public static Scene currScene;
    private ArrayList<String> selectedProviders = new ArrayList<>();
    public static Matrix preferencesMatrix = new Matrix();
    private double[] values = new double[10];

    void initData(List<String> providers) {
        setCurrentScene(krytPane.getScene());
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

        if(checkIfConsistent()){

            preferencesMatrix.setValues(values);

//            for(double d : values){
//                System.out.println(d);
//            }
//
//            double[] check = preferencesMatrix.getPreferencesVector();
//            System.out.println("Preferences");
//            for(double d : check){
//                System.out.println(d);
//            }

            Stage stage = (Stage) dalej0.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/warianty.fxml"));
            Scene scene = new Scene(loader.load());
            ChoicesController controller = loader.<ChoicesController>getController();
            if(controller.currScene != null)
                stage.setScene(controller.currScene);
            else {
                controller.initData(selectedProviders);
                controller.setPreviousScene(dalej0.getScene());
                stage.setScene(scene);
            }
            stage.show();

        }
        else{

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ostrzeżenie");
            alert.setHeaderText("Nie można wykonać żądanej operacji.");
            alert.setContentText("Upewnij się, że wszystkie preferencje zostały odpowiednio dobrane.");

            alert.showAndWait();

        }

    }

    private boolean checkIfConsistent() {
        return kryteria_spojnosc.getText().equals("TAK");
    }


    public void setPreviousScene(Scene prevScene){
        this.prevScene = prevScene;
    }

    public static void deletePreviousScene(){prevScene = null; }

    public void setCurrentScene(Scene currScene){
        this.currScene = currScene;
    }

    @FXML
    private void isConsistent(Event event) {

        values[0] = krytS1.getValue();
        values[1] = krytS2.getValue();
        values[2] = krytS3.getValue();
        values[3] = krytS4.getValue();
        values[4] = krytS5.getValue();
        values[5] = krytS6.getValue();
        values[6] = krytS7.getValue();
        values[7] = krytS8.getValue();
        values[8] = krytS9.getValue();
        values[9] = krytS10.getValue();

        Matrix matrix = new Matrix(values);
        matrix.normalize();

        if(matrix.isConsistent()){
            kryteria_spojnosc.setText("TAK");
            kryteria_spojnosc.setTextFill(Paint.valueOf("#029940"));
        }
        else {
            kryteria_spojnosc.setText("NIE");
            kryteria_spojnosc.setTextFill(Paint.valueOf("#ea0404"));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
