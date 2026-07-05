package mutsa.team4.credit.repository;

import mutsa.team4.credit.domain.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    Optional<Credit> findByMember_MemberId(Long memberId);
}
