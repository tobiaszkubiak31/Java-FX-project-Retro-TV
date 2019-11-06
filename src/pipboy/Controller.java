package pipboy;

import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;


public class Controller {

    public AnchorPane anchorPane;

    public ToggleButton powerButton;

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


        powerButton.setTranslateX(155);
        powerButton.setTranslateY(540);
        powerButton.setStyle(powerOFF);
        powerButton.setPickOnBounds(false);

        //        powerUpButton.setRotationAxis(Rotate.X_AXIS);
        //        powerUpButton.setRotate(40);
        //        powerUpButton.setRotationAxis(Rotate.Z_AXIS);
        //        powerUpButton.setRotate(40);
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
