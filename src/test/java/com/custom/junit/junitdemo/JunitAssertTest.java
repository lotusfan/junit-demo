package com.custom.junit.junitdemo;

import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.*;

/**
 * @title: JunitAssert
 * @description:
 * @author: zhangfan
 * @data: 2018年07月17日 19:35
 */
public class JunitAssertTest {


    /**
     * AbstractObjectAssert：as 描述断言判定失败时自定义内容
     *
     */
    @Test
    public void assertThatTest() {
        People people = new People();
        people.setAge(10);
        people.setName("fan");
        assertThat(people.getAge()).as("check age %d", people.getAge()).isEqualTo(10);


    }


    /**
     * ListAssert: filteredOn
     * contains、containsAnyOf、containsOnlyOnce
     *
     */
    @Test
    public void assertThatFilteredOn() {

        People people = new People(10, "a");
        People people1 = new People(20, "b");
        List<People> peopleList = Arrays.asList(people, people1);

        //filteredOn(String propertyOrFieldName,FilterOperator<?> filterOperator)
        //FilterOperator: 构造  InFilter.in、NotFilter.not、NotInFilter.notIn
        assertThat(peopleList).filteredOn("age", in(10)).containsOnly(people);
        assertThat(peopleList).filteredOn(p -> p.getAge() == 20).containsOnly(people1);

    }


    /**
     * PredicateAssert: accepts 接收数据，要满足test条件
     */
    @Test
    public void assertThatInPredicate() {
        List<String> list = Arrays.asList("aaa", "bbb");
        Predicate<List<String>> predicate = t -> {
            System.out.println(t);
            return t.contains("aaa") || t.contains("bbb");
        };
        assertThat(predicate).accepts(list);

        assertThat((Predicate) p -> true).rejects("bbbb");

        /**
         * 于此类似的还有AssertJ封装了IntPredicate、LongPredicate、DoublePredicate
         */
        assertThat((IntPredicate) pi -> pi > 0).accepts(3, 4);

    }


    /**
     * OptionalAssert:isPresent、isNotEmpty、containst等
     */
    @Test
    public void assertThatInOptional() {
//        Optional op = Optional.ofNullable(new People());
        Optional op = Optional.ofNullable("aaa");
//        assertThat(op).isEmpty();
        assertThat(op).isNotEmpty().contains("aaa");
    }

    /**
     * AbstractBigDecimalAssert<?> assertThat(BigDecimal actual)
     * AbstractBigIntegerAssert<?>  assertThat(BigInteger actual)
     * AbstractUriAssert<?> assertThat(URI actual)
     * AbstractUrlAssert<?> assertThat(URL actual)
     * AbstractBooleanAssert<?> assertThat(boolean actual)
     * AbstractBooleanArrayAssert<?> assertThat(boolean[] actual)
     * AbstractByteAssert<?> assertThat(byte actual)
     * AbstractByteArrayAssert<?> assertThat(byte[] actual)
     * AbstractCharacterAssert<?> assertThat(char actual)
     * AbstractCharacterAssert<?> assertThat(Character actual)
     * ClassAssert assertThat(Class<?> actual)
     * AbstractDoubleAssert<?> assertThat(double actual)
     * AbstractDoubleArrayAssert<?> assertThat(double[] actual)
     * AbstractFileAssert<?> assertThat(File actual)
     * <p>
     * FutureAssert<RESULT> assertThat(Future<RESULT> actual)
     * <p>
     * AbstractInputStreamAssert<?, ? extends InputStream> assertThat(InputStream actual)
     * AbstractFloatAssert<?> assertThat(float actual)
     * AbstractFloatArrayAssert<?> assertThat
     * AbstractIntegerAssert<?> assertThat(int actual)
     * <p>
     * <ACTUAL extends Iterable<? extends ELEMENT>, ELEMENT, ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>>
     * FactoryBasedNavigableIterableAssert<?, ACTUAL, ELEMENT, ELEMENT_ASSERT> assertThat(Iterable<? extends ELEMENT> actual,
     * AssertFactory<ELEMENT, ELEMENT_ASSERT> assertFactory)
     * <p>
     * <ELEMENT, ACTUAL extends List<? extends ELEMENT>, ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>>
     * ClassBasedNavigableListAssert<?, ACTUAL, ELEMENT, ELEMENT_ASSERT> assertThat(List<? extends ELEMENT> actual,
     *
     * 注：jdk中所有对象可有都封了特定的Assert，基本类型还封装了ArrayAssert 使用的时候可以因需而定
     */



    @Test
    public void assertThatExtracting() {
        People people = new People(10, "a");
        People people1 = new People(20, "b");
        List<People> peopleList = Arrays.asList(people, people1);

        assertThat(peopleList).extracting("name", "age").contains(tuple("a", 10)).doesNotContain(tuple("c", "33"));
    }
    /**
     *  tuple只能用在extracting后，创建了一个元组
     *  Utility method to build nicely a {@link org.assertj.core.groups.Tuple} when working with
     * {@link org.assertj.core.api.IterableAssert#extracting(String...)} or
     * {@link org.assertj.core.api.ObjectArrayAssert#extracting(String...)}
     *
     */
    @Test
    public void assertThatTuple() {
        People p1 = new People(10, "a");
        People p2 = new People(20, "b");
        List<People> peopleList = Arrays.asList(p1, p2);
        assertThat(peopleList).extracting("name", "age").contains(tuple("a", 10));
    }

    /**
     * MapAssert: contains、containsAnyOf、containsOnly、containsKeys、containsOnlyKeys、containsValues
     *
     * 注：
     * contains(Map.Entry<? extends KEY, ? extends VALUE>... entries) 入参为Map.entry,所以需要调用
     * Assertions：<K, V> MapEntry<K, V> entry(K key, V value) 构造
     */
    @Test
    public void assertThatEntry() {
        Map<String, People> map = new HashMap<>();
        map.put("akey", new People(10, "a"));

        assertThat(map).containsAnyOf(entry("bkey", new People(20, "b")), entry("akey", map.get("akey")));
    }

    /**
     * Assertions: atIndex 使用在 AbstractListAssert: contains(ELEMENT value, Index index) 方法中的index构造
     * 可能还有其它地方使用到
     */
    @Test
    public void assertThatAtIndex() {

        People p1 = new People(10, "a");
        People p2 = new People(20, "b");
        List<People> peopleList = Arrays.asList(p1, p2);

        assertThat(peopleList).extracting("name").contains("a", atIndex(0));
    }

    /**
     * 精度问题
     * Assertions entry point for float、double
     */
    @Test
    public void assertThatOffset() {
        assertThat(new Float(0.20)).isEqualTo(new Float(0.19), offset(new Float(0.01)));
        assertThat(0.2f).isEqualTo(0.19f, offset(0.01f));
    }

    @Test
    public void assertThatWithin() {
        assertThat(1).isCloseTo(-1, within(1));
    }

    @Test
    public void assertThatByLessThan() {
        assertThat(3).isCloseTo(9, byLessThan(2));
    }

    /**
     * AbstractObjectAssert: returns 验证 入参对象 调用方法返回值
     * from 构造 Function
     */
    @Test
    public void assertThatReturns() {
        assertThat(new People(10, "a")).returns("b", from(People::getName));
    }

    /**
     * AbstractAssert is，isNot，has，doesNotHave
     * AbstractObjectArrayAssert are，have，doNotHave，areAtLeast，haveExactly  集合条件
     *
     * condition组合 allOf anyOf doesNotHave not
     */
    @Test
    public void assertThatCondition() {
        People p1 = new People(10, "a");
        People p2 = new People(20, "b");
        List<People> peopleList = Arrays.asList(p1, p2);

        Condition<People> c1 = new Condition<>(people -> people.getName().equals("a"), "condition 1");
        Condition<People> c2 = new Condition<>(people -> people.getName().equals("b"), "condition 2");
        Condition<People> c3 = new Condition<>(people -> true, "condition 3");
        Condition<People> c4 = new Condition<>(people -> false, "condition 4");


        assertThat(peopleList).have(not(c4));
//        assertThat(peopleList).have(anyOf(c1, c2, c3));

    }

    /**
     * Assertions:filter 按条件筛选数据并重新生成列表进行校鸡
     */

    @Test
    public void assertThatFilter() {
        People p1 = new People(10, "a");
        People p2 = new People(20, "b");
        List<People> peopleList = Arrays.asList(p1, p2);

        assertThat(filter(peopleList).and("name").equalsTo("a").and("age").equalsTo("10").get()).extracting("name").contains("b");
    }

}

class People {

    public People(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public People() {
    }

    int age;
    String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
