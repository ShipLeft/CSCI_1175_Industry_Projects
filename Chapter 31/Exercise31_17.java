import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.text.DecimalFormat;

/**
 * <h1>Exercise31_17</h1>
 *
 * <p>This class will create an investment calculator GUI that will allow a
 * user to input how much they invested, how many years they will invest,
 * and what the rate of interest is on that investment. It will then return
 * the future value if all of those numbers stay the same throughout the investment.</p>
 *
 * <p>Created: 01/13/2022</p>
 *
 * @author Rhett Boatright
 */
public class Exercise31_17 extends Application{
    private Pane pane = new Pane();
    private VBox vBox = new VBox();
    private Investment investment = new Investment();
    private Button calculate = new Button("Calculate");
    private GridPane gridPane = new GridPane();
    private MenuItem calculateItem = new MenuItem("Calculate");
    private MenuItem exit = new MenuItem("Exit");
    private TextField iAF = new TextField();
    private TextField nOY = new TextField();
    private TextField aIR = new TextField();
    private TextField fV = new TextField();

    /**
     * Main method only needed for IDEs with limited function.
     *
     * @param args (String; placeholder for the main method)
     */
    public static void main(String[] args){
        launch(args);
    }

    @Override
    /**
     * This method will create the GUI and will tell what each option, button, or textField will do
     * when selected or typed in. It will also call upon displayCalculator when asked to calculate, and
     * will use the Investment class to achieve the correct calculations.
     */
    public void start(Stage primaryStage) throws Exception {
        fV.setEditable(false);
        iAF.setAlignment(Pos.BOTTOM_RIGHT);
        nOY.setAlignment(Pos.BOTTOM_RIGHT);
        aIR.setAlignment(Pos.BOTTOM_RIGHT);
        fV.setAlignment(Pos.BOTTOM_RIGHT);

       MenuBar operation = new MenuBar();
       vBox.getChildren().add(operation);
        pane.getChildren().add(vBox);
        Menu menuOperation = new Menu("Operation");
        operation.getMenus().add(menuOperation);
        menuOperation.getItems().addAll(
                calculateItem, exit);



        Scene scene = new Scene(pane, 300, 200); //Create a scene with the pane set inside
        primaryStage.setTitle("Exercise31_17"); //Set the title of the stage
        primaryStage.setScene(scene); //Set scene inside of the stage
        primaryStage.show(); //Show the stage

        calculateItem.setOnAction(e->{ //When the calculateItem menu item is selected
                displayCalculator(); //Display the Investment calculator

        });
        exit.setOnAction(e ->{ //When the exit menu item is selected
            System.exit(0); //Exit with code 0
        });

        calculate.setOnAction(e ->{ //When calculate is pressed
            //Collect all information and Parse it from string to double or int
            String investmentText = iAF.getText();
            investment.setAmountInvested(Double.parseDouble(investmentText));
            String yearsText = nOY.getText();
            investment.setNumberOfYears(Integer.parseInt(yearsText));
            String interestText = aIR.getText();
            investment.setAnnualInterestRate(Double.parseDouble(interestText));

            //Get monthly interest and set the Future Value text field to the investment string
            investment.getMonthlyInterestRate();
            fV.setText(investment.getFutureValue());
        });
    }

    /**
     * This method will display the calculator if the option is selected.
     */
    public void displayCalculator(){
        HBox hBox = new HBox(); //HBox for the button placement
        gridPane.setHgap(5); //Gap between labels and text fields
        gridPane.setVgap(5); //Gap between different labels/text fields

        //Label and field for Investment Amount
        gridPane.add(new Label("Investment Amount:"),0,0);
        gridPane.add(iAF,1,0);

        //Label and field for Number of Years
        gridPane.add(new Label("Number of Years:"), 0 ,1);
        gridPane.add(nOY, 1,  1);

        //Label and field for Annual Interest Rate
        gridPane.add(new Label("Annual Interest Rate:"), 0, 2);
        gridPane.add(aIR, 1, 2);

        //Label and field for Future Value
        gridPane.add(new Label("Future Value:"), 0, 3);
        gridPane.add(fV, 1, 3);

        //Set each area into the respective spot
        vBox.getChildren().add(gridPane);
        hBox.getChildren().add(calculate);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        vBox.getChildren().add(hBox);
    }
}

/**
 * <h1>Investment</h1>
 *
 * <p>This class will create the constructor for the investment objects. This will allow for the
 * correct calculation of the Future value and will allow for the retention of the original values.</p>
 *
 * <p>Created 01/13/2022</p>
 *
 * @author Rhett Boatright
 */
class Investment{
    //Variables to be used for the investment
    private double amountInvested, annualInterestRate, monthlyInterestRate, futureValue;
    private int numberOfYears;

    //Constructor
    Investment(){
    }

    //Setter for Amount Invested
    void setAmountInvested(double amountInvested){
        this.amountInvested = amountInvested;
    }

    //Setter for the Number of Years
    void setNumberOfYears(int numberOfYears){
        this.numberOfYears = numberOfYears;
    }

    //Setter for the Annual Interest Rate.
    void setAnnualInterestRate(double annualInterestRate){
        this.annualInterestRate = annualInterestRate;
    }

    //Getter for the Monthly Interest Rate
    double getMonthlyInterestRate(){
        return this.monthlyInterestRate = (annualInterestRate / 100) / 12;
    }

    //Getter for the string for Future Value
    String getFutureValue(){
        futureValue = amountInvested * Math.pow((1 + monthlyInterestRate),(numberOfYears * 12));
        DecimalFormat df = new DecimalFormat("0.00");
        return "$"+ df.format(futureValue);
    }
}
