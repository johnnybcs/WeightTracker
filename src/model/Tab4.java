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
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static ui.Main.weightGoal;

public class Tab4 implements Observer {
    private static final int TEXT_AREA_HEIGHT = 325;
    private static final String COACH_NAME = "Coach:  ";
    private static final String USER_NAME = "You:  ";

    private String name;
    private TextField textField;
    private TextArea textArea;
    private String userInput;
    private Random randomNumberGenerator;
    private Tab2 tab2;


    public Tab4(String name) {
        this.name = name;
        this.tab2 = new Tab2("Summary");
        tab2.updateWeights();
    }

    public Tab createTab4() {
        Tab tab4 = new Tab(name);
        tab4.setClosable(false);


        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefHeight(TEXT_AREA_HEIGHT);
        textArea.setWrapText(true);
        textArea.setText(COACH_NAME + "What can I do for you today?\n");

        textField = new TextField();

        tab4.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (tab4.isSelected()) {

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
        tab2.updateWeights();
        randomNumberGenerator = new Random();
        int randomNumber = randomNumberGenerator.nextInt(10) + 1;
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
        } else if (userInput.contains("i want")
                || userInput.contains("i wish")
                || userInput.contains("help")
                || userInput.contains("i hope")
                || userInput.contains("attempting to")
                || userInput.contains("trying to")
                || userInput.contains("try to")) {
            coachResponse("Where there's a will, there's a way! Eat less sugar. You're sweet enough already!");
        } else if (userInput.contains("name")
                || userInput.contains("who")) {
            coachResponse("Who I am doesn't matter. What matters is who you are. Never forget that you are a champion!");
        } else if (userInput.contains("age")
                || userInput.contains("how old")) {
            coachResponse("I'm old enough to be your coach!");
        } else if (userInput.contains("where")) {
            coachResponse("Canada.");
        } else if (userInput.contains("how are you")
                || userInput.contains("whats up")
                || userInput.contains("hows it going")
                || userInput.contains("how's it going")
                || userInput.contains("what's up")) {
            coachResponse("I'm good. Thanks for asking.");
        } else if (userInput.contains("how do")
                || userInput.contains("how to")
                || userInput.contains("what do")
                || userInput.contains("should")
                || userInput.contains("do to")) {
            coachResponse("Diet and exercise!");
        } else if (userInput.contains("ok")
                || userInput.contains("okay")
                || userInput.contains("sure")
                || userInput.contains("alright")
                || userInput.contains("k")) {
            coachResponse(" :)");
        } else if (userInput.contains("starting weight")
                || userInput.contains("start weight")) {
            coachResponse("You weighed " + tab2.getStartingWeight()
                    + " pounds when you started your weight loss journey.");
        } else if (userInput.contains("current weight")
                || userInput.contains("recent weight")
                || userInput.contains("latest weight")
                || userInput.contains("how much do i weigh")
                || userInput.contains("my weight")) {
            coachResponse("You currently weigh " + tab2.getCurrentWeight()
                    + " pounds.");
        } else if (userInput.contains("goal")
                || userInput.contains("target weight")) {
            coachResponse("You had set a weight goal of " + weightGoal + " pounds for yourself.");
        } else if (userInput.contains("remaining")
                || userInput.contains("to lose")
                || userInput.contains("have left")) {
            coachResponse("Just " + tab2.getRemainingWeight() + " pounds to go!");
        } else if (userInput.contains("i lost")
                || userInput.contains("did i lose")
                || userInput.contains("weight loss")) {
            coachResponse("You lost " + tab2.getLostWeight() + " pounds so far.");
        } else {

            switch (randomNumber) {
                case (1):
                    coachResponse("Where there is no struggle, there is no strength.");
                    break;
                case (2):
                    coachResponse("The struggle you are in today is developing the strength you need for tomorrow.");
                    break;
                case (3):
                    coachResponse("Never, never, never give up.");
                    break;
                case (4):
                    coachResponse("Look back at where you came from and let yourself feel proud about your progress.");
                    break;
                case (5):
                    coachResponse("The past cannot be changed, but the future is yet in your power.");
                    break;
                case (6):
                    coachResponse("Life is 10 percent what happens to you and 90 percent how you react to it.");
                    break;
                case (7):
                    coachResponse("Have faith in yourself! You are a strong and beautiful person!");
                    break;
                case (8):
                    coachResponse("It doesn't get EASIER. You just get STRONGER.");
                    break;
                case(9):
                    coachResponse("What defines us is how well we rise after falling.");
                    break;
                default:
                    coachResponse("Never let a stumble in the road be the end of your journey.");
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        tab2.updateWeights();
    }
}
