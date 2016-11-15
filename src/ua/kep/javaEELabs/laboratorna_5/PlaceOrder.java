package ua.kep.javaEELabs.laboratorna_5;

import java.sql.*;

/**
 * Created by shrralis on 11/15/16.
 */
public class PlaceOrder {

    public static void main(String args[]) {
        try {
            /*ЖИРАВОВ: ПРИМІТКА! У ЗВ'ЯЗКУ З ТИМ, ЩО В JDK8 МІСТ JDBC-ODBC БУВ ВИЛУЧЕНИЙ,
            ДОВОДИТЬСЯ ВИКОРИСТОВУВАТИ ЗОВНІШНІ БІБЛІОТЕКИ (UCanAccess 3.0.6)*/

            /* Установити зв'язок з базою даних */
            Connection con = DriverManager.getConnection(
                    "jdbc:ucanaccess:///home//shrralis//Documents//Study//Розробка застосувань клієнт-серверної архітектури//" +
                            "Лабораторні роботи//Pubs(Для ЛР_5).mdb");
            /* Створити об' єкт Statement */
            Statement stmt = con.createStatement();
            con.setAutoCommit(false);
            /* Додати оператор INSERT в пакет */
            stmt.addBatch("INSERT INTO product (p_id, p_desc) VALUES (1022, 'Printer')");
            stmt.addBatch("INSERT INTO product (p_id, p_desc) VALUES (1023, 'Scanner')");
            stmt.addBatch("INSERT INTO product (p_id, p_desc) VALUES (1024, 'Keyboard')");
            /* Виконати пакет, використовуючи метод executeBatch() */
            int[] results = stmt.executeBatch();
            System.out.println("");
            System.out.println("Використання об’єкту Statement");
            System.out.println("--------------------------");
            for (int i = 0; i < results.length; i++) {
                System.out.println("Rows affected by " + (i + 1)
                        + " INSERT statement: " + results[i]);
            }
            stmt.close();
            /*
            * Використовувати об' єкт PreparedStatement, щоб виконати пакет
            * оновлень
            */
            PreparedStatement ps = con.prepareStatement("INSERT INTO product (p_id, p_desc) VALUES (?, ?)");
            System.out.println("");
            System.out.println("----------------------------------");
            System.out.println("Використання об’єкту PreparedStatement");
            System.out.println("----------------------------------");
            /* Визначити значення для заповнювачів */
            ps.setInt(1, 1025);
            ps.setString(2, "Mouse");
            ps.addBatch();
            ps.setInt(1, 1026);
            ps.setString(2, "Monitor");
            /* Додати оператор SQL до пакету */
            ps.addBatch();
            /* Виконати пакет операторів SQL */
            int[] numUpdates = ps.executeBatch();
            for (int i = 0; i < numUpdates.length; i++) {
                System.err.println("Rows affected by " + (i + 1)
                        + " INSERT statement: " + numUpdates[i]);
            }
            /* Фіксувати оператори INSERT в пакеті */
            con.commit();
            /* Закрити об'єкт Connection */
            con.close();
        } catch (BatchUpdateException bue) {
            System.out.println("Помилка: " + bue);
        } catch (SQLException sqle) {
            System.out.println("Помилка: " + sqle);
        } catch (Exception e) {
            System.out.println("Помилка: " + e);
        }
    }
}
