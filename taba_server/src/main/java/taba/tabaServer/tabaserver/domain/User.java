package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.config.oauth.OAuthProvider;
import taba.tabaServer.tabaserver.enums.UserActiveStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OAuthProvider oauthProvider;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = true, unique = true)
    private String email;

    //Ouath2 기능을 위해 nullable=true로 설정
    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "birthyear", nullable = false)
    private String birthyear;

    @Column(name = "birthday", nullable = false)
    private String birthday;

    @Column(name = "mobile_number", nullable = false)
    private String mobile;

    //Ouath2 기능을 위해 nullable=true로 설정
    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt;

    @Column(name = "withdraw_at", nullable = true)
    private LocalDateTime withdrawAt;

    @Column(name = "active_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserActiveStatus userActiveStatus;

    @Builder
    public User(
            final String name,
            final String email,
            final String password,
            final String gender,
            final String birthday,
            final String birthyear,
            final String mobile,
            final OAuthProvider oauthProvider,
            final UserActiveStatus userActiveStatus
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.createdAt = LocalDateTime.now();
        this.mobile = mobile;
        this.oauthProvider = oauthProvider;
        this.userActiveStatus = UserActiveStatus.ACTIVE;
    }

    public void withdraw() {
        this.name = "withdraw";
        this.password = "0000";
        this.email = null;
        this.withdrawAt = LocalDateTime.now();
        this.userActiveStatus = UserActiveStatus.WITHDRAW;
    }
}