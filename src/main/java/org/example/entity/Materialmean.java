package org.example.entity;



public class Materialmean extends Uniobject{


    private Integer amount;


    private String physicalcondition;


    private Float meancost;

    public Materialmean(Integer id, String objname, Integer major, Integer classId, Integer amount, String physicalcondition, Float meancost) {
        super(id, objname, major, classId);
        this.amount = amount;
        this.physicalcondition = physicalcondition;
        this.meancost = meancost;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPhysicalcondition() {
        return physicalcondition;
    }

    public void setPhysicalcondition(String physicalcondition) {
        this.physicalcondition = physicalcondition;
    }

    public Float getMeancost() {
        return meancost;
    }

    public void setMeancost(Float meancost) {
        this.meancost = meancost;
    }

}