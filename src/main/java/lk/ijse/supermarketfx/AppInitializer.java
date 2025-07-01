package lk.ijse.supermarketfx;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {

        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

//        stage.setScene( new FXMLLoader(getClass().getResource("/view/LoadPage.fxml")).load()
//        );

        Parent loadPage = FXMLLoader.load(getClass().getResource("/view/LoadPage.fxml"));
        stage.setScene(new Scene(loadPage));
        stage.show();

        Task<Scene> loadingTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashboard.fxml"));
                return  new Scene(fxmlLoader.load());
            }
        };

        loadingTask.setOnSucceeded(e -> {
            Scene value =loadingTask.getValue();

            stage.setTitle("Dashboard");
            stage.setScene(value);
        });

        loadingTask.setOnFailed(e -> {
            System.out.println("Error loading");
        });

        new Thread(loadingTask).start();

//        Parent parent = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
//        Scene scene = new Scene(parent);
//        stage.setScene(scene);
//        stage.setTitle("Supermarket");
//        stage.show();
    }
}
