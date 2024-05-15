package org.example.entity;


import org.example.anotation.Table;

import java.time.Instant;

@Table(name = "electronicdocuments")
public class Electronicdocument extends Document{


    private String format;


    private Float docsize;


    private Boolean docsecurity = false;

    public Electronicdocument(Integer id, String objname, Integer major, Integer classId, String theme, Instant createddate, String doclanguage, String format, Float docsize, Boolean docsecurity) {
        super(id, objname, major, classId, theme, createddate, doclanguage);
        this.format = format;
        this.docsize = docsize;
        this.docsecurity = docsecurity;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Float getDocsize() {
        return docsize;
    }

    public void setDocsize(Float docsize) {
        this.docsize = docsize;
    }

    public Boolean getDocsecurity() {
        return docsecurity;
    }

    public void setDocsecurity(Boolean docsecurity) {
        this.docsecurity = docsecurity;
    }

    @Override
    public String toString() {
        return super.getObjname();
    }

}