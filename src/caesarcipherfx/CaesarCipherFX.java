
/** *******************************************************************
 *                                                                    *
 *  Author:      Kong Lor, Capella University                         *
 *  Course:      ITEC5020 - Application & Database Development        *
 *  Assignment:  Week 2 Assignment - Caesar Cipher FX                 *
 *  File:        CaesarCipherFX.java                                  *
 *  Description: Main Program for Caesar Cipher FX                    *
 *  Input:       User selects Key and input message                   *
 *  Output:      Translate with Caesar Cipher algorithm per Key value *
 *  Created:     10/16/2023                                           *
 *                                                                    *
 ******************************************************************** */
package caesarcipherfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CaesarCipherFX extends Application {
    
    // Method to perform Ceasar Cipher translation
    public static String translate(String inText, int key) {
        StringBuilder outText = new StringBuilder();

        for (int i = 0; i < inText.length(); i++) {
            char ch = ' ';
            char letter = inText.charAt(i);

            if (Character.isLetter(letter)) {
                if (Character.isUpperCase(letter)) {
                    ch = 'A';
                } else if (Character.isLowerCase(letter)) {
                    ch = 'a';
                }
            } else {
                outText.append(letter);
                continue;
            }
            // Calculate the new character using the Caesar Cipher formula
            char translatedChar = (char) (ch + (letter - ch + key + 26) % 26);
            outText.append(translatedChar);
        }
        return outText.toString();
    }

    @Override

    public void start(Stage primaryStage) {
        // Labels
        Label lbl1 = new Label("CAESAR CIPHER TRANSLATION");
        Label lbl2 = new Label("Select a key, enter a message, and press 'Translate'");
        Label keyLbl = new Label("Key: ");
        Label messageLbl1 = new Label("Message 1: ");
        Label messageLbl2 = new Label("Message 2: ");
        // User input components
        ComboBox<String> keyValueCB = createKeyComboBox();
        TextField inputField = new TextField();
        inputField.setPrefWidth(300);
        inputField.setPromptText("Please enter the message to be translated");
        TextField outputField = new TextField();
        outputField.setPrefWidth(300);
        outputField.setPromptText("Translated message will display here");
        outputField.setEditable(false);
        // Buttons
        Button translateBTN = new Button("Translate");
        Button copyBTN = new Button("Copy Up");
        Button clearBTN = new Button("Clear");

        translateBTN.setOnAction(e -> {
            translateAction(keyValueCB, inputField, outputField);
        });
        copyBTN.setOnAction(e -> {
            //System.out.println("This is the Copy Up button");
            copyTextToInput(inputField, outputField);
        });
        clearBTN.setOnAction(e -> {
            //System.out.println("This is the Clear button");
            clearFields(inputField, outputField);
        });

        //VBox vbox = new VBox(10);
        HBox hboxIF = new HBox(messageLbl1, inputField);
        HBox hboxOF = new HBox(messageLbl2, outputField);
        HBox hboxKV = new HBox(keyLbl, keyValueCB);
        HBox hboxBTN = new HBox(25, translateBTN, copyBTN, clearBTN);
        VBox vbox = new VBox(25, lbl1, lbl2, hboxKV, hboxIF, hboxOF, hboxBTN);
        //hbox.setMargin(translateBTN, new Insets(25, 0, 0 , 200));    // TOP, RIGHT, BOTTOM, LEFT
        vbox.setMargin(vbox, new Insets(25, 0, 0, 50));
        VBox vboxOL = new VBox(vbox);

        //vbox.getChildren().add(hbox);
        Scene scene = new Scene(vboxOL, 500, 350);
        primaryStage.setTitle("Caesar Cipher FX by Kong Lor");
        primaryStage.setScene(scene);    
        primaryStage.show();
    }

    // action when translate button is clicked
    private void translateAction(ComboBox<String> keyValueCB,
            TextField inputField, TextField outputField) {
        String tempKey = keyValueCB.getValue();
        if (tempKey == null) {
            showKeyMissingAlert(inputField);
            //outputField.setText("No Key Selected");
        } else {
            int key = Integer.parseInt(tempKey);
            String input = inputField.getText();
            if (input.trim().isEmpty()) {
                showInputFieldMissingAlert(inputField);
            } else {
                String translatedText = translate(input, key);
                outputField.setText(translatedText);
            }
        }
        
    }

    // Create a ComboBox for selecting the key
    private ComboBox<String> createKeyComboBox() {
        String[] keyValue = {"-3", "-2", "-1", "0", "1", "2", "3"};
        ObservableList<String> keyValueOL = FXCollections.observableArrayList(keyValue);
        ComboBox<String> keyValueCB = new ComboBox<>(keyValueOL);
        return keyValueCB;
    }

    // Show an alert for missing key selection and refocus
    private void showKeyMissingAlert(TextField inputField) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Key Value Missing");
        alert.setHeaderText(null);
        alert.setContentText("Please select a key value from the drop-down.");
        alert.showAndWait();
        inputField.requestFocus();
    }

    // Show an alert for missing input text and refocus
    private void showInputFieldMissingAlert(TextField inputField) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Input Field Missing");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a message to be translated.");
        alert.showAndWait();
        inputField.requestFocus();
    }

    // Clear input and output fields and return focus to input field
    private void clearFields(TextField inputField, TextField outputField) {
        inputField.clear();
        outputField.clear();
        inputField.requestFocus();
    }

    // Copy output field and set it to input field
    private void copyTextToInput(TextField inputField, TextField outputField) {
        String text = outputField.getText();
        inputField.setText(text);
        outputField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
