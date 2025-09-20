package com.grid.kwablemap.api.controller.map;

import com.grid.kwablemap.api.ApiResponse;
import com.grid.kwablemap.api.StatusCode;
import com.grid.kwablemap.api.controller.map.request.RouteRequest;
import com.grid.kwablemap.api.service.map.MapService;
import com.grid.kwablemap.api.service.map.response.RouteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Map API", description = "지도 및 경로 검색 API")
public class MapController {
    private final MapService mapService;

    @PostMapping("/api/route")
    @Operation(summary = "경로 검색", description = "시작점과 끝점을 입력받아 최적 경로를 반환합니다")
    public ApiResponse<RouteResponse> getRoute(
            @RequestBody @Parameter(description = "경로 검색 요청") RouteRequest request) {
        return ApiResponse.of(StatusCode.OK, "루트 검색이 완료되었습니다.", mapService.findRoute(request));
    }
}
