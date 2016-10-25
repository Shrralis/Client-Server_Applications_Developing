package ua.kep.javaEELabs.laboratorna_3;

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
            String str = "SELECT * FROM dbo_authors WHERE city LIKE 'O%'";
            /*
            * Ініціалізувати і завантажити драйвер Міст JDBC - ODBC
            */
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            /*ЖИРАВОВ: ПРИМІТКА! У ЗВ'ЯЗКУ З ТИМ, ЩО В JDK8 МІСТ JDBC-ODBC БУВ ВИЛУЧЕНИЙ,
            ДОВОДИТЬСЯ ВИКОРИСТОВУВАТИ ЗОВНІШНІ БІБЛІОТЕКИ (UCanAccess 3.0.6)*/

            /* Установити зв'язок з базою даних */
            Connection con = DriverManager.getConnection(
                    "jdbc:ucanaccess:///home//shrralis//Documents//Study//Розробка застосувань клієнт-серверної архітектури//" +
                            "Лабораторні роботи//Pubs(Для ЛР_3).mdb");
            /*
            * в дужках вище вказується URL, login, password.
            * URL має вигляд jdbc:<субпротокол>:<ім'я, пов'язане з СУБД, або Протоколом>
            * У БД в інтернет/інтранет "ім'я" може містити мережевий URL
            * //<ім'я хосту>:<порт>/..
            * для мосту JDBC - ODBC
            * jdbc:odbc:
            * де, DSN - ім'я пов'язане з БД, login - вхідне ім'я, password - пароль
            * */
            /* Створити об'єкт Statement object, щоб обробити оператор SELECT */
            Statement stmt = con.createStatement();
            /* Виконати SQL - оператор SELECT */
            ResultSet rs = stmt.executeQuery(str);
            System.out.println("ID автора\tІм’я\t\tПрізвище\tМісто");
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
