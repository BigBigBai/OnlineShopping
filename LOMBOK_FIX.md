# Lombok 编译问题修复说明

## 问题诊断

编译时出现以下错误：

- `cannot find symbol method builder()`
- `cannot find symbol method setEmail()`
- `cannot find symbol variable log`

这些都是 Lombok 注解处理器未被正确执行导致的。

## 解决方案

### 1. **更新 pom.xml**

已添加以下配置：

```xml
<!-- maven-compiler-plugin 配置 -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.30</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>

<!-- Lombok 依赖 -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```

### 2. **Java 版本要求**

- **推荐**: Java 11 或更高版本
- **不兼容**: Java 23（与编译器的兼容性问题）

### 3. **配置默认 Java 版本（macOS）**

创建了 `.mavenrc` 文件来自动设置 Java 11：

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
```

### 4. **手动设置（如果需要）**

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 11)
mvn clean compile
```

## 验证修复

运行以下命令验证编译成功：

```bash
mvn clean compile
```

应该看到：

```
[INFO] BUILD SUCCESS
```

## 相关文件修改

- `pom.xml` - 添加了 maven-compiler-plugin 配置和 Lombok 版本定义
- `.mavenrc` - 新建文件，自动设置 Java 11 环境变量

## 注意事项

1. **Lombok 注解生成**：Lombok 会自动生成以下内容：

   - `@Data` → getters/setters
   - `@Builder` → builder 方法
   - `@Slf4j` → log 变量

2. **IDE 支持**：

   - IntelliJ IDEA：需要启用 Annotation Processors
   - VS Code：使用 Extension Pack for Java

3. **编译缓存**：如果仍有问题，执行：
   ```bash
   rm -rf ~/.m2/repository/org/projectlombok
   mvn clean compile
   ```
