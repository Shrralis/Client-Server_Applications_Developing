package ua.kep.javaEELabs.laboratorna_10.frame;

import ua.kep.javaEELabs.laboratorna_10.logic.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

/**
 * Created by shrralis on 11/15/16.
 */
public class GroupDialog extends JDialog implements ActionListener {

    private static final int D_HEIGHT = 150; // висота
    private final static int D_WIDTH = 200; // ширина
    private JSpinner spYear;
    private JComboBox groupList;
    private JButton btnOk = new JButton("OK");
    private JButton btnCancel = new JButton("Cancel");
    private boolean result = false;
    public GroupDialog(int year, List groups) {
// Встановити заголовок
        setTitle("Перенесення групи");
// Створюємо складний layout для нашого вікна
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
// Створюємо змінну для установки правил розміщення
        GridBagConstraints c = new GridBagConstraints();
// Відразу задаємо відступ від меж для кожного елементу
        c.insets = new Insets(5, 5, 5, 5);
// Перший елемент - заголовок для поля вибору груп
        JLabel l = new JLabel("Нова група :");
// Після нього можна буде ще поміщати компоненти
        c.gridwidth = GridBagConstraints.RELATIVE;
// Не заповнюємо увесь простір, відведений компоненту
        c.fill = GridBagConstraints.NONE;
// "Прив'язуємо" компонент до правого краю
        c.anchor = GridBagConstraints.EAST;
// Встановлюємо це правило для нашого компонента
        gbl.setConstraints(l, c);
// Додаємо компонент
        getContentPane().add(l);
// Другий елемент - список груп
        groupList = new JComboBox(new Vector(groups));
// Елемент займає усю ширину, що залишилася
        c.gridwidth = GridBagConstraints.REMAINDER;
// Розтягуємо компонент по усьому простору для нього
        c.fill = GridBagConstraints.BOTH;
// "Прив'язуємо" його до лівої частини
        c.anchor = GridBagConstraints.WEST;
// Встановлюємо це правило для нашого компонента
        gbl.setConstraints(groupList, c);
// Додаємо компонент
        getContentPane().add(groupList);
// Третій елемент - заголовок для поля вибору року
        l = new JLabel("Новий рік :");
// Після нього можна буде ще поміщати компоненти
        c.gridwidth = GridBagConstraints.RELATIVE;
// Не заповнюємо увесь простір, відведений компоненту
        c.fill = GridBagConstraints.NONE;
// "Прив'язуємо" компонент до правого краю
        c.anchor = GridBagConstraints.EAST;
// Встановлюємо це правило для нашого компонента
        gbl.setConstraints(l, c);
// Додаємо компонент
        getContentPane().add(l);
// Відразу збільшуємо групу на один рік - для перекладу
        spYear = new JSpinner(new SpinnerNumberModel(year + 1, 1900, 2100, 1));
// Елемент займає усю ширину, що залишилася
        c.gridwidth = GridBagConstraints.REMAINDER;
// Розтягуємо компонент по усьому простору для нього
        c.fill = GridBagConstraints.BOTH;
// "Прив'язуємо" його до лівої частини
        c.anchor = GridBagConstraints.WEST;
// Встановлюємо це правило для нашого компонента
        gbl.setConstraints(spYear, c);
// Додаємо компонент
        getContentPane().add(spYear);
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.BOTH;
        btnOk.setName("OK");
// Додаємо лістенер для кнопки
        btnOk.addActionListener(this);
// Встановлюємо це правило для нашого компонента
        gbl.setConstraints(btnOk, c);
// Додаємо компонент
        getContentPane().add(btnOk);
        btnCancel.setName("Cancel");
// Додаємо лістенер для кнопки
        btnCancel.addActionListener(this);
// Встановлюємо це правило для нашого компонента
        gbl.setConstraints(btnCancel, c);
// Додаємо компонент
        getContentPane().add(btnCancel);
// Встановлюємо поведінку форми при закритті - не закривати зовсім.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
// Отримуємо розміри екрану
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
// А тепер просто поміщаємо його по центру, обчислюючи координати на
// основі отриманої інформації
        setBounds(((int) d.getWidth() - GroupDialog.D_WIDTH) / 2, ((int) d
                        .getHeight() - GroupDialog.D_HEIGHT) / 2, GroupDialog.D_WIDTH,
                GroupDialog.D_HEIGHT);
    }
    // Повернення року, який встановлений на формі
    public int getYear() {
        return ((SpinnerNumberModel) spYear.getModel()).getNumber().intValue();
    }
    // Повернення групи, яка встановлена на формі
    public Group getGroup() {
        if (groupList.getModel().getSize() > 0) {
            return (Group) groupList.getSelectedItem();
        }
        return null;
    }
    // Отримати результат, true - кнопка ОК, false - кнопка Cancel
    public boolean getResult() {
        return result;
    }
    // Обробка натиску кнопок
    public void actionPerformed(ActionEvent e) {
        JButton src = (JButton) e.getSource();
        if (src.getName().equals("OK")) {
            result = true;
        }
        if (src.getName().equals("Cancel")) {
            result = false;
        }
        setVisible(false);
    }
}
