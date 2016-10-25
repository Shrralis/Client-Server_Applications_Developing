package ua.kep.javaEELabs.laboratorna_1.additional_14;

import java.io.*;
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

                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                String messageIn = in.readUTF();
                String messageOut;
                StringBuilder stringBuilder = new StringBuilder(messageIn.length());

                System.out.println("Message from the client: " + messageIn
                        + " (length: " + messageIn.length() + ")");
                System.out.print("Characters with indexes that is % 2: " + messageIn.charAt(0));
                stringBuilder.append(messageIn.charAt(0));

                for (int i = 1; i < messageIn.length(); i++) {
                    if (i % 2 == 0) {
                        System.out.print(", " + messageIn.charAt(i));
                        stringBuilder.append(messageIn.charAt(i));
                    } else {
                        stringBuilder.append(String.valueOf(messageIn.charAt(i)).toUpperCase());
                    }
                }
                System.out.println();

                messageOut = stringBuilder.toString();

                out.writeUTF(messageOut);
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
