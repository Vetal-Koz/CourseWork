package org.example.util;

import org.example.UniqueNumberSearchApp;
import org.example.entity.Uniobject;
import org.example.frames.UniobjectFrame;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UniobjectUtil {

    public static <E extends Uniobject> E generateClassByUniobj(Uniobject uniobject) {
        String className = UniqueNumberSearchApp.getNameOfClassUniObj(uniobject);
        String fullPathToClass = "org.example.entity." + className;
        try {
            List<Class<?>> classes = new ArrayList<>();
            Class<?> c = Class.forName(fullPathToClass);
            Class<?> tempClass = Class.forName(fullPathToClass);
            while (!tempClass.getName().equals("java.lang.Object")){
                classes.add(tempClass);
                tempClass = tempClass.getSuperclass();
            }
            for (Class<?> clas: classes ){
                for (Field field: clas.getDeclaredFields()){
                    System.out.println(field);
                    System.out.println(field.getType());
                    String str = "1";

                }
            }
            List<Object> objectsList = new ArrayList<>();
            Constructor<?> constructor = c.getConstructor(getParameterTypes(UniqueNumberSearchApp.mapForFillUniobj(uniobject)));
            UniqueNumberSearchApp.mapForFillUniobj(uniobject).forEach((k, v) -> {
                System.out.println("key: "+ k);
                System.out.println("value: " + v);
                objectsList.add(v);
            });



            Object[] arr = objectsList.toArray();
            return (E) constructor.newInstance(arr);



        }catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException| IllegalAccessException e){
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static <E extends UniobjectFrame> E generateFrameForUniObj(Uniobject uniobject){
        String className = UniqueNumberSearchApp.getFrameNameForUniObject(uniobject);
        String fullPathToClass = "org.example.frames." + className;
        try {
            Class<?> c = Class.forName(fullPathToClass);
            Constructor constructor = c.getConstructor(uniobject.getClass());
            return (E) constructor.newInstance(uniobject);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?>[] getParameterTypes(Map<String, Object> uniobject) {
        Class<?>[] parameterTypes = new Class<?>[uniobject.size()];
        int i = 0;
        for (Object value : uniobject.values()) {
            parameterTypes[i++] = value.getClass();
        }
        return parameterTypes;
    }
}
