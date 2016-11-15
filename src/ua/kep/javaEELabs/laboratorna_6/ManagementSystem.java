package ua.kep.javaEELabs.laboratorna_6;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by shrralis on 11/15/16.
 */
public class ManagementSystem {

    private List<Group> groups;
    private Collection<Student> students;
    // Для шаблону Singletone статична змінна
    private static ManagementSystem instance;
    // закритий конструктор
    private ManagementSystem() {
        loadGroups();
        loadStudents();
    }
    // метод getInstance – перевіряє, чи ініціалізувала статична
    // змінна(у разі потреби робить це) і повертає її
    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }
    // Метод, який викликається при запуску класу
    public static void main(String[] args) {
        // Цей код дозволяє нам перенаправити стандартне виведення у файл
        // Так як на екран виводиться не зовсім легке для читання кодування,
        // файл в даному випадку зручніший
        try {
            System.setOut(new PrintStream("out.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return;
        }
        ManagementSystem ms = ManagementSystem.getInstance();
        // Перегляд повного списку груп
        printString("Повний список груп");
        printString("*******************");
        List<Group> allGroups = ms.getGroups();
        for (Group gi : allGroups) {
            printString(gi);
        }
        printString();
        // Перегляд повного списку студентів
        printString("Повний список студентів");
        printString("***********************");
        Collection<Student> allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();
        // Виведення списків студентів по групах
        printString("Список студентів по групах");
        printString("***************************");
        List<Group> groups = ms.getGroups();
        // Перевіряємо усі групи
        for (Group gi : groups) {
            printString("-> Група: " + gi.getNameGroup());
            // Отримуємо список студентів для конкретної групи
            Collection<Student> students = ms.getStudentsFromGroup(gi, 2010);
            for (Student si : students) {
                printString(si);
            }
        }
        printString();
        // Створимо нового студента і додамо його в список
        Student s = new Student();
        s.setStudentId(5);
        s.setFirstName("Володимир");
        s.setPatronymic("Володимирович");
        s.setSurName("Краєцький");
        s.setSex('ч');
        Calendar c = Calendar.getInstance();
        c.set(1993, 8, 31);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(1);
        s.setEducationYear(2010);
        printString("Додавання студента: " + s);
        printString("********************");
        ms.insertStudent(s);
        printString("-> Повний список студентів після додавання");
        allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();
        // Змінимо дані про студента – Краєцький стане у нас
        // Іващенко
        // Але усе інше буде таким же – створюємо студента з таким же ID
        s = new Student();
        s.setStudentId(5);
        s.setFirstName("Володимир");
        s.setPatronymic("Володимирович");
        s.setSurName("Іващенко");
        s.setSex('ч');
        c = Calendar.getInstance();
        c.set(1993, 8, 31);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(1);
        s.setEducationYear(2010);
        printString("Редагування даних студента: " + s);
        printString("*******************************");
        ms.updateStudent(s);
        printString("-> Повний список студентів після редагування");
        allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();
        // Видалимо нашого студента
        printString("Видалення студента: " + s);
        printString("******************");
        ms.deleteStudent(s);
        printString("-> Повний список студентів після видалення");
        allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();
        // Тут ми переводимо усіх студентів однієї групи в іншу
        // Ми знаємо, що у нас 2 групи
        // Не зовсім елегантне рішення, але доки зробимо так
        Group g1 = groups.get(0);
        Group g2 = groups.get(1);
        printString("Перевід студентів з 1-ої в 2-у групу");
        printString("***************************************");
        ms.moveStudentsToGroup(g1, 2010, g2, 2011);
        printString("-> Повний список студентів після переводу");
        allStudends = ms.getAllStudents();
        for (Student si : allStudends) {
            printString(si);
        }
        printString();
        // Видаляємо студентів з групи
        printString("Видалення студентів з групи: " + g2 + " в 2011 році");
        printString("*****************************");
        ms.removeStudentsFromGroup(g2, 2011);
        printString("-> Повний список студентів після видалення");
        allStudends = ms.getAllStudents();
        for (Iterator i = allStudends.iterator(); i.hasNext();) {
            printString(i.next());
        }
        printString();
    }
    // Метод створює дві групи і поміщає їх в колекцію для груп
    public void loadGroups() {
        // Перевіряємо – можливо наш список ще не створений взагалі
        if (groups == null) {
            groups = new ArrayList<Group>();
        } else {
            groups.clear();
        }
        Group g = null;
        g = new Group();
        g.setGroupId(1);
        g.setNameGroup("Перша");
        g.setCurator("Кучеренко Данило Іванович");
        g.setSpeciality("Вища математика");
        groups.add(g);
        g = new Group();
        g.setGroupId(2);
        g.setNameGroup("Друга");
        g.setCurator("Левицький Ігор Володимирович");
        g.setSpeciality("Програмна інженерія");
        groups.add(g);
    }
    // Метод створює декілька студентів і поміщає їх в колекцію
    public void loadStudents() {
        if (students == null) {
            // и використовуємо колекцію, яка автоматично сортує свої
            // елементи
            students = new TreeSet<Student>();
        } else {
            students.clear();
        }
        Student s = null;
        Calendar c = Calendar.getInstance();
        // Друга група
        s = new Student();
        s.setStudentId(1);
        s.setFirstName("Іван");
        s.setPatronymic("Сергійович");
        s.setSurName("Явтушевський");
        s.setSex('ч');
        c.set(1992, 3, 20);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(2);
        s.setEducationYear(2010);
        students.add(s);
        s = new Student();
        s.setStudentId(2);
        s.setFirstName("Наталія");
        s.setPatronymic("Андріївна");
        s.setSurName("Сиротинська");
        s.setSex('ж');
        c.set(1992, 6, 10);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(2);
        s.setEducationYear(2010);
        students.add(s);
        // Перша група
        s = new Student();
        s.setStudentId(3);
        s.setFirstName("Ігор");
        s.setPatronymic("Вікторович");
        s.setSurName("Маяковський");
        s.setSex('ч');
        c.set(1993, 3, 12);
        s.setDateOfBirth(c.getTime());
        s.setEducationYear(2010);
        s.setGroupId(1);
        students.add(s);
        s = new Student();
        s.setStudentId(4);
        s.setFirstName("Адріана");
        s.setPatronymic("Олександрівна");
        s.setSurName("Ковальова");
        s.setSex('ж');
        c.set(1993, 7, 19);
        s.setDateOfBirth(c.getTime());
        s.setEducationYear(2010);
        s.setGroupId(1);
        students.add(s);
    }
    // Отримати список груп
    public List<Group> getGroups() {
        return groups;
    }
    // Отримати список усіх студентів
    public Collection<Student> getAllStudents() {
        return students;
    }
    // Отримати список студентів для певної групи
    public Collection<Student> getStudentsFromGroup(Group group, int year) {
        Collection<Student> l = new TreeSet<Student>();
        for (Student si : students) {
            if (si.getGroupId() == group.getGroupId()
                    && si.getEducationYear() == year) {
                l.add(si);
            }
        }
        return l;
    }
    // Перевести студентів з однієї групи з одним роком навчання в іншу групу
    // з іншим роком навчання
    public void moveStudentsToGroup(Group oldGroup, int oldYear,
                                    Group newGroup, int newYear) {
        for (Student si : students) {
            if (si.getGroupId() == oldGroup.getGroupId()
                    && si.getEducationYear() == oldYear) {
                si.setGroupId(newGroup.getGroupId());
                si.setEducationYear(newYear);
            }
        }
    }
    // Видалити усіх студентів з певної групи
    public void removeStudentsFromGroup(Group group, int year) {
        // Ми створимо новий список студентів БЕЗ тих, кого ми хочемо
        // видалити.
        // Можливо не найцікавіший варіант. Можна було б продемонструвати
        // елегантніший метод, але він вимагає зануритися в колекції більш
        // глибоко
        // Тут ми не ставимо собі таку мету
        Collection<Student> tmp = new TreeSet<Student>();
        for (Student si : students) {
            if (si.getGroupId() != group.getGroupId()
                    || si.getEducationYear() != year) {
                tmp.add(si);
            }
        }
        students = tmp;
    }
    // Додати студента
    public void insertStudent(Student student) {
        // Просто додаємо об'єкт в колекцію
        students.add(student);
    }
    // Відновити дані про студента
    public void updateStudent(Student student) {
        // Потрібно знайти потрібного студента(по його ID) і замінити поля
        Student updStudent = null;
        for (Student si : students) {
            if (si.getStudentId() == student.getStudentId()) {
                // Ось цей студент – запам'ятовуємо його і припиняємо цикл
                updStudent = si;
                break;
            }
        }
        updStudent.setFirstName(student.getFirstName());
        updStudent.setPatronymic(student.getPatronymic());
        updStudent.setSurName(student.getSurName());
        updStudent.setSex(student.getSex());
        updStudent.setDateOfBirth(student.getDateOfBirth());
        updStudent.setGroupId(student.getGroupId());
        updStudent.setEducationYear(student.getEducationYear());
    }
    // Видалити студента
    public void deleteStudent(Student student) {
        // Потрібно знайти потрібного студента(по його ID) і видалити
        Student delStudent = null;
        for (Student si : students) {
            if (si.getStudentId() == student.getStudentId()) {
                // Ось цей студент – запам'ятовуємо його і припиняємо цикл
                delStudent = si;
                break;
            }
        }
        students.remove(delStudent);
    }
    // Цей код дозволяє нам змінити кодування
    // Таке може статися якщо використовується IDE – наприклад NetBeans.
    // Тоді ви отримуєте просто одні знаки питання, що украй незручно читати
    public static void printString(Object s) {
        System.out.println(s.toString());
        /*
        * try { System.out.println(new String(
        * s.toString().getBytes("windows-1251"), "windows-1252")); } catch
        * (UnsupportedEncodingException ex) { ex.printStackTrace(); }
        */
    }
    public static void printString() {
        System.out.println();
    }
}
