package org.example.entity;


import org.example.anotation.Table;

@Table(name = "immovable")
public class Immovable extends Materialmean{



    private Float area;


    private String adress;


    private String functionalpurpose;

    public Immovable(Integer id, String objname, Integer major, Integer classId, Integer amount, String physicalcondition, Float meancost, Float area, String adress, String functionalpurpose) {
        super(id, objname, major, classId, amount, physicalcondition, meancost);
        this.area = area;
        this.adress = adress;
        this.functionalpurpose = functionalpurpose;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getFunctionalpurpose() {
        return functionalpurpose;
    }

    public void setFunctionalpurpose(String functionalpurpose) {
        this.functionalpurpose = functionalpurpose;
    }

    @Override
    public String toString() {
        return super.getObjname();
    }

}