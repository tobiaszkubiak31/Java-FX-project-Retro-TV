package pipboy;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.nio.file.Paths;

import static javafx.animation.Animation.INDEFINITE;


public class Controller {

    public AnchorPane anchorPane;

    public ToggleButton powerButton;

    public ImageView geigerPointer;

    public ImageView lights;



    //gif and sound
    int option = 0;

    public ImageView RadioPointer;

    public double RadioPropertyY = 425;


    public ImageView myGif;

    AnimatedGifDemo.Animation a ;

    MediaPlayer player ;
    public void init(){

        initBackground();
        initPowerButton();
        initGeigerPointer();
        initLights();
        initGifs();
        initRadioPointer();
        playMusic(0);
    }


    @FXML
    private void handleKeyPressed(KeyEvent event){
        switch (event.getCode()) {
            case P:    RadioPropertyY-=2; RadioPointer.setTranslateY(RadioPropertyY); break;
            case L:  RadioPropertyY+=2; RadioPointer.setTranslateY(RadioPropertyY);  break;
        }
        int newOption ;

        if(RadioPropertyY>450)
            newOption = 1;
        else if(RadioPropertyY<430&&RadioPropertyY>420)
            newOption = 2;
        else if(RadioPropertyY<400)
            newOption = 3;
        else
            newOption = 0;


        if(option!=newOption){
            option = newOption;
            switch (option) {
                case 1:
                    updateGif(1);
                    playMusic(1);
                    break;
                case 2:
                    updateGif(2);
                    playMusic(2);
                    break;
                case 3:
                    updateGif(3);
                    playMusic(3);
                    break;
                case 0:
                    updateGif(0);
                    playMusic(0);
                    break;
            }
        }
    }






    private void updateGif(int option) {

        switch (option) {
            case 1:
                myGif.setImage(new Image("/pipboy/img/1.gif"));
                myGif.setFitHeight(300);
                myGif.setFitWidth(300);
                break;
            case 2:
                myGif.setImage(new Image("/pipboy/img/2.gif"));
                myGif.setFitHeight(300);
                myGif.setFitWidth(300);
                break;
            case 3:
                myGif.setImage(new Image("/pipboy/img/3.gif"));
                myGif.setFitHeight(300);
                myGif.setFitWidth(300);
                break;
            case 0:
                myGif.setImage(new Image("/pipboy/img/0.gif"));
                myGif.setFitHeight(300);
                myGif.setFitWidth(300);
                break;
        }
    }

    private void playMusic(int option) {
        //stop delete last music object
        if(player!=null)
            player.stop();
        player = null;
        System.gc();

        Media media ;
        switch (option) {
            case 1:
                media = new Media(Paths.get("src\\pipboy\\music\\1.mp3").toUri().toString());
                break;
            case 2:
                media = new Media(Paths.get("src\\pipboy\\music\\2.mp3").toUri().toString());
                break;
            case 3:
                media = new Media(Paths.get("src\\pipboy\\music\\3.mp3").toUri().toString());
                break;
            case 0:
                media = new Media(Paths.get("src\\pipboy\\music\\0.mp3").toUri().toString());
                break;
            default: media = new Media(Paths.get("src\\pipboy\\music\\0.mp3").toUri().toString());
                break;
        }
        player = new MediaPlayer(media);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();
    }


    private void initRadioPointer() {
        RadioPointer.setImage(new Image("/pipboy/img/geigerPointer.png"));
        RadioPointer.setFitHeight(41);
        RadioPointer.setFitWidth(23);
        RadioPointer.setTranslateX(1030);
        RadioPointer.setTranslateY(RadioPropertyY);
        RadioPointer.getTransforms().add(new Rotate(-35, 0, 0));


    }
    private void initGifs() {
        myGif.setImage(new Image("/pipboy/img/0.gif"));
        myGif.setFitHeight(300);
        myGif.setFitWidth(300);
        myGif.setTranslateX(270);
        myGif.setTranslateY(120);

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
            turnOnLights();
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
