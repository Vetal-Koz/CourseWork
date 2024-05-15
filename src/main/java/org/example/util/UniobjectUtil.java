package org.example.util;

import org.example.dao.UniobjectDao;
import org.example.anotation.Table;
import org.example.createFrame.UniObjectFrameCreate;
import org.example.entity.Uniobject;
import org.example.frames.UniobjectFrame;
import org.example.updateFrame.UniObjectFrameUpdate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UniobjectUtil {

    public static <E extends Uniobject> E generateClassByUniobj(Uniobject uniobject) {
        String className = UniobjectDao.getNameOfClassUniObj(uniobject);
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
            Constructor<?> constructor = c.getConstructor(getParameterTypes(UniobjectDao.mapForFillUniobj(uniobject)));
            UniobjectDao.mapForFillUniobj(uniobject).forEach((k, v) -> {
                System.out.println("key: "+ k);
                System.out.println("value: " + v);
                objectsList.add(v);
            });

            Object[] arr = objectsList.toArray();
            return (E) constructor.newInstance(arr);



        }catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException| IllegalAccessException e){
            throw new RuntimeException(e);
        }

    }

    public static List<String> getAllTablesNameRelatedToUniobj(Uniobject uniobject) {
        List<String> tablesName = new ArrayList<>();
        Class<?> concreteClass = getUnioBjectClass(uniobject);
        tablesName.add(getNameOfTableByClass(concreteClass));
        while (concreteClass.getSuperclass() != Object.class){
            concreteClass = concreteClass.getSuperclass();
            tablesName.add(getNameOfTableByClass(concreteClass));
        }
        return tablesName;
    }

    public static List<String> getAllTablesNameRelatedToUniobjById(Integer uniobjectId) {
        List<String> tablesName = new ArrayList<>();
        Class<?> concreteClass = getUnioBjectClassById(uniobjectId);
        tablesName.add(getNameOfTableByClass(concreteClass));
        while (concreteClass.getSuperclass() != Object.class){
            concreteClass = concreteClass.getSuperclass();
            tablesName.add(getNameOfTableByClass(concreteClass));
        }
        return tablesName;
    }

    public static String getNameOfTableByClass(Class<?> concreteClass){
        Table[] annotationsByType = concreteClass.getAnnotationsByType(Table.class);
        if (annotationsByType[0] != null){
            return annotationsByType[0].name();
        }
        else {
            throw new RuntimeException("Such anotations do not exist");
        }
    }

    private static Class<?> getUnioBjectClass(Uniobject uniobject){
        String className = UniobjectDao.getNameOfClassUniObj(uniobject);
        String fullPathToClass = "org.example.entity." + className;
        try {
            return Class.forName(fullPathToClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getUnioBjectClassById(Integer uniobjectId){
        String className = UniobjectDao.getNameOfClassUniObjById(uniobjectId);
        String fullPathToClass = "org.example.entity." + className;
        try {
            return Class.forName(fullPathToClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E extends UniobjectFrame> E generateFrameForUniObj(Uniobject uniobject){
        String className = UniobjectDao.getFrameNameForUniObject(uniobject);
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

    public static <E extends UniObjectFrameUpdate> E generateFrameUpdateForUniObj(Uniobject uniobject){
        String className = UniobjectDao.getFrameNameForUniObject(uniobject);
        String fullPathToClass = "org.example.updateFrame." + className + "Update";
        try {
            Class<?> c = Class.forName(fullPathToClass);
            Constructor constructor = c.getConstructor(uniobject.getClass());
            return (E) constructor.newInstance(uniobject);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <E extends UniObjectFrameCreate> E generateFrameCreate(Integer classId, Integer major){
        String className = UniobjectDao.getFrameNameForUniObjectByClassId(classId);
        String fullPathToClass = "org.example.createFrame." + className + "Create";
        try {
            Class<?> c = Class.forName(fullPathToClass);
            Constructor constructor = c.getConstructor(Integer.class, Integer.class);
            return (E) constructor.newInstance(classId, major);
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
