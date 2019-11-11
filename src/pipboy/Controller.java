package pipboy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import static javafx.animation.Animation.INDEFINITE;


public class Controller {

    public AnchorPane anchorPane;

    public ToggleButton powerButton;

    public ImageView geigerPointer;
    private Timeline geigerPointerAnimation;

    public ImageView lights;

    public ImageView voltBoy;

    private int option = 0;

    public ImageView RadioPointer;

    private double RadioPropertyY = 425;

    public Label TerminalText1;

    public Label TerminalText2;

    public Label Time;

    private MediaPlayer player ;


    void init(){

        initBackground();
        initPowerButton();
        initGeigerPointer();
        initLights();

        initTerminalText();
        initRadioPointer();
        initTime();
        playMusic(0);



    }

    private void initTime() {
        Time.setTranslateX(685);
        Time.setTranslateY(550);

        final Font f;
        try {
            f = Font.loadFont(new FileInputStream(new File("src\\pipboy\\fonts\\AM_TP001.ttf")), 25);
            Time.setFont(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis( 500 ),
                        event -> {
                            final long diff = System.currentTimeMillis();
                            if ( diff < 0 ) {
                                //  timeLabel.setText( "00:00:00" );
                                Time.setText( timeFormat.format( 0 ) );
                            } else {
                                Time.setText( timeFormat.format( diff ) );
                            }
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
        Time.setVisible(false);
    }

    private void initTerminalText() {

        anchorPane.getStyleClass().add("falloutFont");
        TerminalText2.setText("Current station");
        TerminalText1.setText("None");

        final Font f;
        try {
            f = Font.loadFont(new FileInputStream(new File("src\\pipboy\\fonts\\OverseerBoldItalic.ttf")), 60);
            TerminalText1.setFont(f);
            TerminalText2.setFont(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TerminalText1.setTranslateX(190);
        TerminalText1.setTranslateY(300);
        TerminalText2.setTranslateX(250);
        TerminalText2.setTranslateY(150);

        TerminalText1.setVisible(false);
        TerminalText2.setVisible(false);
    }



    private void updateTerminalText(int option) {
        switch (option) {
            case 1:
                TerminalText1.setText("Station X");
                break;
            case 2:
                TerminalText1.setText("Station Y");
                break;
            case 3:
                TerminalText1.setText("Station Z");
                break;
            case 0:
                TerminalText1.setText("None");
                break;
        }
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
                    updateTerminalText(1);
                    playMusic(1);
                    //updateGif(int 1);
                    break;
                case 2:
                    updateTerminalText(2);
                    playMusic(2);
                    //updateGif(int 2);
                    break;
                case 3:
                    updateTerminalText(3);
                    playMusic(3);
                    //updateGif(int 3);
                    break;
                case 0:
                    updateTerminalText(0);
                    playMusic(0);
                    //updateGif(int 0);
                    break;
            }
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
        Rotate r = new Rotate(30.0, 0, 0);
        geigerPointer.getTransforms().add(r);
        geigerPointerAnimation = new Timeline(
                new KeyFrame(Duration.seconds(1.0), new KeyValue(r.angleProperty(), r.angleProperty().get() - 70)),
                new KeyFrame(Duration.seconds(2.0), new KeyValue(r.angleProperty(), r.angleProperty().get() - 50)),
                new KeyFrame(Duration.seconds(3.0), new KeyValue(r.angleProperty(), r.angleProperty().get() - 20)),
                new KeyFrame(Duration.seconds(3.5), new KeyValue(r.angleProperty(), r.angleProperty().get() - 50)),
                new KeyFrame(Duration.seconds(4.0), new KeyValue(r.angleProperty(), r.angleProperty().get() - 10)),
                new KeyFrame(Duration.seconds(4.5), new KeyValue(r.angleProperty(), r.angleProperty().get() - 30)),
                new KeyFrame(Duration.seconds(5.0), new KeyValue(r.angleProperty(), r.angleProperty().get() - 20))
        );
        geigerPointerAnimation.cycleCountProperty().setValue(INDEFINITE);   //loop animation
        geigerPointerAnimation.autoReverseProperty().setValue(true);
    }

    private void turnOnGeigerPointer(){
        geigerPointerAnimation.play();

    }

    private void turnOffGeigerPointer(){
        geigerPointerAnimation.pause();
    }

    private void initVoltBoy() {
        voltBoy.setImage(new Image("/pipboy/img/voltBoy.gif"));
        voltBoy.setFitHeight(-1);
        voltBoy.setFitWidth(-1);
        voltBoy.setTranslateX(270);
        voltBoy.setTranslateY(120);
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException ex){ex.printStackTrace();}
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

    private void powerON(){
        setPowerButtonON();
        turnOnLights();
        TerminalText1.setVisible(true);
        TerminalText2.setVisible(true);
        Time.setVisible(true);
        turnOnGeigerPointer();
        //initVoltBoy();
    }

    private void powerOFF(){
        setPowerButtonOFF();
        turnOffLights();
        TerminalText1.setVisible(false);
        TerminalText2.setVisible(false);
        Time.setVisible(false);
        turnOffGeigerPointer();
    }

    public void powerButtonOnClick() {
        if(powerButton.isSelected()){
            powerON();
        }
        else{
            powerOFF();
        }
    }





    ///gifs
    /*
     private void initGifs() {
        myGif.setImage(new Image("/pipboy/img/0.gif"));
        myGif.setFitHeight(300);
        myGif.setFitWidth(300);
        myGif.setTranslateX(270);
        myGif.setTranslateY(120);

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
        */

}
