package mutsa.team4.store.service;

import lombok.RequiredArgsConstructor;
import mutsa.team4.global.exception.GeneralException;
import mutsa.team4.store.code.StoreErrorCode;
import mutsa.team4.store.domain.Menu;
import mutsa.team4.store.domain.OptionGroup;
import mutsa.team4.store.domain.Store;
import mutsa.team4.store.dto.*;
import mutsa.team4.store.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 현재 조회 기능만 존재
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final CategoryRepository categoryRepository;

    // 가게 목록 조회
    public List<StoreResponseDto> getStores(Long categoryId) {
        // 카테 고리 존재 여부 확인 후 미존재 시 에러 던짐
        if (categoryId != null && !categoryRepository.existsById(categoryId)) {
            throw new GeneralException(StoreErrorCode.CATEGORY_NOT_FOUND);
        }

        List<Store> stores = categoryId == null
                ? storeRepository.findAll()
                : storeRepository.findAllByCategory_CategoryId(categoryId);

        return stores.stream()
                .map(StoreResponseDto::from)
                .toList();
    }

    // 가게 단건 조회
    public StoreDetailResponseDto getStore(Long storeId) {
        // 가게 존재 여부 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(StoreErrorCode.STORE_NOT_FOUND));
        // 해당 가게 메뉴 목록 조회
        List<Menu> menus = menuRepository.findAllByStore_StoreId(storeId);
        // 메뉴별 옵션 그룹과 옵션 조회 후 응답 DTO로 변환
        List<MenuResponseDto> menuResponses = menus.stream()
                .map(menu -> {
                    List<OptionGroup> optionGroups =
                            optionGroupRepository.findAllByMenu_MenuId(menu.getMenuId());

                    List<OptionGroupResponseDto> optionGroupResponses = optionGroups.stream()
                            .map(optionGroup -> {
                                List<MenuOptionResponseDto> optionResponses =
                                        menuOptionRepository
                                                .findAllByOptionGroup_OptionGroupId(optionGroup.getOptionGroupId())
                                                .stream()
                                                .map(MenuOptionResponseDto::from)
                                                .toList();

                                return OptionGroupResponseDto.from(optionGroup, optionResponses);
                            })
                            .toList();

                    return MenuResponseDto.from(menu, optionGroupResponses);
                })
                .toList();
        // 가게 상세 응답 DTO 생성
        return StoreDetailResponseDto.from(store, menuResponses);
    }
}
