package org.example.entity;


import java.time.LocalDateTime;

public class Technicalstaff extends Employee{


    private String specialization;

    private String jobposition;

    public Technicalstaff(Integer id, String objname, Integer major, Integer classId, LocalDateTime dateofbirth, String sex, String nationality, Float salary, String education, Float experience, String specialization, String jobposition) {
        super(id, objname, major, classId, dateofbirth, sex, nationality, salary, education, experience);
        this.specialization = specialization;
        this.jobposition = jobposition;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getJobposition() {
        return jobposition;
    }

    public void setJobposition(String jobposition) {
        this.jobposition = jobposition;
    }

}