package com.grid.kwablemap.api.controller.map.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "경로 검색 요청")
public class RouteRequest {
    @Schema(description = "출발지 좌표", required = true)
    private Coordinate start;

    @Schema(description = "도착지 좌표", required = true)
    private Coordinate end;

    @Getter
    @Schema(description = "좌표 정보")
    public static class Coordinate {
        @Schema(description = "X 좌표", example = "127.1234", required = true)
        private Float xCoordinate;

        @Schema(description = "Y 좌표", example = "37.5678", required = true)
        private Float yCoordinate;
    }
}
