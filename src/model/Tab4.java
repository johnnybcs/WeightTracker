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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static model.Tab2.*;

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
                    textArea.setWrapText(true);
                    textArea.setText(COACH_NAME + "What can I do for you today?\n");

                    textField = new TextField();
                    textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {

                            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                                textArea.appendText(USER_NAME + textField.getText() + "\n");
                                userInput = textField.getText().toLowerCase();
                                textField.setText("");

                                ActionListener actionListener = new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent ae) {
                                        reply();
                                    }
                                };
                                Timer timer = new Timer(850, actionListener);
                                timer.setRepeats(false);
                                timer.start();
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

    private void coachResponse(String string) {
        textArea.appendText(COACH_NAME + string + "\n");
    }

    private void reply() {
        randomNumberGenerator = new Random();
        int randomNumber = randomNumberGenerator.nextInt(20) + 1;
        if (userInput.contains("hi") || userInput.contains("hello")
                    || userInput.contains("hey") || userInput.contains("how are you")
                    || userInput.contains("how's it going") || userInput.contains("what's up")
                    || userInput.contains("good morning") || userInput.contains("good afternoon")
                    || userInput.contains("good evening") || userInput.contains("nice to meet")) {

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
                    break;
                default:
                    coachResponse("Hello!");
            }
        } else if (userInput.contains("bye") || userInput.contains("goodbye")
                    || userInput.contains("farewell") || userInput.contains("later")
                    || userInput.contains("so long") || userInput.contains("see ya")) {

            switch (randomNumber) {
                case (1):
                    coachResponse("See ya!");
                    break;
                case (2):
                    coachResponse("Goodbye!");
                    break;
                case (3):
                    coachResponse("Farewell!");
                    break;
                case (4):
                    coachResponse("Take care!");
                    break;
                case (5):
                    coachResponse("Bye bye!");
                    break;
                case (6):
                    coachResponse("See you later!");
                    break;
                case (7):
                    coachResponse("So long!");
                    break;
                default:
                    coachResponse("Have a good one!");
            }
        } else if (userInput.contains("thank")) {

                switch (randomNumber) {
                    case (1):
                        coachResponse("No problem!");
                        break;
                    case (2):
                        coachResponse("You're welcome.");
                        break;
                    case (3):
                        coachResponse("My pleasure.");
                        break;
                    default:
                        coachResponse("I'm happy to help.");
                }

        } else if (userInput.contains("starting weight")
                || userInput.contains("start weight")) {
            coachResponse("You weighed " + startingWeight
                    + " pounds when you started your weight loss journey.");
        } else if (userInput.contains("current weight")
                || userInput.contains("recent weight")
                || userInput.contains("latest weight")
                || userInput.contains("my weight")) {
            coachResponse("You currently weigh " + currentWeight
                    + " pounds.");
        } else if (userInput.contains("goal")
                || userInput.contains("target weight")) {
            coachResponse("You had set a weight goal of " + currentWeight + " pounds for yourself.");
        } else if (userInput.contains("remaining weight")
                || userInput.contains("to lose")
                || userInput.contains("have left")) {
            coachResponse("Just " + remainingWeight + " pounds to go!");
        } else if (userInput.contains("i lost")
                || userInput.contains("how much weight did i lose")
                || userInput.contains("weight loss")) {
            coachResponse("You lost " + lostWeight + " pounds so far.");
        } else if (userInput.contains("how do")
                || userInput.contains("how to")
                || userInput.contains("do to")) {
            coachResponse("Diet and exercise!");
        } else {

            switch (randomNumber) {
                case (1):
                    coachResponse("Only you can change your life. No one can do it for you.");
                    break;
                case (2):
                    coachResponse("Doubt kills more dreams than failure ever will.");
                    break;
                case (3):
                    coachResponse("The struggle you are in today is developing the strength you need for tomorrow.");
                    break;
                case (4):
                    coachResponse("The road may be bumpy, but stay committed to the process.");
                    break;
                case (5):
                    coachResponse("Will is a skill.");
                    break;
                case (6):
                    coachResponse("Stressed spelled backwards is desserts. Coincidence? I think not!");
                    break;
                case (7):
                    coachResponse("Someone busier than you is running right now.");
                    break;
                case (8):
                    coachResponse("You can't cross the sea merely by standing and staring at the water.");
                    break;
                case (9):
                    coachResponse("Strive for progress, not perfection.");
                    break;
                case (10):
                    coachResponse("Make your body the sexiest outfit you own.");
                    break;
                case (11):
                    coachResponse("It won't be easy but it'll be worth it.");
                    break;
                case (12):
                    coachResponse("Never let a stumble in the road be the end of your journey.");
                    break;
                case (13):
                    coachResponse("Look back at where you came from and let yourself feel proud about your progress.");
                    break;
                case (14):
                    coachResponse("Eat less sugar. You're sweet enough already.");
                    break;
                case (15):
                    coachResponse("When you feel like quitting, think about why you started.");
                    break;
                default:
                    coachResponse("Never, never, never give up.");
            }
        }
    }
}
