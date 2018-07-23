package com.xiaogch.guava;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/18 16:30 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class ObjectsTest {

    static A a1;

    {
        a1 = new A(1);
    }

    static A a2 = new A(2);

    ObjectsTest() {
        System.out.print("ObjectsTest()");
        a2.f(1);
    }

    public static void main(String[] args) {
        ObjectsTest objectsTest = new ObjectsTest();
    }


}

class A {
    public A(int i) {
        System.out.print("A(" + i +")");
    }

    public void f(int i) {
        System.out.print("A.f(" + i +")");
    }
}
