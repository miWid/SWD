package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Michal on 2016-06-11.
 */
public class ChoicesController implements Initializable {

    @FXML
    private TabPane tabPane;

    private Scene prevScene;
    public static Scene currScene;
    private ArrayList<String> selectedProviders = new ArrayList<>();

    void initData(List<String> providers) {
        setCurrentScene(tabPane.getScene());
        this.selectedProviders.addAll(providers);
    }

    public void setPreviousScene(Scene prevScene){
        this.prevScene = prevScene;
    }

    public void setCurrentScene(Scene currScene){
        this.currScene = currScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
