package pipboy;

import javafx.animation.*;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public class Controller {

    public AnchorPane anchorPane;

    public ToggleButton powerButton;

    public ImageView pointer;

    private String powerOFF =
            "-fx-background-radius: 100em;\n" +
            "-fx-min-width: 62px;\n" +
            "-fx-min-height: 52px;\n" +
            "-fx-focus-color: transparent;\n" +
            "-fx-background-image: url('/pipboy/img/powerOFF.png');";

    private String powerON =
            "-fx-background-radius: 100em;\n" +
            "-fx-min-width: 62px;\n" +
            "-fx-min-height: 52px;\n" +
            "-fx-focus-color: transparent;\n" +
            "-fx-background-image: url('/pipboy/img/powerON.png');";

    public void initialize(){

        BackgroundImage pipBoy = new BackgroundImage(
                new Image("pipboy/img/pipboy.png"),                 // image
                BackgroundRepeat.NO_REPEAT,                             // repeatX
                BackgroundRepeat.NO_REPEAT,                             // repeatY
                BackgroundPosition.CENTER,                              // position
                new BackgroundSize(
                        -1,
                        -1,
                        false,
                        false,
                        false,
                        true
                )
        );
        anchorPane.setBackground(new Background(pipBoy));


        //POWER BUTTON
        powerButton.setTranslateX(155);
        powerButton.setTranslateY(540);
        powerButton.setStyle(powerOFF);
        powerButton.setPickOnBounds(false);


        //GEIGER COUNTER
        pointer.setImage(new Image("/pipboy/img/geigerPointer.png"));
        pointer.setTranslateX(843);
        pointer.setTranslateY(142);
        pointer.getTransforms().add(new Rotate(-70.0, 0, 0));

        Rotate pointerRotation = new Rotate(0.0, 0, 0);
        pointer.getTransforms().add(pointerRotation);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pointerRotation.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(pointerRotation.angleProperty(), 100))
        );

        timeline.cycleCountProperty().setValue(1000);   //loop 1000 times
        timeline.autoReverseProperty().setValue(true);
        timeline.play();
    }


    public void onClick() {
        if(powerButton.isSelected()){
            powerButton.setStyle(powerON);
            System.out.println("POWER ON!");
        }
        else {
            powerButton.setStyle(powerOFF);
            System.out.println("POWER OFF!");
        }

    }

}
