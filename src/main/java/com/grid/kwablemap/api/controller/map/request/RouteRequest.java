package com.grid.kwablemap.api.controller.map.request;

import lombok.Getter;

@Getter
public class RouteRequest {
    private Coordinate start;
    private Coordinate end;

    public static class Coordinate {
        private Float xCoordinate;
        private Float yCoordinate;
    }
}
