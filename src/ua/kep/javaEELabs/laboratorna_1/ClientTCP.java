package ua.kep.javaEELabs.laboratorna_1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by shrralis on 10/11/16.
 */
public class ClientTCP extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ClientTCP");
        primaryStage.setWidth(384);
        primaryStage.setHeight(256);

        BorderPane root = new BorderPane();
        VBox vBox = new VBox(16);

        vBox.setTranslateX(32);
        vBox.setTranslateY(64);

        HBox hBox = new HBox(8);
        Label labelDateTitle = new Label("Date on the server: ");
        Label labelDateData = new Label("N/A");

        hBox.getChildren().addAll(labelDateTitle);
        hBox.getChildren().addAll(labelDateData);
        vBox.getChildren().addAll(hBox);

        hBox = new HBox(8);
        Label labelMessageTitle = new Label("Message from the server: ");
        Label labelMessageData = new Label("N/A");

        hBox.getChildren().addAll(labelMessageTitle);
        hBox.getChildren().addAll(labelMessageData);
        vBox.getChildren().addAll(hBox);

        Button button = new Button("Get server data");

        button.setPrefSize(112, 24);
        button.setOnAction(event -> {
            try {
                // Створюється об'єкт Socket
                // для з'єднання з сервером
                Socket clientSocket = new Socket("localhost", 1500);
                // Отримуємо посилання на потік, пов'язаний з сокетом
                ObjectInputStream in = new ObjectInputStream(
                        clientSocket.getInputStream());
                // Витягаємо об'єкт з вхідного потоку
                DateMessage dateMessage = ((DateMessage) in.readObject());
                // Виводимо отримані дані в консоль
                System.out.println(dateMessage.getMessage());
                labelDateData.setText(dateMessage.getDate().toString());
                System.out.println(dateMessage.getDate());
                labelMessageData.setText(dateMessage.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vBox.getChildren().addAll(button);

        Scene scene = new Scene(root);

        root.setCenter(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
