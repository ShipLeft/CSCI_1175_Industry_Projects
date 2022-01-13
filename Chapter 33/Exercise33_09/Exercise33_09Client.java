import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

import java.net.Socket;

/**
 * <h1>Exercise33_09Client</h1>
 *
 * <p>This class sets up a client that will work with the Exercise33_09Server
 * and will allow the user to exchange messages with the server user.</p>
 *
 * <p>Created 01/12/2022</p>
 *
 * @author Rhett Boatright
 */
public class Exercise33_09Client extends Application {
    private TextArea taServer = new TextArea();
    private TextArea taClient = new TextArea();
    private Socket clientSocket; //Socket for gathering information from the Server
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Thread thread; //Thread for the GUI to operate simultaneously with the server.

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        taServer.setWrapText(true);
        taServer.setEditable(false);
        taClient.setWrapText(true);

        BorderPane pane1 = new BorderPane();
        pane1.setTop(new Label("History"));
        pane1.setCenter(new ScrollPane(taServer));
        BorderPane pane2 = new BorderPane();
        pane2.setTop(new Label("New Message"));
        pane2.setCenter(new ScrollPane(taClient));

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(pane1, pane2);

        // Create a scene and place it in the stage
        Scene scene = new Scene(vBox, 200, 200);
        primaryStage.setTitle("Exercise31_09Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        thread = new Thread(()-> {
            try {
                //Get all of the info from the server
                while (true) {
                    String host = inputStream.readUTF();

                    //Append it to the server text area
                    Platform.runLater(() -> {
                        taServer.appendText("S: " + host + '\n');
                    });
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
            scene.setOnKeyPressed(e -> {
                try {
                    if (e.getCode() == KeyCode.ENTER) {
                        String client = taClient.getText();
                        taClient.setText("");
                        taServer.appendText(client);

                        outputStream = new DataOutputStream(clientSocket.getOutputStream());
                        outputStream.writeUTF(client);
                        outputStream.flush();
                    }
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            });
            try {
                clientSocket = new Socket("localhost", 8000); //host and port for the server

                //Input stream for data from the server
                inputStream = new DataInputStream(clientSocket.getInputStream());
                outputStream = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException ex) {
                taServer.appendText(ex.toString() + '\n');
            }
        });
        thread.start();
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
