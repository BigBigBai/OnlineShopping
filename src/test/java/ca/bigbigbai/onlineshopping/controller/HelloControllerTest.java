package ca.bigbigbai.onlineshopping.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HelloControllerTest {
    HelloController helloController;

//    @Mock
//    DependencyA mock;
//
//    @Resource
//    DependencyA dependencyA;
//
//    @BeforeEach
//    void setUp() {
//        helloController = new HelloController(dependencyA);
//    }

    @Test
    void helloWorld_actual() {
        String res = helloController.helloWorld();
        assertEquals("Hello world", res);
    }

//    @Test
//    void helloWorld_mock() {
//        helloController = new HelloController(mock);
//        when(mock.send(any())).thenReturn("abcd");
//        String res = helloController.helloWorld();
//        assertEquals("abcd", res);
//    }
//
//    @Test
//    void testHelloWord() {
//        String res = helloController.echo("ABC");
//        assertEquals("You just Input : ABC", res);
//    }
}
