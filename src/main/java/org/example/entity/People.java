package org.example.entity;

import lombok.NoArgsConstructor;
import org.example.anotation.Table;

import java.time.LocalDateTime;
@NoArgsConstructor
@Table(name = "people")
public class People extends Uniobject{
    private LocalDateTime dateofbirth;


    private String sex;


    private String nationality;

    public People(Integer id, String objname, Integer major, Integer classId, LocalDateTime dateofbirth, String sex, String nationality) {
        super(id, objname, major, classId);
        this.dateofbirth = dateofbirth;
        this.sex = sex;
        this.nationality = nationality;
    }

    public LocalDateTime getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(LocalDateTime dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    @Override
    public String toString() {
        return super.getObjname();
    }

}