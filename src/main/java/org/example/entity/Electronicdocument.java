package org.example.entity;



public class Electronicdocument extends Document{


    private String format;


    private Float docsize;


    private Boolean docsecurity = false;


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

}