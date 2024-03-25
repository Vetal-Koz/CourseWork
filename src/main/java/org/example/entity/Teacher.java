package org.example.entity;


import java.time.LocalDateTime;

public class Teacher extends Employee{

    private String subject;


    private String academictitles;


    private Float rating;

    public Teacher(Integer id, String objname, Integer major, Integer classId, LocalDateTime dateofbirth, String sex, String nationality, Float salary, String education, Float experience, String subject, String academictitles, Float rating) {
        super(id, objname, major, classId, dateofbirth, sex, nationality, salary, education, experience);
        this.subject = subject;
        this.academictitles = academictitles;
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAcademictitles() {
        return academictitles;
    }

    public void setAcademictitles(String academictitles) {
        this.academictitles = academictitles;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

}