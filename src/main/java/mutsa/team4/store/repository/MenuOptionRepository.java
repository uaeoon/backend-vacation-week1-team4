package mutsa.team4.store.repository;

import mutsa.team4.store.domain.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
    List<MenuOption> findAllByOptionGroup_OptionGroupId(Long optionGroupId);
}
