package com.custom.junit.junitdemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @title: BasicTest
 * @description:
 * @author: zhangfan
 * @data: 2018年07月16日 11:29
 */
public class BasicTest {

    @Before
    public void fe() {
        System.out.println("before");
    }

    @Test
    public void print() {

        System.out.println("basic test");
    }


}
