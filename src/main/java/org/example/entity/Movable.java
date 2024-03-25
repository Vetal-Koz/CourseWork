package org.example.entity;



public class Movable extends Materialmean{



    private Integer servicelife;


    private String specifications;


    private Float repaircost;


    public Movable(Integer id, String objname, Integer major, Integer classId, Integer amount, String physicalcondition, Float meancost, Integer servicelife, String specifications, Float repaircost) {
        super(id, objname, major, classId, amount, physicalcondition, meancost);
        this.servicelife = servicelife;
        this.specifications = specifications;
        this.repaircost = repaircost;
    }

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