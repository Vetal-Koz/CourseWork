package org.example.entity;




public class Department extends Subdivision{

    private String teachingfocus;

    private Float budget;


    public String getTeachingfocus() {
        return teachingfocus;
    }

    public void setTeachingfocus(String teachingfocus) {
        this.teachingfocus = teachingfocus;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

}