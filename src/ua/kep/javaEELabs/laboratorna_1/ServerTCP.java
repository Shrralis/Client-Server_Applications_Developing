package ua.kep.javaEELabs.laboratorna_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by shrralis on 10/11/16.
 */
public class ServerTCP extends Thread {
    // Оголошується посилання
    // на об'єкт - сокет сервера
    ServerSocket serverSocket = null;
    /**
     * * Конструктор за умовчанням
     */
    public ServerTCP() {
        try {
            // Створюється об'єкт ServerSocket, який отримує
            // запити клієнта на порт 1500
            serverSocket = new ServerSocket(1500);

            System.out.println("Сервер запущений ");
            // Запускаємо процес
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * * Запуск процесу
     */
    public void run() {
        try {
            while (true) {
                // Очікування запитів з'єднання від клієнтів
                Socket clientSocket = serverSocket.accept();

                System.out.println("Підключення відбувається від клієнта з IP адресою "
                        + clientSocket.getInetAddress().getHostAddress());
                // Отримання вихідного потоку,
                // пов'язаного з об'єктом Socket
                ObjectOutputStream out = new ObjectOutputStream(
                        clientSocket.getOutputStream());
                DateMessage dateMessage;
                File file = new File("src/" + this.getClass().getPackage().getName().replace(".", "/")
                        + "/serverDatafile.rrs");
                // Створення об'єкту для передачі клієнтам
                if (file.exists()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String date = bufferedReader.readLine();
                    String message = bufferedReader.readLine();
                    dateMessage = new DateMessage(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(date), message);
                } else {
                    dateMessage = new DateMessage(Calendar
                            .getInstance().getTime(), "Поточна дата/час на сервері");
                }
                // Запис об'єкту у вихідний потік
                out.writeObject(dateMessage);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
    // Запуск сервера
        new ServerTCP();
    }
}
