package ua.kep.javaEELabs.laboratorna_4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by shrralis on 10/11/16.
 */
public class AuthorsInfo {

    public static void main(String args[]) {
        try {
            String str = "SELECT * FROM authors WHERE city LIKE 'O%'";
            /*
            * Ініціалізувати і завантажити драйвер Міст JDBC - ODBC
            */
            Class.forName("com.mysql.jdbc.Driver");
            /* Установити зв'язок з базою данных */
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ced", "root", "zolotorig91");

            /*ЖИРАВОВ: ПРИМІТКА! НЕОБХІДНИЙ ДАМП ДЛЯ СТВОРЕННЯ БД.*/

            /* Створити об'єкт Statement object, щоб обробити оператор SELECT */
            Statement stmt = con.createStatement();
            /* Виконати SQL - оператор SELECT */
            ResultSet rs = stmt.executeQuery(str);
            System.out.println("ID Автора\tІм’я\t\tПрізвище\tМісто");
            /* Відобразити результат */
            while (rs.next()) {
                String id = rs.getString("au_id");
                String lname = rs.getString("au_lname");
                String fname = rs.getString("au_fname");
                String city = rs.getString("city");
                System.out.print(id + "\t");
                /*
                * Використовувати табуляцію, щоб форматувати вивід. Якщо число
                * символів в значенні даного менше або рівне 7,
                * використовуються два символи табуляції, щоб задати позицію
                * для відображення наступного стовпця в ResultSet
                */
                if (fname.length() <= 7)
                    System.out.print(fname + "\t\t");
                else
                    System.out.print(fname + "\t");
                if (lname.length() <= 7)
                    System.out.print(lname + "\t\t");
                else
                    System.out.print(lname + "\t");
                System.out.println(city);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println("Увага! Виникла помилка!");
            System.out.println("Помилка:" + ex);
        }
    }
}
