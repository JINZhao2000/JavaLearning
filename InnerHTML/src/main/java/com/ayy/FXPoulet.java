package com.ayy;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @ ClassName FXPoulet
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/11/2020 20
 * @ Version 1.0
 */
public class FXPoulet extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage){
        stage.setWidth(1300);
        stage.setHeight(770);
        Scene scene = new Scene(new Group());


        final WebView browser = new WebView();
        browser.setMinHeight(720);
        browser.setMinWidth(1280);
        final WebEngine webEngine = browser.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);

        webEngine.getLoadWorker().stateProperty()
                .addListener(new ChangeListener<State>() {
                    @Override
                    public void changed(ObservableValue ov, State oldState, State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            stage.setTitle("Poulet Roti");//webEngine.getLocation());
                            stage.getIcons().add(new Image("poulet.jpg"));
                        }
                    }
                });
        webEngine.load("http://localhost:8080/ptut/index.html");
        
        scene.setRoot(scrollPane);

        stage.setScene(scene);
        stage.show();
    }
}
