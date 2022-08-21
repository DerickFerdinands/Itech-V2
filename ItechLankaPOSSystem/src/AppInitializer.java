import Util.FactoryConfigurations;
import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FactoryConfigurations.getInstance().getSession();
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("View/DashBoardForm.fxml"))));
        primaryStage.getIcons().add(new Image("Assets/11606da5a97ab6aa78fc81dd4a12b394.jpg"));
        primaryStage.setTitle("Itech Lanka Pvt Ltd POS System");
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
          System.exit(0);
        });
    }
}
