package pipboy;

import javafx.animation.*;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import static javafx.animation.Animation.INDEFINITE;


public class Controller {

    public AnchorPane anchorPane;

    public ToggleButton powerButton;

    public ImageView geigerPointer;

    public ImageView lights;


    public void initialize(){
        initBackground();
        initPowerButton();
        initGeigerPointer();
        initLights();
    }

    private void initBackground(){
        anchorPane.getStyleClass().add("backgroundPipBoy");
    }

    private void initPowerButton(){
        powerButton.setTranslateX(155);
        powerButton.setTranslateY(540);
        powerButton.setPickOnBounds(false);
        setPowerButtonOFF();
    }

    private void initGeigerPointer(){
        geigerPointer.setImage(new Image("/pipboy/img/geigerPointer.png"));
        geigerPointer.setTranslateX(843);
        geigerPointer.setTranslateY(142);
        geigerPointer.getTransforms().add(new Rotate(-70.0, 0, 0));

        Rotate pointerRotation = new Rotate(0.0, 0, 0);
        geigerPointer.getTransforms().add(pointerRotation);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pointerRotation.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(pointerRotation.angleProperty(), 100))
        );

        timeline.cycleCountProperty().setValue(INDEFINITE);   //loop animation
        timeline.autoReverseProperty().setValue(true);
        timeline.play();
    }


    private void initLights(){
        lights.setFitWidth(-1);         // -1 so it doesn't fit size to the parent
        lights.setFitHeight(-1);
        lights.setImage(new Image("/pipboy/img/lights.png"));
        lights.setPreserveRatio(true);
        lights.setTranslateX(791);
        lights.setTranslateY(215);
        ColorAdjust ca = new ColorAdjust();
        ca.setSaturation(-1.0);          //set lights off
        lights.setEffect(ca);
    }

    private void turnOnLights(){
        ColorAdjust ca = (ColorAdjust)lights.getEffect();
        double animTime = Math.abs(ca.getSaturation()) + 0.1;
        Timeline t = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(ca.saturationProperty(), ca.getSaturation())),
                new KeyFrame(Duration.seconds(animTime), new KeyValue(ca.saturationProperty(), 0.1))
        );
        t.play();
    }

    private void turnOffLights(){
        ColorAdjust ca = (ColorAdjust)lights.getEffect();
        double animTime = Math.abs(ca.getSaturation()) + 1.0;
        Timeline t = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(ca.saturationProperty(), ca.getSaturation())),
                new KeyFrame(Duration.seconds(animTime), new KeyValue(ca.saturationProperty(), -1.0))
        );
        t.play();
    }

    private void discoMode(){
        ColorAdjust ca = new ColorAdjust();
        lights.setEffect(ca);
        Timeline t = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(ca.hueProperty(), -1.0)),
                new KeyFrame(Duration.seconds(4), new KeyValue(ca.hueProperty(), 1.0))
        );
        t.cycleCountProperty().setValue(INDEFINITE);    //loop animation
        t.autoReverseProperty().setValue(true);         //loop animation
        t.play();
    }

    private void setPowerButtonON(){
        String powerON =
                "-fx-background-radius: 100em;\n" +
                "-fx-min-width: 62px;\n" +
                "-fx-min-height: 52px;\n" +
                "-fx-focus-color: transparent;\n" +
                "-fx-background-image: url('/pipboy/img/powerON.png');";
        powerButton.setStyle(powerON);
    }

    private void setPowerButtonOFF(){
        String powerOFF =
                "-fx-background-radius: 100em;\n" +
                        "-fx-min-width: 62px;\n" +
                        "-fx-min-height: 52px;\n" +
                        "-fx-focus-color: transparent;\n" +
                        "-fx-background-image: url('/pipboy/img/powerOFF.png');";
        powerButton.setStyle(powerOFF);
    }

    public void powerButtonOnClick() {
        if(powerButton.isSelected()){
            setPowerButtonON();
//            turnOnLights();
            discoMode();
            System.out.println("POWER ON!");
        }
        else {
            setPowerButtonOFF();
            turnOffLights();
            System.out.println("POWER OFF!");
        }
    }

}
