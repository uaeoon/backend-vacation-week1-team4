package mutsa.team4.credit.repository;

import mutsa.team4.credit.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    // 회원가입 및 로그인 로직 구현 후 수정 예정
    Optional<Credit> findByMemberId(Long memberId);
}
