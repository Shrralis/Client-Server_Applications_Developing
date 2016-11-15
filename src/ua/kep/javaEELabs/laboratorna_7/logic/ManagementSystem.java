package ua.kep.javaEELabs.laboratorna_7.logic;

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
    /* метод getInstance - перевіряє, чи ініціалізувала статична
    змінна(у разі потреби робить це) і повертає її*/
    public static synchronized ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }
        return instance;
    }
    // Метод створює дві групи і поміщає їх в колекцію для груп
    public void loadGroups() {
        // Перевіряємо чи створений наш список чи ще не створений взагалі
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
        /* Ми використовуємо колекцію, яка автоматично сортує свої
        елементи*/
            students = new TreeSet<Student>();
        } else {
            students.clear();
        }
        Student s = null;
        Calendar c = Calendar.getInstance();
        // Друга група
        s = new Student();
        s.setStudentId(1);
        s.setFirstName("Святослав");
        s.setPatronymic("Казимирович");
        s.setSurName("Коморовський");
        s.setSex('ч');
        c.set(1993, 3, 20);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(2);
        s.setEducationYear(2011);
        students.add(s);
        s = new Student();
        s.setStudentId(2);
        s.setFirstName("Галина");
        s.setPatronymic("Іванівна");
        s.setSurName("Вовк");
        s.setSex('ж');
        c.set(1993, 6, 10);
        s.setDateOfBirth(c.getTime());
        s.setGroupId(2);
        s.setEducationYear(2012);
        students.add(s);
        // Перша група
        s = new Student();
        s.setStudentId(3);
        s.setFirstName("Ростислав");
        s.setPatronymic("Вікторович");
        s.setSurName("Кружинський");
        s.setSex('ж');
        c.set(1994, 3, 12);
        s.setDateOfBirth(c.getTime());
        s.setEducationYear(2012);
        s.setGroupId(1);
        students.add(s);
        s = new Student();
        s.setStudentId(4);
        s.setFirstName("Вероніка");
        s.setPatronymic("Сергіївна");
        s.setSurName("Квасневські");
        s.setSex('ж');
        c.set(1993, 7, 19);
        s.setDateOfBirth(c.getTime());
        s.setEducationYear(2011);
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
    /* Перевести студентів з однієї групи з одним роком навчання в іншу групу
    з іншим роком навчання*/
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
    // кращий метод, але він вимагає зануритися в колекції більш
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
                // Ось цей студент - запам'ятовуємо його і припиняємо цикл
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
                // Ось цей студент - запам'ятовуємо його і припиняємо цикл
                delStudent = si;
                break;
            }
        }
        students.remove(delStudent);
    }
}
