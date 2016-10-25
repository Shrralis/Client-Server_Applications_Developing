package ua.kep.javaEELabs.laboratorna_1.additional_14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by shrralis on 10/11/16.
 */
public class ClientTCP {

    public static void main(String args[]) {
        try {
            // Створюється об'єкт Socket
            // для з'єднання з сервером
            Socket clientSocket = new Socket("localhost", 1500);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your line: ");
            String messageOut = bufferedReader.readLine();

            out.writeUTF(messageOut);
            out.flush();

            String messageIn = in.readUTF();
            System.out.println("Result: " + messageIn);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
