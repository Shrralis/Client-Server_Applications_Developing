package ua.kep.javaEELabs.laboratorna_9.frame;

import ua.kep.javaEELabs.laboratorna_9.logic.Student;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.util.Vector;

/**
 * Created by shrralis on 11/15/16.
 */
public class StudentTableModel extends AbstractTableModel {
    // Зробимо сховище для нашого списку студентів
    private Vector students;
    // Модель при створенні отримує список студентів
    public StudentTableModel(Vector students) {
        this.students = students;
    }
    // Кількість рядків дорівнює числу записів
    public int getRowCount() {
        if (students != null) {
            return students.size();
        }
        return 0;
    }
    // Кількість стовпців - 4. Прізвище, Ім'я, По батькові, Дата народження
    public int getColumnCount() {
        return 4;
    }
// Повернемо найменування колонки
public String getColumnName(int column) {
    String[] colNames = { "Прізвище", "Ім'я", "По батькові", "Дата" };
    return colNames[column];
}
    // Повертаємо дані для певного рядка і стовпця
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (students != null) {
// Отримуємо з вектору студента
            Student st = (Student) students.get(rowIndex);
// Залежно від колонки повертаємо ім'я, прізвище і так далі
            switch (columnIndex) {
                case 0:
                    return st.getSurName();
                case 1:
                    return st.getFirstName();
                case 2:
                    return st.getPatronymic();
                case 3:
                    return DateFormat.getDateInstance(DateFormat.SHORT).format(
                            st.getDateOfBirth());
            }
        }
        return null;
    }
    // Додамо метод, який повертає студента по номеру рядка
// Це нам згодиться трохи пізніше
    public Student getStudent(int rowIndex) {
        if (students != null) {
            if (rowIndex < students.size() && rowIndex >= 0) {
                return (Student) students.get(rowIndex);
            }
        }
        return null;
    }
}
