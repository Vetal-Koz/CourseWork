package org.example.entity;

import org.example.anotation.Table;

@Table(name = "folders")
public class Folder extends Uniobject{

    public Folder(Integer id, String objname, Integer major, Integer classId) {
        super(id, objname, major, classId);
    }

}
