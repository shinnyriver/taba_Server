package taba.tabaServer.tabaserver.config.infra.naver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverUserDto {
    private String email;
    private String name;
    private String gender;
    private String birthday;
    private String birthyear;
    private String mobile;
}