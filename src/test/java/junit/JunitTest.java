package junit;

import org.junit.jupiter.api.*;

public class JunitTest {


    @BeforeAll
    static void setUP(){
        System.out.println("Before All");
    }

    @BeforeEach
    void before() {
        System.out.println("Before Each");
    }

    @AfterEach
    void after() {
        System.out.println("After Each");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After All");
    }


    @Nested //группировка тестов
    public class PositiveTest {
        @Test
        @Disabled("баг 123")
        void test1() {
            System.out.println("Test1");
        }

        @Test
        void test2() {
            System.out.println("Test2");
        }
    }

    @Nested //группировка тестов
    public class NegativeTest {
        @Test
        void test1(){
            System.out.println("Test1");
        }

        @Test
        void test2(){
            System.out.println("Test2");
        }
    }




}
