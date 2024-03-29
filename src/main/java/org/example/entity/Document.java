package org.example.entity;


import java.time.Instant;

public class Document extends Uniobject{

    private String theme;


    private Instant createddate;


    private String doclanguage;

    public Document(Integer id, String objname, Integer major, Integer classId, String theme, Instant createddate, String doclanguage) {
        super(id, objname, major, classId);
        this.theme = theme;
        this.createddate = createddate;
        this.doclanguage = doclanguage;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Instant getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Instant createddate) {
        this.createddate = createddate;
    }

    public String getDoclanguage() {
        return doclanguage;
    }

    public void setDoclanguage(String doclanguage) {
        this.doclanguage = doclanguage;
    }

}