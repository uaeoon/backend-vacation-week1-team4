package mutsa.team4.store.controller;

import lombok.RequiredArgsConstructor;
import mutsa.team4.global.apiPayload.ApiResponse;
import mutsa.team4.store.dto.StoreDetailResponseDto;
import mutsa.team4.store.dto.StoreResponseDto;
import mutsa.team4.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    // 가게 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<StoreResponseDto>>> getStores(
            @RequestParam(required = false) Long categoryId
    ) {
        List<StoreResponseDto> response = storeService.getStores(categoryId);

        return ResponseEntity.ok(
                ApiResponse.onSuccess("가게 목록 조회에 성공했습니다.", response)
        );
    }

    // 가게 단건 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse<StoreDetailResponseDto>> getStore(
            @PathVariable Long storeId
    ) {
        StoreDetailResponseDto response = storeService.getStore(storeId);

        return ResponseEntity.ok(
                ApiResponse.onSuccess("가게 단건 조회에 성공했습니다.", response)
        );
    }
}
