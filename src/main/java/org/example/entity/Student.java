package org.example.entity;



public class Student extends People{


    private Float averagemark;


    private String unigroup;


    private Integer practicalexperience;


    public Float getAveragemark() {
        return averagemark;
    }

    public void setAveragemark(Float averagemark) {
        this.averagemark = averagemark;
    }

    public String getUnigroup() {
        return unigroup;
    }

    public void setUnigroup(String unigroup) {
        this.unigroup = unigroup;
    }

    public Integer getPracticalexperience() {
        return practicalexperience;
    }

    public void setPracticalexperience(Integer practicalexperience) {
        this.practicalexperience = practicalexperience;
    }

}