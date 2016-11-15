package ua.kep.javaEELabs.laboratorna_10.frame;

import ua.kep.javaEELabs.laboratorna_10.logic.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by shrralis on 11/15/16.
 */
public class StudentsFrame extends JFrame implements ActionListener, ListSelectionListener, ChangeListener {
    // Введемо відразу імена для кнопок - потім їх використовуватимемо в
// обробниках
    private static final String MOVE_GR = "moveGroup";
    private static final String CLEAR_GR = "clearGroup";
    private static final String INSERT_ST = "insertStudent";
    private static final String UPDATE_ST = "updateStudent";
    private static final String DELETE_ST = "deleteStudent";
    private static final String ALL_STUDENTS = "allStudent";
    private static final String EXIT = "exit";
    private ManagementSystem ms = null;
    private JList grpList;
    private JTable stdList;
    private JSpinner spYear;

    public StudentsFrame() throws Exception {
// Встановлюємо layout для усієї клієнтської частини форми
        getContentPane().setLayout(new BorderLayout());
// Створюємо рядок меню
        JMenuBar menuBar = new JMenuBar();
// Створюємо випадаюче меню
        JMenu menu = new JMenu("Звіти");
// Створюємо пункт у випадаючому меню
        JMenuItem menuItem = new JMenuItem("Усі студенти");
        menuItem.setName(ALL_STUDENTS);
// Додаємо листенер
        menuItem.addActionListener(this);
// Вставляємо пункт меню у випадаюче меню
        menu.add(menuItem);
// Вставляємо випадаюче меню в рядок меню
        menuBar.add(menu);

        menuItem = new JMenuItem("Вийти");

        menuItem.setName(EXIT);

        menuItem.addActionListener(this);

        menu = new JMenu("Вихід");

        menu.add(menuItem);

        menuBar.add(menu);

// Встановлюємо меню для форми
        setJMenuBar(menuBar);
// Створюємо верхню панель, де буде поле для введення року
        JPanel top = new JPanel();
// Встановлюємо для неї layout
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
// Вставляємо пояснювальний напис
        top.add(new JLabel("Рік навчання :"));
// Робимо спін-поле
// 1. Задаємо модель поведінки - тільки цифри
// 2. Вставляємо в панель
        SpinnerModel sm = new SpinnerNumberModel(2012, 1980, 2100, 1);
        spYear = new JSpinner(sm);
// Додаємо лістенер
        spYear.addChangeListener(this);
        top.add(spYear);
// Створюємо нижню панель і задаємо їй layout
        JPanel bot = new JPanel();
        bot.setLayout(new BorderLayout());
// Створюємо ліву панель для виведення списку груп
        GroupPanel left = new GroupPanel();
// Задаємо layout і задаємо "бордюр" навколо панелі
        left.setLayout(new BorderLayout());
        left.setBorder(new BevelBorder(BevelBorder.LOWERED));
// Отримуємо конект до бази і створюємо об'єкт ManagementSystem
        ms = ManagementSystem.getInstance();
// Отримуємо список груп
        Vector<Group> gr = new Vector<Group>(ms.getGroups());
// Створюємо напис
        left.add(new JLabel("Групи:"), BorderLayout.NORTH);
// Створюємо візуальний список і вставляємо його в прокручувану
// панель, яку у свою чергу вже кладемо на панель left
        grpList = new JList(gr);
// Додаємо лістенер
        grpList.addListSelectionListener(this);
// Відразу виділяємо першу групу
        grpList.setSelectedIndex(0);
        left.add(new JScrollPane(grpList), BorderLayout.CENTER);
// Створюємо кнопки для груп
        JButton btnMvGr = new JButton("Перемістити");
        btnMvGr.setName(MOVE_GR);
        JButton btnClGr = new JButton("Очистити");
        btnClGr.setName(CLEAR_GR);
// Додаємо лістенер
        btnMvGr.addActionListener(this);
        btnClGr.addActionListener(this);
// Створюємо панель, на яку покладемо наші кнопки і кладемо її вниз
        JPanel pnlBtnGr = new JPanel();
        pnlBtnGr.setLayout(new GridLayout(1, 2));
        pnlBtnGr.add(btnMvGr);
        pnlBtnGr.add(btnClGr);
        left.add(pnlBtnGr, BorderLayout.SOUTH);
// Створюємо праву панель для виведення списку студентів
        JPanel right = new JPanel();
// Задаємо layout і задаємо "бордюр" навколо панелі
        right.setLayout(new BorderLayout());
        right.setBorder(new BevelBorder(BevelBorder.LOWERED));
// Створюємо напис
        right.add(new JLabel("Студенти:"), BorderLayout.NORTH);
// Створюємо таблицю і вставляємо її в прокручувану
// панель, яку у свою чергу вже кладемо на панель right
// Наша таблиця доки нічого не уміє - просто покладемо її як заготівку
// Зробимо в ній 4 колонки - Прізвище, Ім'я, По батькові, Дата
// народження
        stdList = new JTable(1, 4);
        right.add(new JScrollPane(stdList), BorderLayout.CENTER);
// Створюємо кнопки для студентів
        JButton btnAddSt = new JButton("Додати");
        btnAddSt.setName(INSERT_ST);
        btnAddSt.addActionListener(this);
        JButton btnUpdSt = new JButton("Виправити");
        btnUpdSt.setName(UPDATE_ST);
        btnUpdSt.addActionListener(this);
        JButton btnDelSt = new JButton("Видалити");
        btnDelSt.setName(DELETE_ST);
        btnDelSt.addActionListener(this);
// Створюємо панель, на яку покладемо наші кнопки і кладемо її вниз
        JPanel pnlBtnSt = new JPanel();
        pnlBtnSt.setLayout(new GridLayout(1, 3));
        pnlBtnSt.add(btnAddSt);
        pnlBtnSt.add(btnUpdSt);
        pnlBtnSt.add(btnDelSt);
        right.add(pnlBtnSt, BorderLayout.SOUTH);
// Вставляємо панелі зі списками груп і студентів в нижню панель
        bot.add(left, BorderLayout.WEST);
        bot.add(right, BorderLayout.CENTER);
// Вставляємо верхню і нижню панелі у форму
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(bot, BorderLayout.CENTER);
// Задаємо межі форми
        setBounds(100, 100, 700, 500);
    }
    // Метод для забезпечення інтерфейсу ActionListener
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Component) {
            Component c = (Component) e.getSource();
            if (c.getName().equals(MOVE_GR)) {
                moveGroup();
            }
            if (c.getName().equals(CLEAR_GR)) {
                clearGroup();
            }
            if (c.getName().equals(ALL_STUDENTS)) {
                showAllStudents();
            }
            if (c.getName().equals(INSERT_ST)) {
                insertStudent();
            }
            if (c.getName().equals(UPDATE_ST)) {
                updateStudent();
            }
            if (c.getName().equals(DELETE_ST)) {
                deleteStudent();
            }
            if (c.getName().equals(EXIT)) {
                Runtime.getRuntime().exit(0);
            }
        }
    }
    // Метод для забезпечення інтерфейсу ListSelectionListener
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            reloadStudents();
        }
    }
    // Метод для забезпечення інтерфейсу ChangeListener
    public void stateChanged(ChangeEvent e) {
        reloadStudents();
    }
    // метод для оновлення списку студентів для певної групи
    protected void reloadStudents() {
        // Створюємо анонімний клас для потоку
        Thread t = new Thread() {
            // Перевизначаємо в нім метод run
            public void run() {
                if (stdList != null) {
// Отримуємо виділену групу
                    Group g = (Group) grpList.getSelectedValue();
// Отримуємо число зі спінера
                    int y = ((SpinnerNumberModel) spYear.getModel())
                            .getNumber().intValue();
                    try {
// Отримуємо список студентів
                        Collection<Student> s = ms.getStudentsFromGroup(g, y);
// І встановлюємо модель для таблиці з новими даними
                        stdList.setModel(new StudentTableModel(
                                new Vector<Student>(s)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                    }
                }
            }
// Закінчення нашого методу run
        };
// Закінчення визначення анонімного класу
// Тепер ми запускаємо наш потік
        t.start();
    }
    // метод для перенесення групи
    private void moveGroup() {
        Thread t = new Thread() {
            public void run() {
// Якщо група не виділена - виходимо. Хоча це украй
// маловірогідно
                if (grpList.getSelectedValue() == null) {
                    return;
                }
                try {
// Отримуємо виділену групу
                    Group g = (Group) grpList.getSelectedValue();
// Отримуємо число зі спінера
                    int y = ((SpinnerNumberModel) spYear.getModel())
                            .getNumber().intValue();
// Створюємо наш діалог
                    GroupDialog gd = new GroupDialog(y, ms.getGroups());
// Задаємо йому режим модальності - не можна нічого окрім
// нього виділити
                    gd.setModal(true);
// Показуємо діалог
                    gd.setVisible(true);
// Якщо натиснули кнопку OK - переміщаємо в нову групу з
// новим роком
// і перевантажуємо список студентів
                    if (gd.getResult()) {
                        ms.moveStudentsToGroup(g, y, gd.getGroup(), gd
                                .getYear());
                        reloadStudents();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e
                            .getMessage());
                }
            }
        };
        t.start();
    }
    // метод для очищення групи
    private void clearGroup() {
        Thread t = new Thread() {
            public void run() {
// Перевіряємо - чи виділена група
                if (grpList.getSelectedValue() != null) {
                    if (JOptionPane.showConfirmDialog(StudentsFrame.this,
                            "Ви хочете видалити студентів з групи?",
                            "Видалення студентів", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
// Отримуємо виділену групу
                        Group g = (Group) grpList.getSelectedValue();
// Отримуємо число зі спінера
                        int y = ((SpinnerNumberModel) spYear.getModel())
                                .getNumber().intValue();
                        try {
// Видалили студентів з групи
                            ms.removeStudentsFromGroup(g, y);
// перевантажили список студентів
                            reloadStudents();
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                        }
                    }
                }
            }
        };
        t.start();
    }
    // метод для додавання студента
    private void insertStudent() {
        Thread t = new Thread() {
            public void run() {
                try {
// Додаємо нового студента - тому true
// Також відмічу, що необхідно вказати не просто this, а
// StudentsFrame.this
// Інакше клас не буде сприйнятий - він же інший - анонімний
                    StudentDialog sd = new StudentDialog(ms.getGroups(), true, StudentsFrame.this);
                    sd.setModal(true);
                    sd.setVisible(true);
                    if (sd.getResult()) {
                        Student s = sd.getStudent();
                        ms.insertStudent(s);
                        reloadStudents();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(StudentsFrame.this, e
                            .getMessage());
                }
            }
        };
        t.start();
    }
    // метод для редагування студента
    private void updateStudent() {
        Thread t = new Thread() {
            public void run() {
                if (stdList != null) {
                    StudentTableModel stm = (StudentTableModel) stdList
                            .getModel();
// Перевіряємо - чи виділений хоч який-небудь студент
                    if (stdList.getSelectedRow() >= 0) {
// Ось де нам згодився метод getStudent(int rowIndex)
                        Student s = stm.getStudent(stdList.getSelectedRow());
                        try {
// Виправляємо дані на студента - тому false
// Також відмічу, що необхідно вказати не просто
// this, а StudentsFrame.this
// Інакше клас не буде сприйнятий - він же інший -
// анонімний
                            StudentDialog sd = new StudentDialog(ms.getGroups(), false, StudentsFrame.this);
                            sd.setStudent(s);
                            sd.setModal(true);
                            sd.setVisible(true);
                            if (sd.getResult()) {
                                Student us = sd.getStudent();
                                ms.updateStudent(us);
                                reloadStudents();
                            }
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(StudentsFrame.this, e
                                    .getMessage());
                        }
                    }
// Якщо студент не виділений - повідомляємо користувача, що це необхідно виконати
                    else {
                        JOptionPane.showMessageDialog(StudentsFrame.this,
                                "Необхідно виділити студента в списку");
                    }
                }
            }
        };
        t.start();
    }
    // метод для видалення студента
    private void deleteStudent() {
        Thread t = new Thread() {
            public void run() {
                if (stdList != null) {
                    StudentTableModel stm = (StudentTableModel) stdList.getModel();
// Перевіряємо - чи виділений хоч який-небудь студент
                    if (stdList.getSelectedRow() >= 0) {
                        if (JOptionPane.showConfirmDialog(StudentsFrame.this,
                                "Ви хочете видалити студента?",
                                "Видалення студента",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
// Ось де нам згодився метод getStudent(int
// rowIndex)
                            Student s = stm.getStudent(stdList.getSelectedRow());
                            try {
                                ms.deleteStudent(s);
                                reloadStudents();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(
                                        StudentsFrame.this, e.getMessage());
                            }
                        }
                    }
// Якщо студент не виділений - повідомляємо користувача, що
// це необхідно зробити
                    else {
                        JOptionPane.showMessageDialog(StudentsFrame.this,"Необхідно виділити студента в списку");
                    }
                }
            }
        };
        t.start();
    }
    // метод для показу усіх студентів
    private void showAllStudents() {
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();
                if (stdList != null) {
                    try {
// Отримуємо список студентів
                        Collection<Student> s = ms.getAllStudents();
// І встановлюємо модель для таблиці з новими даними
                        stdList.setModel(new StudentTableModel(
                                new Vector<Student>(s)));
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(StudentsFrame.this, e.getMessage());
                    }
                }
// Вводимо штучну затримку на 3 секунди
            }
        };
        t.start();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
// Ми відразу відмінимо продовження роботи, якщо не зможемо
// отримати
// конект до бази даних
                    StudentsFrame sf = new StudentsFrame();
                    sf.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    sf.setVisible(true);
// Перевантаження списку нам потрібне в цьому треді
// оскільки при створенні форми списку студентів ще немає
                    sf.reloadStudents();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    // Наш внутрішній клас - перевизначена панель.
    class GroupPanel extends JPanel {
        public Dimension getPreferredSize() {
            return new Dimension(250, 0);
        }
    }
}
