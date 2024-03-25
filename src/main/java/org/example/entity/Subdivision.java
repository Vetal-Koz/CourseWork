package org.example.entity;


import lombok.ToString;

@ToString(callSuper = true)

public class Subdivision extends Uniobject{



    private String chef;

    public Subdivision(Integer id, String objname, Integer major, Integer classId, String chef) {
        super(id, objname, major, classId);
        this.chef = chef;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

}