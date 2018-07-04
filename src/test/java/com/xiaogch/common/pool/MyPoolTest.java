package com.xiaogch.common.pool;

import com.xiaogch.pool.MyPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * ProjectName: demo<BR>
 * File name: CommonUtil.java <BR>
 * Author: guich <BR>
 * Project: demo <BR>
 * Version: v 1.0 <BR>
 * Date: 2018/7/4 12:32 <BR>
 * Description: <BR>
 * Function List:  <BR>
 */
public class MyPoolTest {

    public static void main(String[] args) {
        MyPooledObjectFactory<Stutdent> factory = new MyPooledObjectFactory<>();
        GenericObjectPool<Stutdent> pool = new GenericObjectPool<Stutdent>(factory);
        try {
            pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Stutdent {
        private String name;
        private String age;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
