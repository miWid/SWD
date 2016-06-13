package controllers;

import app.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Michal on 2016-06-12.
 */
public class RankingController implements Initializable {

    @FXML
    private SplitPane rankPanel;
    @FXML
    private Label rank1;
    @FXML
    private Button cofnijLast;

    private Scene prevScene;
    private ArrayList<String> rankingProviders = new ArrayList<>();

    void initData(List<String> providers) {
        this.rankingProviders.addAll(providers);
        rank1.setText(providers.get(0));
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {

        Stage stage = (Stage) rankPanel.getScene().getWindow();
        stage.setScene(prevScene);
        stage.show();

    }

    @FXML
    private void close(ActionEvent event) throws IOException {

        Stage stage = (Stage) rankPanel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goToStart(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/main2.fxml"));
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void setPreviousScene(Scene prevScene) {
        this.prevScene = prevScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
