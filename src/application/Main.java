package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	//@FXML private ComboBox<String> comboBox;
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = FXMLLoader.load(getClass().getResource("seisekihyou.fxml"));
			Scene scene = new Scene(root,700,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			/*
			comboBox.getItems().addAll(
					"id",
					"name",
					"mathScore",
					"kokugoScore"
			);
			*/

			primaryStage.setScene(scene);
			primaryStage.show();



			//App app = new App();
			//app.init();



		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
