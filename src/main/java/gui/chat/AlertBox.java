package gui.chat;

import gui.ClientGUI;
import gui.CustomButtons;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class creates stages with important messages.
 */
public class AlertBox {

    /**
     * This function creates a new stage containing a message and a confirm button.
     * @param title The title of the stage.
     * @param message The actual message.
     */
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.setResizable(false);
        window.getIcons().add(new Image(ClientGUI.class.getResourceAsStream("/images/Small_Logo.png")));

        window.initModality(Modality.APPLICATION_MODAL); // not allowed to switch unless alert is handled
        window.setTitle(title);
        window.setMinWidth(250);

        Label lbl = new Label();
        lbl.setText(message);
        CustomButtons closeBtn = new CustomButtons("OK",'W',0,12);
        closeBtn.setPrefSize(50,30);
        closeBtn.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(lbl, closeBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: blue, lightblue;");

        Scene scene = new Scene(layout,300,100);
        window.setScene(scene);
        window.showAndWait(); // no interaction unless alert window closed

    }
}
