package mutsa.team4.store.repository;

import mutsa.team4.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByCategory_CategoryId(Long categoryId);
}
