package com.playonlinux.ui.javafximpl.mainwindow;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.playonlinux.ui.javafximpl.JavaFXControllerImplementation;
import com.playonlinux.ui.api.EventHandler;

public class JavaFXMainWindowImplementation extends Stage {

    MenuBar menuBar;

    public void setUpWindow() {
        this.menuBar =  new MenuBar(this);

        BorderPane pane = new BorderPane();

        Scene scene = new Scene(pane, 600, 400);

        VBox topContainer = new VBox();

        topContainer.getChildren().add(menuBar);
        topContainer.getChildren().add(new ToolBar());

        VBox bottomContainer = new VBox();
        bottomContainer.getChildren().add(new StatusBar(this, scene));

        pane.setTop(topContainer);
        pane.setBottom(bottomContainer);

        this.setScene(scene);
        this.setTitle("PlayOnLinux");
        this.show();
    }

    public EventHandler getEventHandler() {
        return JavaFXControllerImplementation.getStaticEventHandler();
    }

}
