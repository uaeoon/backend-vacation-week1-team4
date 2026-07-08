package mutsa.team4.store.repository;

import mutsa.team4.store.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByStore_StoreId(Long storeId);
}
