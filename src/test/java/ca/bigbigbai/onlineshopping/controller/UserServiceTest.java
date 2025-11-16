package ca.bigbigbai.onlineshopping.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Resource
    UserService userService;

    @Mock
    AddService mockAddService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addMock() {
        when(mockAddService.add(anyInt(), anyInt())).thenReturn(100);
        userService = new UserService(mockAddService);
        int res = userService.add5(1,2);
        assertEquals(105, res);
    }

    @Test
    void addReal() {
        int res = userService.add5(1,2);
        assertEquals(8, res);
    }
}