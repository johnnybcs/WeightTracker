package model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.util.Random;

import static model.Tab2.currentWeight;
import static model.Tab2.startingWeight;

public class Tab4 {
    private static final int TEXT_AREA_HEIGHT = 325;
    private static final String COACH_NAME = "Coach:  ";
    private static final String USER_NAME = "You:  ";

    private String name;
    private TextField textField;
    private TextArea textArea;
    private String userInput;
    private Random randomNumberGenerator;


    public Tab4(String name) {
        this.name = name;
    }

    public Tab createTab4() {
        Tab tab4 = new Tab(name);
        tab4.setClosable(false);

        tab4.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (tab4.isSelected()) {

                    textArea = new TextArea();
                    textArea.setEditable(false);
                    textArea.setPrefHeight(TEXT_AREA_HEIGHT);
                    textArea.setText(COACH_NAME + "What can I do for you today?\n");

                    textField = new TextField();
                    textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                                textArea.appendText(USER_NAME + textField.getText() + "\n");
                                userInput = textField.getText().toLowerCase();
                                textField.setText("");

                                if (userInput.contains("hi") || userInput.contains("hello")
                                        || userInput.contains("hey") || userInput.contains("how are you")
                                        || userInput.contains("how's it going") || userInput.contains("what's up")
                                        || userInput.contains("good morning") || userInput.contains("good afternoon")
                                        || userInput.contains("good evening") || userInput.contains("nice to meet")) {

                                    randomNumberGenerator = new Random();
                                    int randomNumber = randomNumberGenerator.nextInt(10) + 1;
                                    
                                    switch (randomNumber) {
                                        case (1):
                                            coachResponse("Hello there!");
                                            break;
                                        case (2):
                                            coachResponse("Hi!");
                                            break;
                                        case (3):
                                            coachResponse("Hi there!");
                                            break;
                                        case (4):
                                            coachResponse("Howdy!");
                                            break;
                                        case (5):
                                            coachResponse("Yo!");
                                            break;
                                        case (6):
                                            coachResponse("Hey!");
                                            break;
                                        case (7):
                                            coachResponse("Sup!");
                                        default:
                                            coachResponse("Hello!");
                                    }
                                } else if (userInput.contains("starting weight")
                                        || userInput.contains("start weight")) {
                                    coachResponse("You weighed " + startingWeight
                                            + " pounds when you started your weight loss journey.");
                                } else if (userInput.contains("current weight")
                                        || userInput.contains("recent weight")
                                        || userInput.contains("latest weight")) {
                                    coachResponse("You currently weight " + currentWeight
                                            + " pounds.");
                                } else {
                                    coachResponse("Diet and exercise!");
                                }

                            }
                        }
                    });

                    VBox vBox = new VBox(textArea, textField);
                    vBox.setMargin(textField, new Insets(10, 10, 10, 10));
                    tab4.setContent(vBox);
                }
            }
        });

        return tab4;
    }

    public void coachResponse(String string) {
        textArea.appendText(COACH_NAME + string + "\n");
    }
}