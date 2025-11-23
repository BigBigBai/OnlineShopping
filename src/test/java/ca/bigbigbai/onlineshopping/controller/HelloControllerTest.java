package ca.bigbigbai.onlineshopping.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {
    @Autowired
    private HelloController helloController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddService mockAddService;

    // 测试 /echo/{content} 端点 - 数字
    @Test
    void testEchoEndpointWithNumber() throws Exception {
        mockMvc.perform(get("/echo/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello 123"));
    }

    // 测试 /echo/{content} 端点 - 中文
    @Test
    void testEchoEndpointWithChinese() throws Exception {
        mockMvc.perform(get("/echo/世界"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello 世界"));
    }

    // 测试 /echo/{content} 端点 - 普通字符串
    @Test
    void testEchoEndpoint() throws Exception {
        mockMvc.perform(get("/echo/World"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    // 测试 / 端点
    @Test
    void testHelloEndpoint() throws Exception {
        mockMvc.perform((get("/")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World with Get"));
    }

    // 测试 helloWord() 方法
    @Test
    void testHelloWorld() {
        String res = helloController.helloGet();
        assertEquals("Hello World with Get", res);
    }
}
