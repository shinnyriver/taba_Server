package taba.tabaServer.tabaserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taba.tabaServer.tabaserver.enums.GenderEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", unique = true)
    private String loginId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<DrivingSession> drivingSessions = new ArrayList<>();

    @Builder
    public User(
            final String loginId,
            final String email,
            final String password,
            final GenderEnum gender
    ) {
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.createdAt = LocalDateTime.now();
    }
}
