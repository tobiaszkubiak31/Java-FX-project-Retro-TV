package pipboy;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static javafx.animation.Animation.INDEFINITE;


public class Controller {

    public AnchorPane anchorPane;

    public ToggleButton powerButton;

    public ImageView geigerPointer;
    private Timeline geigerPointerAnimation;

    public ImageView lights;

    public ImageView voltBoy;
    private double voltBoyDisplayTime;

    private int option;

    public ImageView RadioPointer;
    private double RadioPropertyY;

    public Label terminalText1;
    public Label terminalText2;
    public Label terminalTime;
    private Thread terminalThread;


    private MediaPlayer player ;


    void init(){
        initBackground();
        initPowerButton();
        initGeigerPointer();
        initLights();
        initVoltBoy();

        option = 0;
        RadioPropertyY = 425;

        initTerminalText();
        initRadioPointer();
        initTime();

        voltBoyDisplayTime = 4.0;
    }

    private void initTime() {
        terminalTime.setTranslateX(240);
        terminalTime.setTranslateY(350);

        final Font f;
        try {
            f = Font.loadFont(new FileInputStream(new File("src/pipboy/fonts/FSEX300.ttf")), 45);
            terminalTime.setFont(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        terminalTime.setOpacity(0.9);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(3.0);
        terminalTime.setTextFill(Color.web("#78f7a8"));
        terminalTime.setEffect(gaussianBlur);
        InnerShadow is = new InnerShadow();
        is.setColor(Color.GREEN);
        is.setChoke(0.01);
        is.setRadius(5);
        terminalTime.setEffect(is);

        DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis( 500 ),
                        event -> {
                            final long diff = System.currentTimeMillis();
                            if ( diff < 0 ) {
                                //  timeLabel.setText( "00:00:00" );
                                terminalTime.setText( "TIME: " + timeFormat.format( 0 ) + "\n" + "DATE: 13 NOV 2019");
                            } else {
                                terminalTime.setText( "TIME: " + timeFormat.format( diff ) + "\n" + "DATE: 13 NOV 2019");
                            }
                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();
        terminalTime.setVisible(false);
    }

    private void initTerminalText() {

        anchorPane.getStyleClass().add("falloutFont");
        terminalText1.setText("CURRENT STATION:");
        terminalText2.setText("NONE");

        final Font f;
        try {
            f = Font.loadFont(new FileInputStream(new File("src/pipboy/fonts/FSEX300.ttf")), 45);
            terminalText1.setFont(f);
            terminalText2.setFont(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        terminalText1.setOpacity(0.9);
        terminalText2.setOpacity(0.9);

        terminalText1.setTextFill(Color.web("#78f7a8"));
        terminalText2.setTextFill(Color.web("#78f7a8"));
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(3.0);
        terminalText1.setEffect(gaussianBlur);
        terminalText2.setEffect(gaussianBlur);
        InnerShadow is = new InnerShadow();
        is.setColor(Color.GREEN);
        is.setChoke(0.01);
        is.setRadius(5);
        terminalText1.setEffect(is);
        terminalText2.setEffect(is);

        terminalText1.setTranslateX(240);
        terminalText1.setTranslateY(130);
        terminalText2.setTranslateX(240);
        terminalText2.setTranslateY(190);

        terminalText1.setVisible(false);
        terminalText2.setVisible(false);
    }

    private void initRadioPointer() {
        RadioPointer.setImage(new Image("/pipboy/img/radioPointer.png"));
        RadioPointer.setFitHeight(-1);
        RadioPointer.setFitWidth(-1);
        RadioPointer.setTranslateX(1030);
        RadioPointer.setTranslateY(RadioPropertyY);
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

    private void updateTerminalText(int option) {
        switch (option) {
            case 1:
                terminalText2.setText("STATION X");
                break;
            case 2:
                terminalText2.setText("STATION Y");
                break;
            case 3:
                terminalText2.setText("STATION Z");
                break;
            case 0:
                terminalText2.setText("NONE");
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
                    break;
                case 2:
                    updateTerminalText(2);
                    playMusic(2);
                    break;
                case 3:
                    updateTerminalText(3);
                    playMusic(3);
                    break;
                case 0:
                    updateTerminalText(0);
                    playMusic(0);
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

    private void stopMusic(){
        player.stop();
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
        voltBoy.setTranslateX(330);
        voltBoy.setTranslateY(120);
        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#104229"));
        is.setRadius(60);
        voltBoy.setEffect(is);
        hideVoltBoy();
    }

    private void showVoltBoy(){
        voltBoy.setVisible(true);
        Timeline t = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(voltBoy.opacityProperty(), 0.0)),
                new KeyFrame(Duration.seconds(voltBoyDisplayTime), new KeyValue(voltBoy.opacityProperty(), 0.6))
        );
        t.play();
    }

    private void hideVoltBoy(){
        voltBoy.setVisible(false);
    }

    private void turnOnLights(){
        ColorAdjust ca = (ColorAdjust)lights.getEffect();
        double animTime = Math.abs(ca.getSaturation()) + 0.1 + 0.5;
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

    private void turnOnTerminal(){
        terminalText1.setVisible(true);
        terminalText2.setVisible(true);
        terminalTime.setVisible(true);
        hideVoltBoy();
    }

    private void turnOffTerminal(){
        terminalThread.interrupt();
        terminalText1.setVisible(false);
        terminalText2.setVisible(false);
        terminalTime.setVisible(false);
    }

    private void turnOnTerminalAfterDelay(){
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws InterruptedException{
                Thread.sleep((long)voltBoyDisplayTime * 1000);
                turnOnTerminal();
                return null;
            }
        };
        terminalThread = new Thread(sleeper);
        terminalThread.start();
    }

    private void powerON(){
        showVoltBoy();
        turnOnLights();
//        discoMode();
        setPowerButtonON();
        turnOnGeigerPointer();
        turnOnTerminalAfterDelay();
        //playMusic(0);
    }

    private void powerOFF(){
        hideVoltBoy();
        turnOffLights();
        setPowerButtonOFF();
        turnOffGeigerPointer();
        turnOffTerminal();
        stopMusic();
    }

    public void powerButtonOnClick() {
        if (powerButton.isSelected()) {
            powerON();
        }
        else {
            powerOFF();
        }
    }

}
