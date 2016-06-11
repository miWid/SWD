package controllers;

import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Michal on 2016-06-11.
 */
public class ChoicesController implements Initializable {

    private Scene prevScene;
    private ArrayList<String> selectedProviders = new ArrayList<>();

    void initData(List<String> providers) {
        this.selectedProviders.addAll(providers);
    }

    public void setPreviousScene(Scene prevScene){
        this.prevScene = prevScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
