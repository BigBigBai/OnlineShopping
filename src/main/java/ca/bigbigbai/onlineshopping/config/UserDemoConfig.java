package ca.bigbigbai.onlineshopping.config;

import ca.bigbigbai.onlineshopping.UserModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDemoConfig {
    @Bean(name = "nobody")
    public UserModel z3Provider() {
        return UserModel.builder()
                .id(0)
                .name("nobody")
                .build();
    }

    @Bean(name = "l4")
    public UserModel l4Provider() {
        return  UserModel.builder()
                .id(4)
                .name("l4")
                .email("z4@hotmail.com")
                .build();
    }
}
