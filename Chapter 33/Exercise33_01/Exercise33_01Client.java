// Exercise31_01Client.java: The client sends the input to the server and receives
// result back from the server
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * <h1>Exercise33_01Client</h1>
 *
 * <p>This class will make a GUI that will allow a user to input information into textFields, will collect and
 * send the data to the Exercise33_01Server, and will read the information that the server sends back to the client.
 * The program will then append all of the inputted information and the received information to the textArea.</p>
 *
 * <p>Created: 01/12/2022</p>
 *
 * @author Rhett Boatright
 */
public class Exercise33_01Client extends Application {
    // Text field for receiving radius
    private TextField tfAnnualInterestRate = new TextField();
    private TextField tfNumOfYears = new TextField();
    private TextField tfLoanAmount = new TextField();
    private Button btSubmit= new Button("Submit");

    // Text area to display contents
    private TextArea ta = new TextArea();

    //Socket to connect to the server
    private Socket clientSocket;

    //Reader for input
    private DataInputStream input;

    //OutputStream for writing to server
    private DataOutputStream output;

    @Override // Override the start method in the Application class
    /**
     * This method will create the GUI and will send, gather and append all information to and from the server.
     */
    public void start(Stage primaryStage) {
        ta.setWrapText(true);

        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Annual Interest Rate"), 0, 0);
        gridPane.add(new Label("Number Of Years"), 0, 1);
        gridPane.add(new Label("Loan Amount"), 0, 2);
        gridPane.add(tfAnnualInterestRate, 1, 0);
        gridPane.add(tfNumOfYears, 1, 1);
        gridPane.add(tfLoanAmount, 1, 2);
        gridPane.add(btSubmit, 2, 1);

        tfAnnualInterestRate.setAlignment(Pos.BASELINE_RIGHT);
        tfNumOfYears.setAlignment(Pos.BASELINE_RIGHT);
        tfLoanAmount.setAlignment(Pos.BASELINE_RIGHT);

        tfLoanAmount.setPrefColumnCount(5);
        tfNumOfYears.setPrefColumnCount(5);
        tfLoanAmount.setPrefColumnCount(5);

        BorderPane pane = new BorderPane();
        pane.setCenter(new ScrollPane(ta));
        pane.setTop(gridPane);

        btSubmit.setOnAction(e -> { //When the submit button is pressed
            try{

                //Get all of the information from the inputted data
                String aIRS = tfAnnualInterestRate.getText();
                String nOYS = tfNumOfYears.getText();
                String lAS = tfLoanAmount.getText();

                //Convert all of the data to doubles or ints
                double aIR = Double.parseDouble(aIRS);
                int nOY = Integer.parseInt(nOYS);
                double lA = Double.parseDouble(lAS);

                //Write the information to the server
                output.writeDouble(aIR);
                output.flush();
                output.writeInt(nOY);
                output.flush();
                output.writeDouble(lA);
                output.flush();

                //Gather the data from the server
                double monthlyPayment = input.readDouble();
                double totalPayment = input.readDouble();
                ta.appendText("Annual Interest Rate: " + aIR
                + "\nNumber of Years: " + nOY
                + "\nLoan Amount: $" + lA
                + "\nMonthly Payment: $" + monthlyPayment
                + "\nTotal Payment: $" + totalPayment + '\n');
            }
            catch (IOException ex){ //Catch any IOException error
                System.err.println(ex);
            }

        });

        try{
            clientSocket = new Socket("localhost", 8000); //host and port for the server

            //Input stream for data from server
            input = new DataInputStream(clientSocket.getInputStream());

            //Output stream for data to server
            output = new DataOutputStream(clientSocket.getOutputStream());
        }
        catch (IOException ex){
            ta.appendText(ex.toString() + '\n');
        }



        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 400, 250);
        primaryStage.setTitle("Exercise31_01Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
