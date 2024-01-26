package org.example.entity;



public class Paperdocument extends Document{



    private Integer pryzicalsize;

    private Boolean archiving = false;


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

}