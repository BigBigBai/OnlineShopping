package ca.bigbigbai.onlineshopping.service;

import ca.bigbigbai.onlineshopping.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JwtService的单元测试
 * 不依赖Spring容器，使用反射注入配置值
 */
@ExtendWith(MockitoExtension.class)
public class JwtServiceUnitTest {
    private JwtService jwtService;

    // 测试用的配置值
    private static final String TEST_SECRET_KEY = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e0089b";
    private static final long TEST_EXPIRATION_TIME = 360000L; // 6分钟

    private UserModel testUser;

    @BeforeEach
    public void setUp() throws Exception {
        // 创建JwtService实例
        jwtService = new JwtService();

        // 使用反射注入私有字段的值（模拟@Value注解的效果）
        setPrivateField(jwtService, "secretKey", TEST_SECRET_KEY);
        setPrivateField(jwtService, "jwtExpiration", TEST_EXPIRATION_TIME);

        // 创建测试用户
        testUser = UserModel.builder()
                .id(1)
                .name("张三")
                .email("zhangsan@example.com")
                .build();
    }

    /**
     * 使用反射设置私有字段的值
     */
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testEncryptUser_ShouldReturnValidJwtToken() {
        // 执行加密
        String token = jwtService.encryptUser(testUser);

        // 验证token不为空
        assertNotNull(token, "加密后的token不应该为null");
        assertFalse(token.isEmpty(), "加密后的token不应该为空字符串");

        // 验证token格式（JWT token有三部分：header，payload，signature）
        String[] parts = token.split("\\.");
        assertEquals(3, parts.length, "JWT token应该包含3个部分");
    }

    @Test
    public void testEncryptUser_TokenContainsUserData() {
        // 加密用户
        String token = jwtService.encryptUser(testUser);

        // 手动解析token验证内容
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(TEST_SECRET_KEY));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 验证claims中包含用户信息
        assertEquals(1, claims.get("id"), "token中应该包含用户ID");
        assertEquals("张三", claims.get("name"), "token中应该包含用户名");
        assertEquals("zhangsan@example.com", claims.get("email"), "token中应该包含邮箱");
    }

    @Test
    public void testEncryptUser_TokenContainsTimestamps() {
        // 加密用户
        long beforeEncryption = System.currentTimeMillis();
        String token = jwtService.encryptUser(testUser);
        long afterEncryption = System.currentTimeMillis();

        // 解析token
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(TEST_SECRET_KEY));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 验证签发时间在合理范围内
        // 注意：JWT时间戳是秒级精度，需要给一些容差（1秒）
        long issuedAt = claims.getIssuedAt().getTime();
        assertTrue(issuedAt >= beforeEncryption - 1000 && issuedAt <= afterEncryption + 1000,
                "签发时间应该在加密前后的时间范围内（考虑秒级精度）");

        // 验证过期时间
        long expiration = claims.getExpiration().getTime();
        long expectedExpiration = issuedAt + TEST_EXPIRATION_TIME;
        assertEquals(expectedExpiration, expiration, "过期时间应该等于签发时间加上配置的过期时长");
    }

    @Test
    public void testDecryptUserName_ShouldReturnCorrectUserName() {
        // 先加密
        String token = jwtService.encryptUser(testUser);

        // 再解密
        String decryptedName = jwtService.decryptUserName(token);

        // 验证解密结果
        assertEquals("张三", decryptedName, "解密出的用户名应该与原始用户名一致");
    }

    @Test
    public void testDecryptUserName_WithDifferentUsers() {
        // 创建多个用户并测试
        UserModel user1 = UserModel.builder()
                .id(1)
                .name("用户A")
                .email("userA@example.com")
                .build();

        UserModel user2 = UserModel.builder()
                .id(2)
                .name("用户B")
                .email("userB@example.com")
                .build();

        // 加密
        String token1 = jwtService.encryptUser(user1);
        String token2 = jwtService.encryptUser(user2);

        // 解密并验证
        assertEquals("用户A", jwtService.decryptUserName(token1));
        assertEquals("用户B", jwtService.decryptUserName(token2));
    }

    @Test
    public void testDecryptUserName_WithInvalidToken_ShouldThrowException() {
        // 测试无效的token格式
        String invalidToken = "invalid.jwt.token";

        // 验证会抛出异常
        assertThrows(Exception.class, () -> {
            jwtService.decryptUserName(invalidToken);
        }, "解密无效token应该抛出异常");
    }

    @Test
    public void testDecryptUserName_WithExpiredToken_ShouldThrowException() throws Exception {
        // 创建一个已过期的token（使用很短的过期时间）
        JwtService shortLivedService = new JwtService();
        setPrivateField(shortLivedService, "secretKey", TEST_SECRET_KEY);
        setPrivateField(shortLivedService, "jwtExpiration", 1L);// 1ms就过期

        String token = shortLivedService.encryptUser(testUser);

        // 等待token过期
        Thread.sleep(10);

        // 验证解密过期token会抛出异常
        assertThrows(Exception.class, () -> {
            jwtService.decryptUserName(token);
        }, "解密过期token应该抛出异常");
    }

    @Test
    public void testDecryptUserName_WithWrongSignature_ShouldThrowException() throws Exception {
        // 使用一个secret key加密
        String token = jwtService.encryptUser(testUser);

        // 创建另一个使用不同secret key的service
        JwtService differentKeyService = new JwtService();

        String differentSecretKey = "differentkey76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e0089b";
        setPrivateField(differentKeyService, "secretKey", differentSecretKey);
        setPrivateField(differentKeyService, "jwtExpiration", TEST_EXPIRATION_TIME);

        // 验证使用不同的key解密会失败
        assertThrows(Exception.class, () -> {
            differentKeyService.decryptUserName(token);
        }, "使用错误的签名解密应该抛出异常");
    }

    @Test
    public void testDecryptUserName_WhenNameNotInToken_ShouldReturnNone() {
        // 手动创建一个不包含name字段的token
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(TEST_SECRET_KEY));
        String tokenWithoutName = Jwts.builder()//注意：没有name字段
                .claim("id", 999)
                .claim("email", "test@example.com")
                .signWith(key)
                .compact();

        // 解密应该返回默认值“None”
        String result = jwtService.decryptUserName(tokenWithoutName);
        assertEquals("None", result, "当token中没有name字段时，应该返回默认值‘None’");
    }

    @Test
    public void testEncryptUser_WithSpecialCharacters() {
        // 创建包含特殊字符的用户
        UserModel specialUser = UserModel.builder()
                .id(100)
                .name("李四-Test@123!#$%")
                .email("lisi+test@example.com")
                .build();

        // 加密
        String token = jwtService.encryptUser(specialUser);
        assertNotNull(token);

        // 解密并验证
        String decryptedName = jwtService.decryptUserName(token);
        assertEquals("李四-Test@123!#$%", decryptedName, "应该正确处理包含特殊字符的用户名");
    }

    @Test
    public void testEncryptUser_WithEmptyName() {
        //
        UserModel emptyNameUser = UserModel.builder()
                .id(200)
                .name("")
                .email("empty@example.com")
                .build();

        // 加密
        String token = jwtService.encryptUser(emptyNameUser);
        assertNotNull(token);

        // 解密并验证
        String decryptedName = jwtService.decryptUserName(token);
        assertEquals("", decryptedName, "应该正确处理空字符串用户名");
    }

    @Test
    public void testEncryptUser_WithNullName() {
        // 创建用户名为null的用户
        UserModel nullNameUser = UserModel.builder()
                .id(300)
                .name(null)
                .email("null@example.com")
                .build();
        // 加密
        String token = jwtService.encryptUser(nullNameUser);
        assertNotNull(token);

        // 解密应该返回“None”(因为name为null时，getOrDefault会返回默认值)
        String decryptedName = jwtService.decryptUserName(token);
        assertTrue(decryptedName == null || decryptedName.equals("None") || decryptedName.equals("null"),
                "用户名为null时，解密结果应该是null，‘None’或‘null’字符串");
    }

    @Test
    public void testEncryptUser_WithLongName() {
        // 创建一个很长的用户名（Java 8兼容方式）
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("A");
        }
        String longName = sb.toString();

        UserModel longNameUser = UserModel.builder()
                .id(400)
                .name(longName)
                .email("long@example.com")
                .build();

        // 加密
        String token = jwtService.encryptUser(longNameUser);
        assertNotNull(token);

        // 解密并验证
        String decryptedName = jwtService.decryptUserName(token);
        assertEquals(longName, decryptedName, "应该正确处理很长的用户名");
    }

    @Test
    public void testEncryptUser_WithUnicodeCharacters() {
        // 创建包含各种Unicode字符的用户
        UserModel unicodeUser = UserModel.builder()
                .id(500)
                .name("张三🎉😀中文日本語한국어")
                .email("unicode@example.com")
                .build();

        // 加密
        String token = jwtService.encryptUser(unicodeUser);
        assertNotNull(token);

        // 解密并验证
        String decryptedName = jwtService.decryptUserName(token);
        assertEquals("张三🎉😀中文日本語한국어", decryptedName,
                "应该正确处理Unicode字符（包括emoji）");
    }

    @Test
    public void testEncryptDecrypt_RoundTrip() {
        // 测试加密解密的往返过程
        String originalName = testUser.getName();

        // 加密
        String token = jwtService.encryptUser(testUser);

        // 解密
        String decryptedName = jwtService.decryptUserName(token);

        // 验证往返后数据一致
        assertEquals(originalName, decryptedName,
                "加密解密往返后，用户名应该保持不变");
    }
}
