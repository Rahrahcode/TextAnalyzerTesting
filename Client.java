import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class Client extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter a number: "));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);

        BorderPane mainPane = new BorderPane();
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);

        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        tf.setOnAction(e -> {
            try (Socket socket = new Socket("localhost", 8000);
                 DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
                 DataInputStream fromServer = new DataInputStream(socket.getInputStream())) {
                // Get the number from the text field
                int number = Integer.parseInt(tf.getText().trim());

                // Send the number to the server
                toServer.writeInt(number);
                toServer.flush();

                // Get the response from the server
                boolean isPrime = fromServer.readBoolean();

                // Display the result in the text area
                ta.appendText("You entered the number: " + number + "\n");
                if (isPrime) {
                    ta.appendText(number + " is a prime number.\n");
                } else {
                    ta.appendText(number + " is not a prime number.\n");
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter a valid integer number.");
                alert.showAndWait();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
