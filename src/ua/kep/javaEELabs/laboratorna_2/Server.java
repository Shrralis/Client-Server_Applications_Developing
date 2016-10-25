package ua.kep.javaEELabs.laboratorna_2;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by shrralis on 10/11/16.
 */
public class Server {

    private BufferedReader in = null;
    private String str = null;
    private byte[] buffer;
    private DatagramPacket packet;
    private InetAddress address;
    private DatagramSocket socket;

    public Server() throws IOException {
        System.out.println("Sending messages");
        // Створюється об'єкт DatagramSocket, щоб
        // приймати запити клієнта
        socket = new DatagramSocket();
        // Виклик методу transmit(), щоб передавати повідомлення усім
        // клієнтам, зареєстрованим в групі
        transmit();
    }
    public void transmit() {
        try {
            // створюється вхідний потік, щоб приймати
            // дані з консолі

            while (true) {
                File file = new File("src/" + this.getClass().getPackage().getName().replace(".", "/")
                        + "/serverDatafile.rrs");
                // Створення об'єкту для передачі клієнтам
                if (file.exists()) {
                    in = new BufferedReader(new InputStreamReader(System.in));

                    System.out.print("Натисніть Enter для відправки повідомлення з файлу");
                    in.readLine();

                    in = new BufferedReader(new FileReader(file));
                    str = in.readLine();
                } else {
                    in = new BufferedReader(new InputStreamReader(System.in));

                    System.out.print("Введіть рядок для передачі клієнтам: ");

                    str = in.readLine();
                }

                buffer = str.getBytes();
                address = InetAddress.getByName("233.0.0.1");
                // Відправка пакету датаграм на порт номер 1502
                packet = new DatagramPacket(buffer, buffer.length, address,
                        1502);
                // Відправка повідомлень усіх клієнтів в групі
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Закриття потоку і сокета
                in.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String arg[]) throws Exception {
        // Запуск сервера
        new Server();
    }
}
