package ua.kep.javaEELabs.laboratorna_7.logic;

import java.text.Collator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shrralis on 11/15/16.
 */
public class Student implements Comparable {
    // поле ID СТУДЕНТА
    private int studentId;
    // поле ІМ'Я
    private String firstName;
    // поле ПРІЗВИЩЕ
    private String surName;
    // поле ПО БАТЬКОВІ
    private String patronymic;
    // поле ДАТА НАРОДЖЕННЯ
    private Date dateOfBirth;
    // поле ПОЛ
    private char sex;
    // поле ID ГРУПИ
    private int groupId;
    // поле РІК НАВЧАННЯ
    private int educationYear;
    // get/set для ДАТА НАРОДЖЕННЯ
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    // get/set для РІК НАВЧАННЯ
    public int getEducationYear() {
        return educationYear;
    }

    public void setEducationYear(int educationYear) {
        this.educationYear = educationYear;
    }
    // get/set для ID ГРУПИ
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    // get/set для ID СТУДЕНТА
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    // get/set для ІМ'Я
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    // get/set для ПО БАТЬКОВІ
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    // get/set для ПРІЗВИЩЕ
    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
    // get/set для СТАТЬ
    public char getSex() {
        return sex;
    }
    public
    void setSex(char sex) {
        this.sex = sex;
    }
    /* DateFormat - клас для перетворення дати
    у рядок в визначеному форматі.*/
    public String toString() {
        return surName
                + " "
                + firstName
                + " "
                + patronymic
                + " "
                + DateFormat.getDateInstance(DateFormat.SHORT).format(dateOfBirth) + "" + "Група ID=" + groupId + " Рік :"
                + educationYear;
    }

    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ua"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }
}
