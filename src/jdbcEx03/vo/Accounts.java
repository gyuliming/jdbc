package jdbcEx03.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Accounts {
    private String ano;
    private String owner;
    private int balance;
}