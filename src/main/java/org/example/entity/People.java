package org.example.entity;

import java.time.Instant;
import java.time.LocalDateTime;

public class People extends Uniobject{




    private LocalDateTime dateofbirth;


    private String sex;


    private String nationality;



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

}