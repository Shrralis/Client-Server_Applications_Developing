package ua.kep.javaEELabs.laboratorna_10.frame;

import ua.kep.javaEELabs.laboratorna_10.logic.Group;
import ua.kep.javaEELabs.laboratorna_10.logic.ManagementSystem;
import ua.kep.javaEELabs.laboratorna_10.logic.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * Created by shrralis on 11/15/16.
 */
public class StudentDialog extends JDialog implements ActionListener {

    private static final int D_HEIGHT = 200; // висота вікна
    private final static int D_WIDTH = 450; // ширина вікна
    private final static int L_X = 10; // ліва межа мітки для поля
    private final static int L_W = 100; // ширина мітки для поля
    private final static int C_W = 150; // ширина поля
    // Власник нашого вікна - вводимо для виклику потрібного нам методу
    private StudentsFrame owner;
    // Результат натиснення кнопок
    private boolean result = false;
    // Параметри студента
    private int studentId = 0;
    private JTextField firstName = new JTextField();
    private JTextField surName = new JTextField();
    private JTextField patronymic = new JTextField();
    private JSpinner dateOfBirth = new JSpinner(new SpinnerDateModel(
            new Date(), null, null, Calendar.DAY_OF_MONTH));
    private ButtonGroup sex = new ButtonGroup();
    private JSpinner year = new JSpinner(new SpinnerNumberModel(2012, 1900,
            2100, 1));
    private JComboBox groupList;
    // Другий параметр містить знак - додавання студента або виправлення
    public StudentDialog(List<Group> groups, boolean newStudent,
                         StudentsFrame owner) {
// Після вставки студента без закриття вікна нам знадобиться
// перевантаження списку
// А для цього нам потрібно мати доступ до цього методу в головній формі
        this.owner = owner;
// Встановити заголовок
        setTitle("Редагування даних студента");
        getContentPane().setLayout(new FlowLayout());
        groupList = new JComboBox(new Vector<Group>(groups));
        JRadioButton m = new JRadioButton("Чоловіча");
        JRadioButton w = new JRadioButton("Жіноча");
// Зробимо ім'я таке ж, як вимагається у даних - ч/ж
        m.setActionCommand("ч");
        w.setActionCommand("ж");
// Додамо радіо-кнопки в групу
        sex.add(m);
        sex.add(w);
// Не використовуватимемо layout зовсім
        getContentPane().setLayout(null);
// Розмістимо компоненти по абсолютних координатах
// Прізвище
        JLabel l = new JLabel("Прізвище:", JLabel.RIGHT);
        l.setBounds(L_X, 10, L_W, 20);
        getContentPane().add(l);
        surName.setBounds(L_X + L_W + 10, 10, C_W, 20);
        getContentPane().add(surName);
// Ім'я
        l = new JLabel("Ім’я:", JLabel.RIGHT);
        l.setBounds(L_X, 30, L_W, 20);
        getContentPane().add(l);
        firstName.setBounds(L_X + L_W + 10, 30, C_W, 20);
        getContentPane().add(firstName);
        // По батькові
        l = new JLabel("По батькові:", JLabel.RIGHT);
        l.setBounds(L_X, 50, L_W, 20);
        getContentPane().add(l);
        patronymic.setBounds(L_X + L_W + 10, 50, C_W, 20);
        getContentPane().add(patronymic);
// Пол
        l = new JLabel("Стать:", JLabel.RIGHT);
        l.setBounds(L_X, 70, L_W, 20);
        getContentPane().add(l);
        m.setBounds(L_X + L_W + 10, 70, C_W / 2, 20);
        getContentPane().add(m);
// Зробимо за умовчанням жіночу - з поваги
        w.setBounds(L_X + L_W + 10 + C_W / 2, 70, C_W / 2, 20);
        w.setSelected(true);
        getContentPane().add(w);
// Дата народження
        l = new JLabel("Дата народження :", JLabel.RIGHT);
        l.setBounds(L_X, 90, L_W, 20);
        getContentPane().add(l);
        dateOfBirth.setBounds(L_X + L_W + 10, 90, C_W, 20);
        getContentPane().add(dateOfBirth);
// Група
        l = new JLabel("Група:", JLabel.RIGHT);
        l.setBounds(L_X, 115, L_W, 25);
        getContentPane().add(l);
        groupList.setBounds(L_X + L_W + 10, 115, C_W, 25);
        getContentPane().add(groupList);
// Рік навчання
        l = new JLabel("Рік навчання :", JLabel.RIGHT);
        l.setBounds(L_X, 145, L_W, 20);
        getContentPane().add(l);
        year.setBounds(L_X + L_W + 10, 145, C_W, 20);
        getContentPane().add(year);
        JButton btnOk = new JButton("OK");
        btnOk.setName("OK");
        btnOk.addActionListener(this);
        btnOk.setBounds(L_X + L_W + C_W + 10 + 50, 10, 100, 25);
        getContentPane().add(btnOk);
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setName("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(L_X + L_W + C_W + 10 + 50, 40, 100, 25);
        getContentPane().add(btnCancel);
        if (newStudent) {
            JButton btnNew = new JButton("New");
            btnNew.setName("New");
            btnNew.addActionListener(this);
            btnNew.setBounds(L_X + L_W + C_W + 10 + 50, 70, 100, 25);
            getContentPane().add(btnNew);
        }
// Встановлюємо поведінку форми при закритті - не закривати зовсім.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
// Отримуємо розміри екрану
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
// А тепер просто поміщаємо його по центру, обчислюючи координати на
// основі отриманої інформації
        setBounds(((int) d.getWidth() - StudentDialog.D_WIDTH) / 2, ((int) d
                        .getHeight() - StudentDialog.D_HEIGHT) / 2,
                StudentDialog.D_WIDTH, StudentDialog.D_HEIGHT);
    }
    // Встановити поля відповідно до переданих даних про студента
    public void setStudent(Student st) {
        studentId = st.getStudentId();
        firstName.setText(st.getFirstName());
        surName.setText(st.getSurName());
        patronymic.setText(st.getPatronymic());
        dateOfBirth.getModel().setValue(st.getDateOfBirth());
        for (Enumeration e = sex.getElements(); e.hasMoreElements();) {
            AbstractButton ab = (AbstractButton) e.nextElement();
            ab.setSelected(ab.getActionCommand().equals(
                    new String("" + st.getSex())));
        }
        year.getModel().setValue(new Integer(st.getEducationYear()));
        for (int i = 0; i < groupList.getModel().getSize(); i++) {
            Group g = (Group) groupList.getModel().getElementAt(i);
            if (g.getGroupId() == st.getGroupId()) {
                groupList.setSelectedIndex(i);
                break;
            }
        }
    }
    // Повернути дані у вигляді нового студента з відповідними полями
    public Student getStudent() throws SQLException {
        Student st = new Student(null);
        st.setStudentId(studentId);
        st.setFirstName(firstName.getText());
        st.setSurName(surName.getText());
        st.setPatronymic(patronymic.getText());
        Date d = ((SpinnerDateModel) dateOfBirth.getModel()).getDate();
        st.setDateOfBirth(d);
        for (Enumeration e = sex.getElements(); e.hasMoreElements();) {
            AbstractButton ab = (AbstractButton) e.nextElement();
            if (ab.isSelected()) {
                st.setSex(ab.getActionCommand().charAt(0));
            }
        }
        int y = ((SpinnerNumberModel) year.getModel()).getNumber().intValue();
        st.setEducationYear(y);
        st.setGroupId(((Group) groupList.getSelectedItem()).getGroupId());
        return st;
    }
    // Отримати результат, true - кнопка ОК, false - кнопка Cancel
    public boolean getResult() {
        return result;
    }
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
// Додаємо студента, але не закриваємо вікно
// Тут ми не викликатимемо в окремому потоці збереження.
// // Воно не займео багато часу і зайві дії тут не виправдані
        if (src.getName().equals("New")) {
            result = true;
            try {
                ManagementSystem.getInstance().insertStudent(getStudent());
                owner.reloadStudents();
                firstName.setText("");
                surName.setText("");
                patronymic.setText("");
            } catch (Exception sql_e) {
                JOptionPane.showMessageDialog(this, sql_e.getMessage());
            }
            return;
        }
        if (src.getName().equals("OK")) {
            result = true;
        }
        if (src.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }
}
