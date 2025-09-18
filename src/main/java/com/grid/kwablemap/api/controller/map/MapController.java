package com.grid.kwablemap.api.controller.map;

import com.grid.kwablemap.api.ApiResponse;
import com.grid.kwablemap.api.StatusCode;
import com.grid.kwablemap.api.controller.map.request.RouteRequest;
import com.grid.kwablemap.api.service.map.MapService;
import com.grid.kwablemap.api.service.map.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @PostMapping("/api/route")
    public ApiResponse<RouteResponse> getRoute(@RequestBody RouteRequest request) {
        return ApiResponse.of(StatusCode.OK, "루트 검색이 완료되었습니다.", mapService.findRoute(request));
    }
}
