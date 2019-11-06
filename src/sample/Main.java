package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1000, 1000);

        // Setting background
        BackgroundImage pipBoy = new BackgroundImage(
                new Image("pip-boy.jpg"),                           // image
                BackgroundRepeat.NO_REPEAT,                             // repeatX
                BackgroundRepeat.NO_REPEAT,                             // repeatY
                BackgroundPosition.CENTER,                              // position
                new BackgroundSize(-1, -1, false, false, false, true)
        );
        anchorPane.setBackground(new Background(pipBoy));


        //TEST BUTTON
        Button powerUpButton = new Button();
        powerUpButton.setTranslateX(200);
        powerUpButton.setTranslateY(600);
        powerUpButton.setText("TEST BUTTON");
        anchorPane.getChildren().add(powerUpButton);


        //Creating a Group object
        Group root = new Group(anchorPane);

        //Creating a scene object
        Scene scene = new Scene(root, 1000, 1000);

        //Setting title to the Stage
        stage.setTitle("Pip Boy");

        //No resize
        stage.setResizable(false);

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
