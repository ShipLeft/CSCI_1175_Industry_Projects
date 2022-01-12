import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * <h1>FlagRisingAnimation</h1>
 *
 * <p>This class will create the pane, find the image, and start the application.
 * This will also use an object from the FlagRaising class.</p>
 *
 * <p>Created 01/12/2022</p>
 *
 * @author Rhett Boatright
 */
public class FlagRisingAnimation extends Application {
    private Pane pane = new Pane(); //Pane to be used
    private ImageView imageView = new ImageView("image/us.gif"); //

    @Override // Override the start method in the Application class
    /**
     * This method will create the Runnable FlagRaising object to use a thread to raise the flag.
     * It will also create the Scene with the pane and set the Scene into the primaryStage.
     */
    public void start(Stage primaryStage) {
        Runnable flag = new FlagRaising(pane, imageView);
        Thread flagThread = new Thread(flag);
        flagThread.start();

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 250, 200);
        primaryStage.setTitle("FlagRisingAnimation"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * Main method for use in IDEs.
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}

/**
 * <h1>FlagRaising</h1>
 *
 * <p>This class will make the flag animation run on a thread instead of running it normally.</p>
 *
 * <p>Created: 01/12/2022</p>
 *
 * @author Rhett Boatright
 */
class FlagRaising implements Runnable{
    private Pane pane; //To be set to the pane from the FlagRisingAnimation class
    ImageView imageToBeUsed; //Image to be set to the image from the FlagRisingAnimation class

    /**
     * Constructor of the FlagRaising object.
     *
     * @param p (Pane; pane from the FlagRisingAnimation class)
     * @param i (ImageView; image from the FlagRisingAnimation class)
     */
    public FlagRaising(Pane p, ImageView i){
        pane = p;
        imageToBeUsed = i;
    }

    @Override
    /**
     * This method will run the flag rising animation on a thread.
     */
    public void run() {
        pane.getChildren().add(imageToBeUsed); //Add the image to the pane.

        // Create a path transition
        PathTransition pt = new PathTransition(Duration.millis(10000),
                new Line(100, 200, 100, 0), imageToBeUsed); pt.setCycleCount(5);
        pt.play(); // Start animation
    }
}