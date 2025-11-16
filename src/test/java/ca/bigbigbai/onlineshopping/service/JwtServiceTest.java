package ca.bigbigbai.onlineshopping.service;

import ca.bigbigbai.onlineshopping.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTest {
    @Autowired
    private JwtService jwtService;

    private UserModel testUser;

    @BeforeEach
    public void setUp() {
        testUser = UserModel.builder()
                .id(1)
                .name("张三")
                .email("zhangsan@example.com")
                .build();
    }

    @Test
    public void testEncryptUser_ShouldReturnValidToken() {
        // 测试加密功能 - 应该返回有效的JWT token

        // 验证token不为空

        // 验证token的格式（JWT token应该有三部分，用.分隔）

    }

    @Test
    public void testDecryptUserName_ShouldReturnCorrectUserName() {
        // 测试解密功能 - 应该能正确提取用户名

        // 验证解密出的用户名与原始用户名一致

    }

    @Test
    public void testEncryptAndDecrypt_CompleteFlow() {
        // 测试完整的加密解密流程
        // 1.加密用户

        // 2.解密用户

        // 3.验证解密结果


    }

    @Test
    public void testEncryptDifferentUsers_ShouldGenerateDifferentTokens() {
        // 测试不同用户应该生成不同的token


        // 验证不同用户生成的token不同

        // 验证解密后能得到正确的用户名
    }

    @Test
    public void testDecryptUserName_WithInvalidToken_ShouldThrowException() {
        // 测试使用无效token的解密应该抛出异常

    }

    @Test
    public void testEncryptUser_WithSpecialCharacters() {
        // 测试包含特殊字符的用户名

    }

    @Test
    public void testTokenContainsUserInformation() {
        // 验证token中确实包含了用户信息

        // 手动解析token验证payload内容

        // 验证可以成功提取用户名
    }

    @Test
    public void testMultipleEncryptionsSameUser_DifferentTokens() throws InterruptedException {
        // 测试同一用户多次加密应该生成不同的token （因为时间戳不同）

        // JWT的时间戳是秒级精度，需要等待至少1秒以上确保iat字段不同

        // 虽然是同一用户，但因为时间戳不同(秒级)，token应该不同

        // 但解密后应该得到相同的用户名
    }


}
