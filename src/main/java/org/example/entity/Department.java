package org.example.entity;


import lombok.ToString;

@ToString(callSuper = true)

public class Department extends Subdivision{

    private String teachingfocus;

    private Float budget;

    public Department(Integer id, String objname, Integer major, Integer classId, String chef, String teachingfocus, Float budget) {
        super(id, objname, major, classId, chef);
        this.teachingfocus = teachingfocus;
        this.budget = budget;
    }

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