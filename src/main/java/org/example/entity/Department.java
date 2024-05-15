package org.example.entity;


import org.example.anotation.Table;


@Table(name = "department")
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

    @Override
    public String toString() {
        return super.getObjname();
    }
}