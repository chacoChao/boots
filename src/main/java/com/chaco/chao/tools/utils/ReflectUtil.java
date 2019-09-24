package com.chaco.chao.tools.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author voidsun
 * @Date 2015/7/2
 * @Email voidsun@126.com
 */
public class ReflectUtil<T> {
    private Map<String, Method> methodMap;

    public ReflectUtil(){}

    public ReflectUtil(Class clazz){
        methodMap = new HashMap<>();
        for(Method method : clazz.getMethods()){
            methodMap.put(method.getName(), method);
        }
    }
    public Object invokeMethod(T t, String methodName, Object... inParams){
        Method method = methodMap.get(methodName);
        if(method != null){
            try {
                return method.invoke(t, inParams);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Object invokeGetter(T t, String filedName){
        String methodName = "get" + StringUtils.capitalize(filedName);
        return invokeMethod(t, methodName);
    }

    public void invokeSetter(T t, String fieldName, Object param){
        String methodName = "set" + StringUtils.capitalize(fieldName);
        invokeMethod(t, methodName, param);
    }

    /**
     * 利用反射获取指定对象的指定属性
     * @param obj 目标对象
     * @param fieldName 目标属性
     * @return 目标属性的值
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public Object getFieldValue(Object obj, String fieldName)
            throws IllegalArgumentException, IllegalAccessException {
        Object result = null;
        Field field = getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            result = field.get(obj);
        }
        return result;
    }

    /**
     * 利用反射获取指定对象里面的指定属性
     * @param obj 目标对象
     * @param fieldName 目标属性
     * @return 目标字段
     */
    private Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz =
                clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
            }
        }
        return field;
    }

    /**
     * 利用反射设置指定对象的指定属性为指定的值
     * @param obj 目标对象
     * @param fieldName 目标属性
     * @param fieldValue 目标值
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void setFieldValue(Object obj, String fieldName, String fieldValue)
            throws IllegalArgumentException, IllegalAccessException {
        Field field = getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            field.set(obj, fieldValue);
        }
    }
    
	public void setFieldValueAlany(Object obj, String fieldName) 
			 throws IllegalArgumentException, IllegalAccessException{
		Field field = getField(obj, fieldName);
       if (field != null) {
           field.setAccessible(true);
           if(field.getType().equals(String.class)) {
           	field.set(obj, "");
           }
           if(field.getType().equals(Integer.class)) {
           	field.set(obj, new Integer(0));
           }
           if(field.getType().equals(Double.class)) {
           	field.set(obj, new Double(0));
           }
           if(field.getType().equals(float.class)) {
           	field.set(obj, 0f);
           }
           if(field.getType().equals(Long.class)) {
           	field.set(obj, 0L);
           }
           if(field.getType().equals(long.class)) {
           	field.set(obj, 0l);
           }
           if(field.getType().equals(Boolean.class)) {
           	field.set(obj, true);
           }
           if(field.getType().equals(Date.class)) {
           	field.set(obj, new Date());
           }
       }
		
	}

}
