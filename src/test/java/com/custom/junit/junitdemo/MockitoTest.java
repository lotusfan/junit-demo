package com.custom.junit.junitdemo;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @title: MockitoTest
 * @description: 官方示例 http://static.javadoc.io/org.mockito/mockito-core/2.20.0/org/mockito/Mockito.html
 * @author: zhangfan
 * @data: 2018年07月20日 18:11
 */
public class MockitoTest {

    Car car;

    @Before
    public void beforeTest() {
        car = mock(Car.class);
    }

    /**
     * mock类是不具有真实类的方法实现的
     */
    @Test
    public void mockObject() {

        Car car = mock(Car.class);
        System.out.println(car.getCarString("2222" ,null));

    }


    /**
     * OngoingStubbing<T> when(T methodCall)
     * <p>
     * OngoingStubbing: thenReturn、thenThrow、thenCallRealMethod、thenAnswer、getMock
     * <p>
     * 多个thenReturn会按调动顺序依次返回,直到最后
     */
    @Test
    public void mockWhen() {
        when(car.getCode()).thenReturn("111").thenReturn("2222");
        System.out.println(car.getCode());
        System.out.println(car.getCode());
        System.out.println(car.getCode());
    }

    /**
     * thenAnswer可以接收传入的参数，自定义方法返回
     * ArgumentMatchers: anyString()
     */
    @Test
    public void mockWhenAnswer() {
        when(car.getCarString(anyString(), anyString())).thenAnswer(invocation -> {

            System.out.println(invocation.getArgument(0) + "_" + invocation.getArgument(1));

            return "456789";
        });
        System.out.println(car.getCarString("11", "333"));
    }


    /**
     * verify(T mock, VerificationMode mode)
     * <p>
     * VerificationMode 构造 times(2), atLeastOnce(), atLeast(), atMost(), only(), never(), atLeastOnce(), description()
     */
    @Test
    public void mockVerify() {

        verify(car, times(2).description("get Code not appear 2 times")).getCode();

    }

    /**
     * 重置
     */
    @Test
    public void mockReset() {
        when(car.getCode()).thenReturn("aaaa");
        System.out.println(car.getCode());
        reset(car);
        System.out.println(car.getCode());

    }



    /**
     * 监控真实类，使用do-when创建mock
     *
     */
    @Test
    public void spyObject() {
        Car carReal = new Car();
        Car spyCar = spy(carReal);
        System.out.println(spyCar.getCarString(null, null));
    }


    /**
     * want void method throws Exception
     * doThrow可以添加多个Exception被依次调动，直到最后一个
     */
    @Test
    public void mockDoThrow() {

        doThrow(new RuntimeException("first"), new RuntimeException("second")).when(car).setCode(anyString());

        try {
            car.setCode("aaa");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            car.setCode("bbb");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            car.setCode("ccc");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 调用原始的方法实现
     */
    @Test
    public void mockDoCallRealMethod() {

        when(car.getCode()).thenReturn("bbbb");
        System.out.println(car.getCode());

        doCallRealMethod().when(car).getCode();
        System.out.println(car.getCode());

    }

    /**
     * void method 提供一个接收参数的自定义方法
     */
    @Test
    public void mockDoAnswer() {
        doAnswer(invocation -> null).when(car).setCode(anyString());
        car.setCode("3333");
    }

    /**
     * void method 不做任何操作
     */
    @Test
    public void mockDoNothing() {
        doNothing().doThrow(new RuntimeException()).when(car).setType(anyString());

        car.setType("333");
        car.setType("4444");
    }

    /**
     * 使用spy来监控真实的对象，需要注意的是此时我们需要谨慎的使用when-then语句，而改用do-when语句
     */
    @Test
    public void mockDoReturn() {
        Car carReal = new Car();
        Car spyCar = spy(carReal);
        System.out.println(spyCar.getCode());
    }

}

class Car {
    String type;
    String code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCarString(String year, String day) {

        return type + code + "_" + year + day;
    }
}