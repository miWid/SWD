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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
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
    private Label rank1, rank2, rank3;
    @FXML
    private Button cofnijLast;
    @FXML
    private ImageView  rank1_img, rank2_img, rank3_img;

    private Scene prevScene;
    private ArrayList<String> rankingProviders;

    void initData(List<String> providers) {
        this.rankingProviders = new ArrayList<>();
        this.rankingProviders.addAll(providers);
        rank1.setText(providers.get(0));
        rank2.setText(providers.get(1));
        rank3.setText(providers.get(2));
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
        Stage currStage = (Stage) rankPanel.getScene().getWindow();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/main2.fxml"));
        CriteriaController.currScene = null;
        ChoicesController.currScene = null;
        currStage.close();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public void setPreviousScene(Scene prevScene) {
        this.prevScene = prevScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file1 = new File("images/gold.png");
        File file2 = new File("images/silver.png");
        File file3 = new File("images/bronze.png");
        Image image1 = new Image(file1.toURI().toString());
        Image image2 = new Image(file2.toURI().toString());
        Image image3 = new Image(file3.toURI().toString());
        rank1_img.setImage(image1);
        rank2_img.setImage(image2);
        rank3_img.setImage(image3);

    }


}
