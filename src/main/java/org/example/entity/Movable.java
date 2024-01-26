package org.example.entity;



public class Movable extends Materialmean{



    private Integer servicelife;


    private String specifications;


    private Float repaircost;



    public Integer getServicelife() {
        return servicelife;
    }

    public void setServicelife(Integer servicelife) {
        this.servicelife = servicelife;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Float getRepaircost() {
        return repaircost;
    }

    public void setRepaircost(Float repaircost) {
        this.repaircost = repaircost;
    }

}