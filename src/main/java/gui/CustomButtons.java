package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class is used to create custom Buttons. These Buttons use a png image as a skin.
 */
public class CustomButtons extends Button {

    public char color;
    public int number;
    public int size;
    private final String FONT_PATH = "src/main/resources/images/UI-Elements/kenvector_future_thin.ttf";
    private final String BUTTON_PRESSED_STYLE_BLUE = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_2/blue_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_BLUE = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_2/blue_button_free.png');";
    private final String BUTTON_PRESSED_STYLE_BLUE_SMALL = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/blue_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_BLUE_SMALL = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/blue_button_free.png');";

    private final String BUTTON_PRESSED_STYLE_RED = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_2/red_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_RED = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_2/red_button_free.png');";
    private final String BUTTON_PRESSED_STYLE_RED_SMALL = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/red_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_RED_SMALL = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/red_button_free.png');";


    private final String BUTTON_PRESSED_STYLE_GREEN = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_2/green_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_GREEN = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_2/green_button_free.png');";
    private final String BUTTON_PRESSED_STYLE_GREEN_SMALL = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/green_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_GREEN_SMALL = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/green_button_free.png');";

    private final String BUTTON_PRESSED_STYLE_YELLOW = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_2/yellow_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_YELLOW = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_2/yellow_button_free.png');";
    private final String BUTTON_PRESSED_STYLE_YELLOW_SMALL = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/yellow_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_YELLOW_SMALL = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/yellow_button_free.png');";

    private final String BUTTON_PRESSED_STYLE_WHITE = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/white_button_pressed.png');";
    private final String BUTTON_FREE_STYLE_WHITE = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/white_button_free.png');";
    private final String BUTTON_PRESSED_STYLE_WHITE_2 = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/white_button_pressed2.png');";
    private final String BUTTON_FREE_STYLE_WHITE_2 = "-fx-background-color: transparent; -fx-background-image: url('/images/UI-Elements/Buttons_3/white_button_free2.png');";

    /**
     * This is the constructor of the class. It creates a new button with a specific png image and text.
     * @param text The text that is written inside of the button.
     * @param color The color of the button.
     * @param number This number indicates which version of the png image should be used.
     * @param size The size of the font.
     */
    public CustomButtons(String text, char color, int number, int size) {
        this.setText(text);
        this.size = size;
        setButtonFont();
        this.color = color;
        this.number = number;

        if (color == 'B' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_BLUE);
        } else if (color == 'B' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_BLUE_SMALL);
        } else if (color == 'R' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_RED);
        } else if (color == 'R' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_RED_SMALL);
        } else if (color == 'G' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_GREEN);
        } else if (color == 'G' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_GREEN_SMALL);
        } else if (color == 'Y' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_YELLOW);
        } else if (color == 'Y' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_YELLOW_SMALL);
        } else if (color == 'W' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_WHITE);
        } else if (color == 'W' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_WHITE_2);
        }
        initializeButtonListener();
    }

    /**
     * This method changes the font and size of the text.
     */
    private void setButtonFont() {
        //try {
            //this.setFont(Font.loadFont(new FileInputStream(FONT_PATH),20));
        //} catch (FileNotFoundException e) {
            setFont(Font.font("Verdana",size));
        //}
    }

    /**
     * This method uses the setStyle method of the button class to change the image of the button.
     * The image shows a pressed button and is used when the button is pressed.
     */
    private void setButtonPressedStyle() {
        if (this.color == 'B' && number == 0) {
            this.setStyle(BUTTON_PRESSED_STYLE_BLUE);
        } else if (color == 'B' && number == 1) {
            this.setStyle(BUTTON_PRESSED_STYLE_BLUE_SMALL);
        } else if (this.color == 'R' && number == 0) {
            this.setStyle(BUTTON_PRESSED_STYLE_RED);
        } else if (this.color == 'R' && number == 1) {
            this.setStyle(BUTTON_PRESSED_STYLE_RED_SMALL);
        } else if (this.color == 'G' && number == 0) {
            this.setStyle(BUTTON_PRESSED_STYLE_GREEN);
        }  else if (this.color == 'G' && number == 1) {
            this.setStyle(BUTTON_PRESSED_STYLE_GREEN_SMALL);
        } else if (this.color == 'Y' && number == 0) {
            this.setStyle(BUTTON_PRESSED_STYLE_YELLOW);
        } else if (this.color == 'Y' && number == 1) {
            this.setStyle(BUTTON_PRESSED_STYLE_YELLOW_SMALL);
        } else if (color == 'W' && number == 0) {
            this.setStyle(BUTTON_PRESSED_STYLE_WHITE);
        } else if (color == 'W' && number == 1) {
            this.setStyle(BUTTON_PRESSED_STYLE_WHITE_2);
        }
        if (color == 'W') {
            setPrefHeight(25);
            setLayoutY(getLayoutY() + 4);
        } else {
            setPrefHeight(45);
            setLayoutY(getLayoutY() + 4);
        }
    }

    /**
     * This method uses the setStyle method of the button class to change the image of the button.
     * The image shows a released button and is used after the button is pressed.
     */
    private void setButtonReleasedStyle() {
        if (this.color == 'B' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_BLUE);
        } else if (color == 'B' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_BLUE_SMALL);
        } else if (this.color == 'R' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_RED);
        } else if (this.color == 'R' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_RED_SMALL);
        } else if (this.color == 'G' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_GREEN);
        } else if (this.color == 'G' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_GREEN_SMALL);
        } else if (this.color == 'Y' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_YELLOW);
        } else if (this.color == 'Y' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_YELLOW_SMALL);
        } else if (color == 'W' && number == 0) {
            setStyle(BUTTON_FREE_STYLE_WHITE);
        } else if (color == 'W' && number == 1) {
            setStyle(BUTTON_FREE_STYLE_WHITE_2);
        }
        if (color == 'W') {
            setPrefHeight(29);
            setLayoutY(getLayoutY() - 4);
        } else {
            setPrefHeight(49);
            setLayoutY(getLayoutY() - 4);
        }
    }

    /**
     * This method initializes all the needed Listeners of the button.
     * SetOnMousePressed, setOnMouseReleased, setOnMouseEntered, setOnMouseExited
     */
    private void initializeButtonListener() {

        // When the button is pressed the image of the button is changed with the setButtonPressedStyle() method.
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                    Audio.buttonOne();

                }
            }
        });

        // When the button is released the image of the button is changed with the setButtonReleasedStyle() method.
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }
        });

        // When the mouse enters the button the button is surrounded by a shadow.
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow s = new DropShadow();
                if (color == 'B') {
                    s.setColor(Color.BLUE);
                } else if (color == 'R') {
                    s.setColor(Color.RED);
                } else if (color == 'G') {
                    s.setColor(Color.GREEN);
                } else if (color == 'Y') {
                    s.setColor(Color.ORANGE);
                } else if (color == 'W') {
                    s.setColor(Color.GRAY);
                }
                setEffect(s);
            }
        });

        // When the mouse exits the button the shadow surrounding the button disappears.
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }



}
