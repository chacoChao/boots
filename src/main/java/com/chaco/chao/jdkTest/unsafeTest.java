package com.chaco.chao.jdkTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * author:zhaopeiyan001
 * Date:2020-04-20 18:11
 */
public class unsafeTest {
    public static void main (String[] args) throws Exception {
        //反射实例化unsafe
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        //实例化
        Player player = (Player) unsafe.allocateInstance(Player.class);
        player.setName("lilei");
        System.out.println(player.getName());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Player {
        String name;
    }
}
