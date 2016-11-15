package ua.kep.javaEELabs.laboratorna_5;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * Created by shrralis on 11/15/16.
 */
public class TableNames {

    public static void main(String[] args) {
        try {
            /*ЖИРАВОВ: ПРИМІТКА! У ЗВ'ЯЗКУ З ТИМ, ЩО В JDK8 МІСТ JDBC-ODBC БУВ ВИЛУЧЕНИЙ,
            ДОВОДИТЬСЯ ВИКОРИСТОВУВАТИ ЗОВНІШНІ БІБЛІОТЕКИ (UCanAccess 3.0.6)*/

            /* Установити зв'язок з базою даних */
            Connection con = DriverManager.getConnection(
                    "jdbc:ucanaccess:///home//shrralis//Documents//Study//Розробка застосувань клієнт-серверної архітектури//" +
                            "Лабораторні роботи//Pubs(Для ЛР_5).mdb");
            /* Створити об'єкт DatabaseMetaData */
            DatabaseMetaData dbmd = con.getMetaData();
            String[] tabTypes = {"TABLE"};
            /* Витягнути імена таблиць бази даних */
            System.out.println("");
            System.out.println("Перелік таблиць бази даних");
            System.out.println("*------------------------*");
            ResultSet tablesRS = dbmd.getTables(null, null, null, tabTypes);
            while (tablesRS.next())
                /* Відобразити імена таблиць бази даних */
                System.out.println(tablesRS.getString("TABLE_NAME"));
            con.close();
        } catch (Exception e) {
            System.out.println("Помилка: " + e);
        }
    }
}