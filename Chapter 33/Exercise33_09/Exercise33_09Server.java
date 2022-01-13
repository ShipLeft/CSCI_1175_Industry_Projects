import javafx.application.Application;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * <h1>Exercise33_09Server</h1>
 *
 * <p>This class will create a server that will allow a user on
 * Exercise33_09Client to message the user on this GUI. It also
 * has a chat log that is not editable on either side.</p>
 *
 * <p>Created 01/13/2022</p>
 *
 * @author Rhett Boatright
 */
public class Exercise33_09Server extends Application {
    private TextArea taServer = new TextArea(); //For the Chat Log
    private TextArea taClient = new TextArea(); //For the user to enter a message.

    private ServerSocket serverSocket; //Server socket
    private Socket socket; //Socket for gathering input from Client
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Thread thread; //Thread to run the Server on

    @Override // Override the start method in the Application class
    /**
     * This method will create the GUI, allow for the server to connect to the client, and will allow
     * the user to exchange messages with the connected client until they are finished messaging.
     */
    public void start(Stage primaryStage) {
        taServer.setWrapText(true);
        taServer.setEditable(false); //Make the chat log not editable
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
        primaryStage.setTitle("Exercise31_09Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        //Add thread for the Server
        thread = new Thread(()->{
            //Initialize the server socket
            try{
                //Comment on server startup
                serverSocket = new ServerSocket(8000);
                Platform.runLater(()->
                        taServer.appendText("Exercise33_09Server started at " + new Date() + '\n'));

                //Wait for the connection request
                socket = serverSocket.accept();
                taServer.appendText("Connection started at " + new Date() + '\n');

                //Initialize the input stream
                //inputStream = new DataInputStream(socket.getInputStream());
                inputStream = new DataInputStream(socket.getInputStream());

                while(true){//While the server is getting information
                    String clientString = inputStream.readUTF(); //Read what the client has sent

                    //Write the information to the server
                    Platform.runLater(()->
                            taServer.appendText("C: " + clientString + "\n"));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            scene.setOnKeyPressed(e ->{
                try {
                    if (e.getCode() == KeyCode.ENTER) { //If the enter key is pressed
                        String host = taClient.getText(); //Grab the text in the client text area
                        taClient.setText("");
                        taServer.appendText(host);

                        //Initialize the outputStream
                        outputStream = new DataOutputStream(socket.getOutputStream());
                        outputStream.writeUTF(host);
                        outputStream.flush();

                    }
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            });
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
