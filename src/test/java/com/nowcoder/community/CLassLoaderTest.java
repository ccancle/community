package com.nowcoder.community;

import sun.misc.Launcher;
import com.nowcoder.community.Test;
/**
 * @program: community
 * @description: 类加载器测试实例
 * @author: Macchac
 * @create: 2020-07-07 09:57
 **/
public class CLassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
//        System.out.println(System.getProperty("sun.boot.class.path"));
//        System.out.println(System.getProperty("java.ext.dirs"));
//        System.out.println(System.getProperty("java.class.path"));
//
 /**
        ClassLoader classLoader = Test.class.getClassLoader();
        System.out.println("ClassLoader is :"+classLoader.toString());
        System.out.println("ClassLoader\'s parent is :"+classLoader.getParent().toString());
        System.out.println("ClassLoader\'s parent\'s parent is :"+classLoader.getParent().getParent().toString());
        classLoader = int.class.getClassLoader();
        System.out.println("ClassLoader is :"+classLoader.toString());
*/
        ClassLoader loader = Test.class.getClassLoader();
        System.out.println(loader);
        //使用ClassLoader.loadClass()来加载类，不会执行初始化块
//        loader.loadClass("com.nowcoder.community.Test");
        //使用Class.forName()来加载类，默认会执行初始化块
//        Class.forName("com.nowcoder.community.Test");
        //使用Class.forName()来加载类，并指定ClassLoader，初始化时不执行静态块
        Class.forName("com.nowcoder.community.Test", false, loader);
    }
}
