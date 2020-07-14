package com.nowcoder.community;

/**
 * @program: community
 * @description: Object类方法测试
 * @author: Macchac
 * @create: 2020-07-07 17:18
 **/
public class ObjectTest {
    public static void main(String[] args) {
        Object o = new Object();
        Object o2 = new Object();
        String x = "hahha";
        String x1 = "hahha";
        Integer h = 123;
        Integer h1 = 123;
        Integer m1 = 200;
        Integer m2 = 200;
//        o.equals(o2);
        System.out.println(o.hashCode());
        System.out.println(o2.hashCode());
        System.out.println("------------------------");
        System.out.println(x.hashCode());
        System.out.println(x1.hashCode());
        System.out.println(x==x1);
        System.out.println(x.equals(x1));
        System.out.println(x.hashCode()==x1.hashCode());
        System.out.println("------------------------");
        System.out.println(h.hashCode());
        System.out.println(h1.hashCode());
        System.out.println(h==h1);
        System.out.println(h.equals(h1));
        System.out.println(h.hashCode()==h1.hashCode());
        System.out.println("------------------------");
        System.out.println(m1.hashCode());
        System.out.println(m2.hashCode());
        System.out.println(m2==m1);
        System.out.println(m1.equals(m2));
        System.out.println(m1.hashCode()==m2.hashCode());
        System.out.println("------------------------");
    }
}
