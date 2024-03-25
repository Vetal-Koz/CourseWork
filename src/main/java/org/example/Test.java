package org.example;

import org.example.entity.Uniobject;
import org.example.frames.UniobjectFrame;
import org.example.util.UniobjectUtil;

import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<Uniobject> list = UniqueNumberSearchApp.searchInstanceWithMajorNull();
        Uniobject obj = UniobjectUtil.generateClassByUniobj(list.get(0));
        System.out.println(obj);
        UniobjectFrame uniobjectFrame = UniobjectUtil.generateFrameForUniObj(obj);
    }
}
