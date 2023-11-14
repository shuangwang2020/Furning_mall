package com.hspedu.furns.test;

import java.util.HashMap;

/**
 * @author zpstart
 * @create 2023-11-14 22:57
 */
public class HspTest {
    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("1", "1");
        System.out.println(objectObjectHashMap);
        objectObjectHashMap.clear();
        System.out.println(objectObjectHashMap.size() == 0);
    }
}
