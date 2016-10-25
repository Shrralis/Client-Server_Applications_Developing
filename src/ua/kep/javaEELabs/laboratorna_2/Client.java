package ua.kep.javaEELabs.laboratorna_2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by shrralis on 10/11/16.
 */
public class Client extends Application {

    private static InetAddress address;
    private static byte[] buffer;
    private static DatagramPacket packet;
    private static String str;
    private static MulticastSocket socket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ClientTCP");
        primaryStage.setWidth(464);
        primaryStage.setHeight(218);

        BorderPane root = new BorderPane();
        VBox vBox = new VBox(16);

        vBox.setTranslateX(32);
        vBox.setTranslateY(64);

        HBox hBox = new HBox(8);
        Label labelMessageTitle = new Label("Message from the server: ");
        Label labelMessageData = new Label("N/A");

        hBox.getChildren().addAll(labelMessageTitle);
        hBox.getChildren().addAll(labelMessageData);
        vBox.getChildren().addAll(hBox);

        Button button = new Button("Get server message");

        button.setPrefSize(136, 24);
        button.setOnAction(event -> {
            System.out.println("Очікування повідомлення від сервера");

            try {
                // Створення об'єкту MulticastSocket, щоб отримувати
                // дані від групи, використовуючи номер порту 1502
                socket = new MulticastSocket(1502);
                address = InetAddress.getByName("233.0.0.1");
                // Реєстрація клієнта в групі
                socket.joinGroup(address);

                buffer = new byte[256];
                packet = new DatagramPacket(buffer, buffer.length);
                // Отримання даних від сервера
                socket.receive(packet);
                str = new String(packet.getData());

                System.out.println("Отримано повідомлення: " + str.trim());
                labelMessageData.setText(str.trim());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // Видалення клієнта з групи
                    socket.leaveGroup(address);
                    // Закриття сокета
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        vBox.getChildren().addAll(button);

        Scene scene = new Scene(root);

        root.setCenter(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String arg[]) throws Exception {
        launch(arg);
    }
}
