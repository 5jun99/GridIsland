package com.grid.kwablemap.api.service.map.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "경로 검색 응답")
public class RouteResponse {
    @Schema(description = "경로상의 좌표 목록")
    private List<Coordinate> route;

    @Schema(description = "총 거리(미터)", example = "1500.5")
    private Float totalDistance;

    @Schema(description = "예상 소요시간(분)", example = "15")
    private Integer estimatedTime;

    @Getter
    @Builder
    @Schema(description = "경로 좌표")
    public static class Coordinate {
        @Schema(description = "X 좌표", example = "127.1234")
        private Float xCoordinate;

        @Schema(description = "Y 좌표", example = "37.5678")
        private Float yCoordinate;
    }
}
