package org.example.entity;


import org.example.anotation.Table;

@Table(name = "faculty")
public class Faculty extends Subdivision{



    private String curricula;


    private String faclocation;

    public Faculty(Integer id, String objname, Integer major, Integer classId, String chef, String curricula, String faclocation) {
        super(id, objname, major, classId, chef);
        this.curricula = curricula;
        this.faclocation = faclocation;
    }

    public String getCurricula() {
        return curricula;
    }

    public void setCurricula(String curricula) {
        this.curricula = curricula;
    }

    public String getFaclocation() {
        return faclocation;
    }

    public void setFaclocation(String faclocation) {
        this.faclocation = faclocation;
    }

    @Override
    public String toString() {
        return super.getObjname();
    }

}