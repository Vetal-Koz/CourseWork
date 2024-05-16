package org.example.entity;


import lombok.NoArgsConstructor;
import org.example.anotation.Table;

import java.util.Objects;

@NoArgsConstructor
@Table(name="uniobject")
public class Uniobject {

    private Integer id;

    private String objname;

    private Integer major;

    private Integer classId;

    public Uniobject(Integer id, String objname, Integer major, Integer classId) {
        this.id = id;
        this.objname = objname;
        this.major = major;
        this.classId = classId;
    }

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
        return objname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Uniobject uniobject = (Uniobject) o;
        return Objects.equals(id, uniobject.id) && Objects.equals(objname, uniobject.objname)  && Objects.equals(classId, uniobject.classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, objname,  classId);
    }
}