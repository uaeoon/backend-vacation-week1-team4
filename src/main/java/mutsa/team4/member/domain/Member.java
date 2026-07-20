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
    @Column(nullable = false)
    private String email;
    @Column
    private String password;

    // 카카오 로그인을 위한 필드
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginProvider provider;

    public enum LoginProvider {
        LOCAL,
        KAKAO
    }

    @Column(nullable = false)
    private String providerId;

    public static Member createMember(String name, String email, String password) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .provider(LoginProvider.LOCAL)
                .providerId(email)
                .build();
    }

    public static Member createKakaoMember(String email, String nickname, String providerId) {
        return Member.builder()
                .name(nickname)
                .email(email)
                .provider(LoginProvider.KAKAO)
                .providerId(providerId)
                .build();
    }
}
