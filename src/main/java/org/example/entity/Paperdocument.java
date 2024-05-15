package org.example.entity;


import org.example.anotation.Table;

import java.time.Instant;

@Table(name = "paperdocuments")
public class Paperdocument extends Document{



    private Integer pryzicalsize;

    private Boolean archiving = false;

    public Paperdocument(Integer id, String objname, Integer major, Integer classId, String theme, Instant createddate, String doclanguage, Integer pryzicalsize, Boolean archiving) {
        super(id, objname, major, classId, theme, createddate, doclanguage);
        this.pryzicalsize = pryzicalsize;
        this.archiving = archiving;
    }

    public Integer getPryzicalsize() {
        return pryzicalsize;
    }

    public void setPryzicalsize(Integer pryzicalsize) {
        this.pryzicalsize = pryzicalsize;
    }

    public Boolean getArchiving() {
        return archiving;
    }

    public void setArchiving(Boolean archiving) {
        this.archiving = archiving;
    }

    @Override
    public String toString() {
        return super.getObjname();
    }

}