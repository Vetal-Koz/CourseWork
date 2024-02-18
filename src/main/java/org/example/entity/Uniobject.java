package org.example.entity;



public class Uniobject {

    private Integer id;

    private String objname;

    private Integer major;

    private Integer classId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjname() {
        return objname;
    }

    public void setObjname(String objname) {
        this.objname = objname;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Uniobject{" +
                "id=" + id +
                ", objname='" + objname + '\'' +
                ", major=" + major +
                ", classId=" + classId +
                '}';
    }
}