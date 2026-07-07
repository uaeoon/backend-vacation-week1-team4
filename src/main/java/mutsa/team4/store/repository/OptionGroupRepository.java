package mutsa.team4.store.repository;

import mutsa.team4.store.domain.OptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {
    List<OptionGroup> findAllByMenu_MenuId(Long menuId);
}
