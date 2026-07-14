package mutsa.team4.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Member {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    public static Member createMember(String name, String email, String password) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
