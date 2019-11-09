package pipboy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("pipboy.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root, 1200, 800);
        controller.init();

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Pip Boy");




        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
