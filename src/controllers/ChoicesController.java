package controllers;

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
import java.util.*;

/**
 * Created by Michal on 2016-06-11.
 */
public class ChoicesController implements Initializable {

    private static Scene prevScene;
    public static Scene currScene;
    private ArrayList<String> selectedProviders = new ArrayList<>();
    private List<String> finalRanking = new ArrayList<>();

    private static double[] initValues = {0,0,0};

    public static Matrix cenaPreferences1 = new Matrix(initValues);
    public static Matrix cenaPreferences2 = new Matrix(initValues);
    public static Matrix cenaPreferences3 = new Matrix(initValues);
    public static Matrix cenaPreferences4 = new Matrix(initValues);
    public static Matrix anonPreferences1 = new Matrix(initValues);
    public static Matrix anonPreferences2 = new Matrix(initValues);
    public static Matrix anonPreferences3 = new Matrix(initValues);
    public static Matrix anonPreferences4 = new Matrix(initValues);
    public static Matrix mozPreferences1 = new Matrix(initValues);
    public static Matrix mozPreferences2 = new Matrix(initValues);
    public static Matrix mozPreferences3 = new Matrix(initValues);
    public static Matrix szyfPreferences1 = new Matrix(initValues);
    public static Matrix dostPreferences1 = new Matrix(initValues);



    void initData(List<String> providers) {
        setCurrentScene(tabPanel.getScene());
        this.selectedProviders.addAll(providers);
        fillProviders();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {

        Stage stage = (Stage) cofnij1.getScene().getWindow();
        stage.setScene(prevScene);
        stage.show();

    }

    @FXML
    private void nextPage(ActionEvent event) throws IOException {

        if(checkIfConsistent()) {

            double[] mozValues = {moz1_1.getValue()};
            mozPreferences1.setValues(mozValues);

            countFinalRanking();

            Stage stage = (Stage) dalej1.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ranking.fxml"));
            Scene scene = new Scene(loader.load());
            RankingController controller = loader.<RankingController>getController();
       //     controller.initData(finalRanking);
            controller.setPreviousScene(dalej1.getScene());
            stage.setScene(scene);
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

    private void countFinalRanking() {

        double[] criteriaPreferencesVector = CriteriaController.preferencesMatrix.getPreferencesVector(); //ogolne porownanie kryteriow
        double[] cenaPreferencesVector1 = cenaPreferences1.getPreferencesVector(); // porownanie podkryteriow ceny
        double[] cenaPreferencesVector2 = cenaPreferences2.getPreferencesVector(); // podkryterium1 ceny w stosunku do modeli
        double[] cenaPreferencesVector3 = cenaPreferences3.getPreferencesVector(); // podkryterium2 ceny w stosunku do modeli
        double[] cenaPreferencesVector4 = cenaPreferences4.getPreferencesVector(); // podkryterium3 ceny w stosunku do modeli
        double[] anonPreferencesVector1 = anonPreferences1.getPreferencesVector(); // porownanie podkryteriow anonimowosci
        double[] anonPreferencesVector2 = anonPreferences2.getPreferencesVector(); // podkryterium1 anon w stosunku do modeli
        double[] anonPreferencesVector3 = anonPreferences3.getPreferencesVector(); // podkryterium2 anon w stosunku do modeli
        double[] anonPreferencesVector4 = anonPreferences4.getPreferencesVector(); // podkryterium3 anon w stosunku do modeli
        double[] mozPreferencesVector1 = mozPreferences1.getPreferencesVector();  // porownanie podkryteriow mozliwosci
        double[] mozPreferencesVector2 = mozPreferences2.getPreferencesVector();  // podkryterium1 mozliwosci w stosunku do modeli
        double[] mozPreferencesVector3 = mozPreferences3.getPreferencesVector();  // podkryterium2 mozliwosci w stosunku do modeli
        double[] szyfPreferencesVector1 = szyfPreferences1.getPreferencesVector(); // kryterium szyfrowanie w stosunku do modeli
        double[] dostPreferencesVector1 = dostPreferences1.getPreferencesVector(); // kryterium dostepnosc w stosunku do modeli

        AHProcesseror ahp = new AHProcesseror();
        ahp.addCriteriaPreferences(criteriaPreferencesVector);
        ahp.addSubcriteriaPreferences(0,cenaPreferencesVector1);
        ahp.addSubcriteriaPreferences(1,anonPreferencesVector1);
        ahp.addSubcriteriaPreferences(2,mozPreferencesVector1);
        ahp.addFinalPreferences(0,0,cenaPreferencesVector2);
        ahp.addFinalPreferences(0,1,cenaPreferencesVector3);
        ahp.addFinalPreferences(0,2,cenaPreferencesVector4);
        ahp.addFinalPreferences(1,0,anonPreferencesVector2);
        ahp.addFinalPreferences(1,1,anonPreferencesVector3);
        ahp.addFinalPreferences(1,2,anonPreferencesVector4);
        ahp.addFinalPreferences(2,0,mozPreferencesVector2);
        ahp.addFinalPreferences(2,1,mozPreferencesVector3);
        ahp.addFinalPreferences(3,szyfPreferencesVector1);
        ahp.addFinalPreferences(4,dostPreferencesVector1);

        double[] ratings = ahp.getRatings();

        HashMap<String,Double> results = new HashMap<>();
        results.put(selectedProviders.get(0), ratings[0]);
        results.put(selectedProviders.get(1), ratings[1]);
        results.put(selectedProviders.get(2), ratings[2]);

        System.out.println("Ranking:");
        for(Double d: ratings)
            System.out.println(d);

       // finalRanking.add(selectedProviders.get(bestChoice));

    }

    private boolean checkIfConsistent() {

        return cena_spojnosc1.getText().equals("TAK") && cena_spojnosc3.getText().equals("TAK") && cena_spojnosc4.getText().equals("TAK") && cena_spojnosc5.getText().equals("TAK")
                && anon_spojnosc1.getText().equals("TAK") && anon_spojnosc3.getText().equals("TAK") && anon_spojnosc4.getText().equals("TAK") && anon_spojnosc5.getText().equals("TAK")
                && moz_spojnosc2.getText().equals("TAK") && moz_spojnosc3.getText().equals("TAK")
                && szyf_spojnosc1.getText().equals("TAK")
                && dost_spojnosc1.getText().equals("TAK");
    }

    @FXML
    private void isConsistentCena(Event event) {

        double[] values = new double[3];

        if(event.getSource() == cena1_1 || event.getSource() == cena1_2 || event.getSource() == cena1_3){
            values[0] = cena1_1.getValue();
            values[1] = cena1_2.getValue();
            values[2] = cena1_3.getValue();
            setConsistency(values, cena_spojnosc1);
            cenaPreferences1.setValues(values);
        }
        else if(event.getSource() == cena3_1 || event.getSource() == cena3_2 || event.getSource() == cena3_3){
            values[0] = cena3_1.getValue();
            values[1] = cena3_2.getValue();
            values[2] = cena3_3.getValue();
            setConsistency(values, cena_spojnosc3);
            cenaPreferences2.setValues(values);
        }
        else if(event.getSource() == cena4_1 || event.getSource() == cena4_2 || event.getSource() == cena4_3){
            values[0] = cena4_1.getValue();
            values[1] = cena4_2.getValue();
            values[2] = cena4_3.getValue();
            setConsistency(values, cena_spojnosc4);
            cenaPreferences3.setValues(values);
        }
        else{
            values[0] = cena5_1.getValue();
            values[1] = cena5_2.getValue();
            values[2] = cena5_3.getValue();
            setConsistency(values, cena_spojnosc5);
            cenaPreferences4.setValues(values);
        }
    }

    @FXML
    private void isConsistentAnon(Event event) {

        double[] values = new double[3];

        if(event.getSource() == anon1_1 || event.getSource() == anon1_2 || event.getSource() == anon1_3){
            values[0] = anon1_1.getValue();
            values[1] = anon1_2.getValue();
            values[2] = anon1_3.getValue();
            setConsistency(values, anon_spojnosc1);
            anonPreferences1.setValues(values);
        }
        else if(event.getSource() == anon3_1 || event.getSource() == anon3_2 || event.getSource() == anon3_3){
            values[0] = anon3_1.getValue();
            values[1] = anon3_2.getValue();
            values[2] = anon3_3.getValue();
            setConsistency(values, anon_spojnosc3);
            anonPreferences2.setValues(values);
        }
        else if(event.getSource() == anon4_1 || event.getSource() == anon4_2 || event.getSource() == anon4_3){
            values[0] = anon4_1.getValue();
            values[1] = anon4_2.getValue();
            values[2] = anon4_3.getValue();
            setConsistency(values, anon_spojnosc4);
            anonPreferences3.setValues(values);
        }
        else{
            values[0] = anon5_1.getValue();
            values[1] = anon5_2.getValue();
            values[2] = anon5_3.getValue();
            setConsistency(values, anon_spojnosc5);
            anonPreferences4.setValues(values);
        }
    }

    @FXML
    private void isConsistentMoz(Event event) {

        double[] values = new double[3];

        if(event.getSource() == moz3_1 || event.getSource() == moz3_2 || event.getSource() == moz3_3){
            values[0] = moz3_1.getValue();
            values[1] = moz3_2.getValue();
            values[2] = moz3_3.getValue();
            setConsistency(values, moz_spojnosc2);
            mozPreferences2.setValues(values);
        }
        else{
            values[0] = moz4_1.getValue();
            values[1] = moz4_2.getValue();
            values[2] = moz4_3.getValue();
            setConsistency(values, moz_spojnosc3);
            mozPreferences3.setValues(values);
        }
    }

    @FXML
    private void isConsistentSzyf(Event event) {

        double[] values = new double[3];

        values[0] = szyf1_1.getValue();
        values[1] = szyf1_2.getValue();
        values[2] = szyf1_3.getValue();
        setConsistency(values, szyf_spojnosc1);
        szyfPreferences1.setValues(values);

    }

    @FXML
    private void isConsistentDost(Event event) {

        double[] values = new double[3];

        values[0] = dost1_1.getValue();
        values[1] = dost1_2.getValue();
        values[2] = dost1_3.getValue();
        setConsistency(values, dost_spojnosc1);
        dostPreferences1.setValues(values);

    }

    private void setConsistency(double[] values, Label consistency) {

        Matrix matrix = new Matrix(values);
        matrix.normalize();

        if( matrix.isConsistent()){
            consistency.setText("TAK");
            consistency.setTextFill(Paint.valueOf("#029940"));
        }
        else {
            consistency.setText("NIE");
            consistency.setTextFill(Paint.valueOf("#ea0404"));
        }
    }

    private void fillProviders() {

        cena_provider1_3.setText(selectedProviders.get(0));
        cena_provider1_4.setText(selectedProviders.get(0));
        cena_provider1_5.setText(selectedProviders.get(0));
        cena_provider1_6.setText(selectedProviders.get(0));
        cena_provider1_7.setText(selectedProviders.get(0));
        cena_provider1_8.setText(selectedProviders.get(0));

        anon_provider1_3.setText(selectedProviders.get(0));
        anon_provider1_4.setText(selectedProviders.get(0));
        anon_provider1_5.setText(selectedProviders.get(0));
        anon_provider1_6.setText(selectedProviders.get(0));
        anon_provider1_7.setText(selectedProviders.get(0));
        anon_provider1_8.setText(selectedProviders.get(0));

        moz_provider1_3.setText(selectedProviders.get(0));
        moz_provider1_4.setText(selectedProviders.get(0));
        moz_provider1_5.setText(selectedProviders.get(0));
        moz_provider1_6.setText(selectedProviders.get(0));

        szyf_provider1_1.setText(selectedProviders.get(0));
        szyf_provider1_2.setText(selectedProviders.get(0));

        dost_provider1_1.setText(selectedProviders.get(0));
        dost_provider1_2.setText(selectedProviders.get(0));

        cena_provider2_3.setText(selectedProviders.get(1));
        cena_provider2_4.setText(selectedProviders.get(1));
        cena_provider2_5.setText(selectedProviders.get(1));
        cena_provider2_6.setText(selectedProviders.get(1));
        cena_provider2_7.setText(selectedProviders.get(1));
        cena_provider2_8.setText(selectedProviders.get(1));

        anon_provider2_3.setText(selectedProviders.get(1));
        anon_provider2_4.setText(selectedProviders.get(1));
        anon_provider2_5.setText(selectedProviders.get(1));
        anon_provider2_6.setText(selectedProviders.get(1));
        anon_provider2_7.setText(selectedProviders.get(1));
        anon_provider2_8.setText(selectedProviders.get(1));

        moz_provider2_3.setText(selectedProviders.get(1));
        moz_provider2_4.setText(selectedProviders.get(1));
        moz_provider2_5.setText(selectedProviders.get(1));
        moz_provider2_6.setText(selectedProviders.get(1));

        szyf_provider2_1.setText(selectedProviders.get(1));
        szyf_provider2_2.setText(selectedProviders.get(1));

        dost_provider2_1.setText(selectedProviders.get(1));
        dost_provider2_2.setText(selectedProviders.get(1));

        cena_provider3_3.setText(selectedProviders.get(2));
        cena_provider3_4.setText(selectedProviders.get(2));
        cena_provider3_5.setText(selectedProviders.get(2));
        cena_provider3_6.setText(selectedProviders.get(2));
        cena_provider3_7.setText(selectedProviders.get(2));
        cena_provider3_8.setText(selectedProviders.get(2));

        anon_provider3_3.setText(selectedProviders.get(2));
        anon_provider3_4.setText(selectedProviders.get(2));
        anon_provider3_5.setText(selectedProviders.get(2));
        anon_provider3_6.setText(selectedProviders.get(2));
        anon_provider3_7.setText(selectedProviders.get(2));
        anon_provider3_8.setText(selectedProviders.get(2));

        moz_provider3_3.setText(selectedProviders.get(2));
        moz_provider3_4.setText(selectedProviders.get(2));
        moz_provider3_5.setText(selectedProviders.get(2));
        moz_provider3_6.setText(selectedProviders.get(2));

        szyf_provider3_1.setText(selectedProviders.get(2));
        szyf_provider3_2.setText(selectedProviders.get(2));

        dost_provider3_1.setText(selectedProviders.get(2));
        dost_provider3_2.setText(selectedProviders.get(2));

    }

    public void setPreviousScene(Scene prevScene) {
        this.prevScene = prevScene;
    }

    public static void deletePreviousScene(){prevScene = null; }

    public void setCurrentScene(Scene currScene) {
        this.currScene = currScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private TabPane tabPanel;
    @FXML
    private Label cena_provider1_3, cena_provider1_4, cena_provider1_5, cena_provider1_6, cena_provider1_7, cena_provider1_8;
    @FXML
    private Label cena_provider2_3, cena_provider2_4, cena_provider2_5, cena_provider2_6, cena_provider2_7, cena_provider2_8;
    @FXML
    private Label cena_provider3_3, cena_provider3_4, cena_provider3_5, cena_provider3_6, cena_provider3_7, cena_provider3_8;
    @FXML
    private Label anon_provider1_3, anon_provider1_4, anon_provider1_5, anon_provider1_6, anon_provider1_7, anon_provider1_8;
    @FXML
    private Label anon_provider2_3, anon_provider2_4, anon_provider2_5, anon_provider2_6, anon_provider2_7, anon_provider2_8;
    @FXML
    private Label anon_provider3_3, anon_provider3_4, anon_provider3_5, anon_provider3_6, anon_provider3_7, anon_provider3_8;
    @FXML
    private Label moz_provider1_3, moz_provider1_4, moz_provider1_5, moz_provider1_6;
    @FXML
    private Label moz_provider2_3, moz_provider2_4, moz_provider2_5, moz_provider2_6;
    @FXML
    private Label moz_provider3_3, moz_provider3_4, moz_provider3_5, moz_provider3_6;
    @FXML
    private Label szyf_provider1_1, szyf_provider1_2, szyf_provider2_1, szyf_provider2_2, szyf_provider3_1, szyf_provider3_2;
    @FXML
    private Label dost_provider1_1, dost_provider1_2, dost_provider2_1, dost_provider2_2, dost_provider3_1, dost_provider3_2;
    @FXML
    private Button cofnij1, dalej1;
    @FXML
    private Slider cena1_1, cena1_2, cena1_3, cena3_1, cena3_2, cena3_3, cena4_1, cena4_2, cena4_3, cena5_1, cena5_2, cena5_3;
    @FXML
    private Slider anon1_1, anon1_2, anon1_3, anon3_1, anon3_2, anon3_3, anon4_1, anon4_2, anon4_3, anon5_1, anon5_2, anon5_3;
    @FXML
    private Slider moz1_1, moz3_1, moz3_2, moz3_3, moz4_1, moz4_2, moz4_3;
    @FXML
    private Slider szyf1_1, szyf1_2, szyf1_3;
    @FXML
    private Slider dost1_1, dost1_2, dost1_3;
    @FXML
    private Label cena_spojnosc1, cena_spojnosc3, cena_spojnosc4, cena_spojnosc5;
    @FXML
    private Label anon_spojnosc1, anon_spojnosc3, anon_spojnosc4, anon_spojnosc5;
    @FXML
    private Label moz_spojnosc2, moz_spojnosc3, szyf_spojnosc1, dost_spojnosc1;

}
