package org.example.entity;


import org.example.anotation.Table;

import java.time.LocalDateTime;

@Table(name = "employees")
public class Employee extends People{

    private Float salary;

    private String education;

    private Float experience;

    public Employee(Integer id, String objname, Integer major, Integer classId, LocalDateTime dateofbirth, String sex, String nationality, Float salary, String education, Float experience) {
        super(id, objname, major, classId, dateofbirth, sex, nationality);
        this.salary = salary;
        this.education = education;
        this.experience = experience;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Float getExperience() {
        return experience;
    }

    public void setExperience(Float experience) {
        this.experience = experience;
    }


    @Override
    public String toString() {
        return super.getObjname();
    }

}