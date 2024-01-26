package org.example.entity;



public class Materialmean extends Uniobject{


    private Integer amount;


    private String physicalcondition;


    private Float meancost;

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