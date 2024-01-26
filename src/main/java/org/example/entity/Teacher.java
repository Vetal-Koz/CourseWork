package org.example.entity;



public class Teacher extends Employee{

    private String subject;


    private String academictitles;


    private Float rating;


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