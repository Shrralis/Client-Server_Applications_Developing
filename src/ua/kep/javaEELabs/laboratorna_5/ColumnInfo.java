package ua.kep.javaEELabs.laboratorna_5;

import java.sql.*;

/**
 * Created by shrralis on 11/15/16.
 */
public class ColumnInfo {

    public static void main(String args[]) {
        try {
            /*ЖИРАВОВ: ПРИМІТКА! У ЗВ'ЯЗКУ З ТИМ, ЩО В JDK8 МІСТ JDBC-ODBC БУВ ВИЛУЧЕНИЙ,
            ДОВОДИТЬСЯ ВИКОРИСТОВУВАТИ ЗОВНІШНІ БІБЛІОТЕКИ (UCanAccess 3.0.6)*/

            /* Установити зв'язок з базою даних */
            Connection con = DriverManager.getConnection(
                    "jdbc:ucanaccess:///home//shrralis//Documents//Study//Розробка застосувань клієнт-серверної архітектури//" +
                            "Лабораторні роботи//Pubs(Для ЛР_3).mdb");
            /* Створити оператор SQL */
            String str = "SELECT * FROM " + args[0];
            Statement stmt = con.createStatement();
            /* Виконати оператор SQL */
            ResultSet rs = stmt.executeQuery(str);
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            /* Витягнути число стовпців в a ResultSet */
            System.out.println("Number of attributes in the table authors :"
                    + rsmd.getColumnCount());//Кількість атрибутів у таблиці authors
            System.out.println("");
            System.out.println("-------------------------------------");
            System.out.println("Attributes in " + args[0] + " table");//Атрибути у таблиці
            System.out.println("-------------------------------------");
            /*
            * Витягнути і відобразити імена і типи цих різних стовпців в
            * ResultSet
            */
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println(rsmd.getColumnName(i) + " : "
                        + rsmd.getColumnTypeName(i));
            }
            /* Закрити об'єкт ResultSet */
            rs.close();
            /* Закрити об'єкт Statement */
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
