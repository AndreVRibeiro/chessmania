package gui.chat;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class creates stages where clients can click on yes or on no.
 */
public class ConfirmBox {

    static boolean ans;

    /**
     * This function creates a stage with a title, message and confirm buttons.
     * @param title The title of the stage.
     * @param message The actual message.
     * @return True or false depending on the clicked button.
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); // not allowed to switch unless alert is handled
        window.setTitle(title);
        window.setMinWidth(250);

        Label lbl = new Label();
        lbl.setText(message);

        Button yesBtn = new Button("Yes");
        Button noBtn = new Button("No");


        yesBtn.setOnAction(e -> {
            ans = true;
            window.close();
        });

        noBtn.setOnAction(e -> {
            ans = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(lbl, yesBtn, noBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: blue, lightblue;");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); // no interaction unless alert window closed

        return ans;

    }
}
