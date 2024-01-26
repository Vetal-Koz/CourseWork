package org.example.entity;



public class Faculty extends Subdivision{



    private String curricula;


    private String faclocation;


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

}