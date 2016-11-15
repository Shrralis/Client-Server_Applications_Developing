package ua.kep.javaEELabs.laboratorna_7.logic;

/**
 * Created by shrralis on 11/15/16.
 */
public class Group {
    // поле ID ГРУПИ
    private int groupId;
    // поле ІМ'Я ГРУПИ
    private String nameGroup;
    // поле КУРАТОР
    private String curator;
    // поле СПЕЦІАЛЬНІСТЬ
    private String speciality;
    // get/set для КУРАТОР
    public String getCurator() {
        return curator;
    }

    public void setCurator(String curator) {
        this.curator = curator;
    }
    // get/set для ID ГРУПИ
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    // get/set для ІМ'Я ГРУПИ
    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }
    // get/set для СПЕЦІАЛЬНІСТЬ
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String toString() {
        return nameGroup;
    }
}
