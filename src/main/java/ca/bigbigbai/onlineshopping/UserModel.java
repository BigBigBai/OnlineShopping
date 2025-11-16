package ca.bigbigbai.onlineshopping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserModel {
    public int id;
    public String name;
    public String email;
}
