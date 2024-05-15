package org.example.entity;


import lombok.NoArgsConstructor;
import org.example.anotation.Table;

import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "students")
public class Student extends People{


    private Float averagemark;


    private String unigroup;


    private Integer practicalexperience;

    public Student(Integer id, String objname, Integer major, Integer classId, LocalDateTime dateofbirth, String sex, String nationality, Float averagemark, String unigroup, Integer practicalexperience) {
        super(id, objname, major, classId, dateofbirth, sex, nationality);
        this.averagemark = averagemark;
        this.unigroup = unigroup;
        this.practicalexperience = practicalexperience;
    }

    public Float getAveragemark() {
        return averagemark;
    }

    public void setAveragemark(Float averagemark) {
        this.averagemark = averagemark;
    }

    public String getUnigroup() {
        return unigroup;
    }

    public void setUnigroup(String unigroup) {
        this.unigroup = unigroup;
    }

    public Integer getPracticalexperience() {
        return practicalexperience;
    }

    public void setPracticalexperience(Integer practicalexperience) {
        this.practicalexperience = practicalexperience;
    }


    @Override
    public String toString() {
        return super.getObjname();
    }

}