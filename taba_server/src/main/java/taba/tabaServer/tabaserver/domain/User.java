package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.enums.GenderEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "birthday", nullable = false)
    private LocalDate userBirth;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public User(
            final String email,
            final String password,
            final GenderEnum gender,
            final LocalDate userBirth
    ) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.userBirth = userBirth;
        this.createdAt = LocalDateTime.now();
    }


    public void updateUser(
            String email,
            String password,
            GenderEnum gender,
            LocalDate userBirth
    ) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.userBirth = userBirth;
    }
}
