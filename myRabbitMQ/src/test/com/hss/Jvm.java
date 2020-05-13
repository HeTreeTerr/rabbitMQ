package com.hss;

import org.junit.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class Jvm {

    @Test
    public void test(){
        Student student = new Student();

        Student student1 = new Student();
        ReferenceQueue<Student> queue= new ReferenceQueue<Student>();
        SoftReference<Student> softReference = new SoftReference<Student>(student1,queue);

    }
}
