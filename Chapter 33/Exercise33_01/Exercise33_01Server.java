// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * <h1>Exercise33_01Server</h1>
 *
 * <p>This class will make a server for Exercise33_01Client to connect to as well as make a GUI for the
 * server host to look at. This GUI will append all of the information from when the server started, when
 * it connected to a client, and the information the client inputted into the client GUI. It will then
 * calculate the loan monthly and total payments and will send them back to the client
 * and append that information to the text area as well.</p>
 *
 * <p>Created 01/12/2022</p>
 *
 * @author Rhett Boatright
 */
public class Exercise33_01Server extends Application {
    // Text area for displaying contents
    private TextArea ta = new TextArea();

    //Variables for the socket, input and output streams, and the thread
    private ServerSocket serverSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private Thread thread;

    @Override // Override the start method in the Application class
    /**
     * This method will create the GUI as well as receive, send, and append information to and from the client.
     * It will also call upon Loan.java to calculate the loan payments to return to the client.
     */
    public void start(Stage primaryStage) {
        ta.setWrapText(true);

        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 400, 200);
        primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        thread = new Thread(() ->{
           //Initialize the server socket
            try {
                serverSocket = new ServerSocket(8000);
                Platform.runLater(() ->
                        ta.appendText("Exercise33_01Server started at " + new Date() + '\n'));

                //Wait for connection request
                Socket socket = serverSocket.accept();
                ta.appendText("Connected to a client at " + new Date() + '\n');

                //Create the input and output streams
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());

                while (true) { //While the server is getting information
                    double annualInterestRate = input.readDouble();
                    int numberOfYears = input.readInt();
                    double loanAmount = input.readDouble();

                    //Compute loan payments
                    Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
                    double monthlyPayment = loan.getMonthlyPayment();
                    double totalPayment = loan.getTotalPayment();

                    //Send data to client
                    output.writeDouble(monthlyPayment);
                    output.writeDouble(totalPayment);

                    //Write to server
                    Platform.runLater(()-> {
                        ta.appendText("Annual Interest Rate: " + annualInterestRate
                                + "\nNumber of Years: " + numberOfYears
                                + "\nLoan Amount: $" + loanAmount
                                + "\nMonthly Payment: $" + monthlyPayment
                                + "\nTotal Payment: $" + totalPayment + '\n');
                    });
                }
            }
            catch (IOException ex){
                ex.printStackTrace();
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
