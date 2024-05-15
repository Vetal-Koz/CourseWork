package org.example.entity;


import org.example.anotation.Table;

import java.time.Instant;

@Table(name = "multimediadocument")
public class Multimediadocument extends Electronicdocument{

    private String design;

    public Multimediadocument(Integer id, String objname, Integer major, Integer classId, String theme, Instant createddate, String doclanguage, String format, Float docsize, Boolean docsecurity, String design) {
        super(id, objname, major, classId, theme, createddate, doclanguage, format, docsize, docsecurity);
        this.design = design;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    @Override
    public String toString() {
        return super.getObjname();
    }

}