package ua.kep.javaEELabs.laboratorna_8.frame;

import ua.kep.javaEELabs.laboratorna_8.logic.Group;
import ua.kep.javaEELabs.laboratorna_8.logic.ManagementSystem;
import ua.kep.javaEELabs.laboratorna_8.logic.Student;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Vector;

/**
 * Created by shrralis on 11/15/16.
 */
public class StudentsFrame extends JFrame {

    ManagementSystem ms = null;
    private JList grpList;
    private JList stdList;
    private JSpinner spYear;

    public StudentsFrame() throws Exception {
        // Встановлюємо layout для усієї клієнтської частини форми
        getContentPane().setLayout(new BorderLayout());
        // Створюємо верхню панель, де буде поле для введення року
        JPanel top = new JPanel();
        // Встановлюємо для неї layout
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        // Вставляємо пояснювальний напис
        top.add(new JLabel("Рік навчання :"));
        // Робимо спін-поле
        // 1. Задаємо модель поведінки - тільки цифри
        // 2. Вставляємо в панель
        SpinnerModel sm = new SpinnerNumberModel(2011, 1900, 2100, 1);
        spYear = new JSpinner(sm);
        top.add(spYear);
        // Створюємо нижню панель і задаємо їй layout
        JPanel bot = new JPanel();
        bot.setLayout(new BorderLayout());
        // Створюємо ліву панель для виведення списку груп
        JPanel left = new JPanel();
        // Задаємо layout і задаємо "бордюр" навколо панелі
        left.setLayout(new BorderLayout());
        left.setBorder(new BevelBorder(BevelBorder.RAISED));
        // Нам необхідно обробити помилку при зверненні до бази даних
        Vector gr = null;
        Vector st = null;
        // Спробуємо отримати конект до бази даних
        ms = ManagementSystem.getInstance();
        // Отримуємо список груп
        gr = new Vector<Group>(ms.getGroups());
        // Отримуємо список студентів
        st = new Vector<Student>(ms.getAllStudents());
        // Створюємо напис
        left.add(new JLabel("Групи:"), BorderLayout.NORTH);
        // Створюємо візуальний список і вставляємо його в прокручувану
        // панель, яку у свою чергу вже кладемо на панель left
        grpList = new JList(gr);
        left.add(new JScrollPane(grpList), BorderLayout.CENTER);
        // Створюємо праву панель для виведення списку студентів
        JPanel right = new JPanel();
        // Задаємо layout і задаємо "бордюр" навколо панелі
        right.setLayout(new BorderLayout());
        right.setBorder(new BevelBorder(BevelBorder.RAISED));
        // Створюємо напис
        right.add(new JLabel("Студенти:"), BorderLayout.NORTH);
        // Створюємо візуальний список і вставляємо його в прокручувану
        // панель, яку у свою чергу вже кладемо на панель right
        stdList = new JList(st);
        right.add(new JScrollPane(stdList), BorderLayout.CENTER);
        // Вставляємо панелі зі списками груп і студентів в нижню панель
        bot.add(left, BorderLayout.WEST);
        bot.add(right, BorderLayout.CENTER);
        // Вставляємо верхню і нижню панелі у форму
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(bot, BorderLayout.CENTER);
        // Задаємо межі форми
        setBounds(100, 100, 600, 400);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Ми відразу відмінимо продовження роботи, якщо не
                    // зможемо отримати
                    // конект до бази даних
                    StudentsFrame sf = new StudentsFrame();
                    sf.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    sf.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
