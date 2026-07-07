package mutsa.team4.credit.repository;

import mutsa.team4.credit.domain.CreditHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Long> {
    List<CreditHistory> findAllByCredit_CreditIdOrderByCreatedAtDesc(Long creditId);
}
