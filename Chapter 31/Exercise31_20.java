import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * <h1>Exercise31_20</h1>
 *
 * <p>This class is a modified version of TabPaneDemo.java that allows the user to decide the
 * placement of the tabs on the TabPane by use of a radioButton.</p>
 *
 * <p>Created 01/13/2022</p>
 *
 * @author Rhett Boatright
 */
public class Exercise31_20 extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        //BorderPane to set the tabPane and theHBox into
        BorderPane masterPane = new BorderPane();

        //HBox for the radio buttons
        HBox hBox = new HBox();

        //RadioButtons for direction of the tab Pane
        RadioButton top = new RadioButton("Top ");
        RadioButton left = new RadioButton("Left ");
        RadioButton bottom = new RadioButton("Bottom ");
        RadioButton right = new RadioButton("Right ");

        //Set the RadioButtons into the HBox
        hBox.getChildren().addAll(top, left, bottom, right);
        hBox.setAlignment(Pos.CENTER);

        //Toggle Group for the Buttons
        ToggleGroup direction = new ToggleGroup();
        top.setToggleGroup(direction);
        left.setToggleGroup(direction);
        bottom.setToggleGroup(direction);
        right.setToggleGroup(direction);

        //Make the tabPane and put all of the tabs into it with the shape panes.
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Line");
        StackPane pane1 = new StackPane();
        pane1.getChildren().add(new Line(10, 10, 80, 80));
        tab1.setContent(pane1);
        Tab tab2 = new Tab("Rectangle");
        tab2.setContent(new Rectangle(10, 10, 200, 200));
        Tab tab3 = new Tab("Circle");
        tab3.setContent(new Circle(50, 50, 20));
        Tab tab4 = new Tab("Ellipse");
        tab4.setContent(new Ellipse(10, 10, 100, 80));
        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);

        //Set the tabPane and the hBox into the masterPane
        masterPane.setCenter(tabPane);
        masterPane.setBottom(hBox);

        //Set the masterPane into the scene
        Scene scene = new Scene(masterPane, 300, 250);
        primaryStage.setTitle("DisplayFigure"); // Set the window title
        primaryStage.setScene(scene); // Place the scene in the window
        primaryStage.show(); // Display the window

        top.setOnAction(e ->{ //When the 'top' radioButton is pressed
            tabPane.setSide(Side.TOP); //Set the side to the top

            //Resize the window
            primaryStage.setWidth(300);
            primaryStage.setHeight(250);
        });

        left.setOnAction(e ->{ //When the 'left' radioButton is pressed
            tabPane.setSide(Side.LEFT); //Set the side to the left

            //Resize the window
            primaryStage.setWidth(250);
            primaryStage.setHeight(300);
        });

        bottom.setOnAction(e ->{ //When the 'bottom' radioButton is pressed
            tabPane.setSide(Side.BOTTOM); //Set the side to the bottom

            //Resize the window
            primaryStage.setWidth(300);
            primaryStage.setHeight(250);
        });

        right.setOnAction(e ->{ //When the 'right' radioButton is pressed
            tabPane.setSide(Side.RIGHT); //Set the side to the right

            //Resize the window
            primaryStage.setWidth(250);
            primaryStage.setHeight(300);
        });
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     * line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}