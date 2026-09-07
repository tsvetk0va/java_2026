package webshop.test;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

public class APITest {

    @Test
    @Tags({@Tag("API"), @Tag("positive")})
    void apiTest1(){
        System.out.println("API positive TEST");
    }

    @Test
    @Tags({@Tag("API"), @Tag("negative")})
    void apiTest2(){
        System.out.println("API negative TEST");
    }
}
