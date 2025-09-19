package com.grid.kwablemap.api.service.map;

import com.grid.kwablemap.api.controller.map.request.RouteRequest;
import com.grid.kwablemap.api.service.map.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {
    public RouteResponse findRoute(RouteRequest request) {
        return null;
    }
}
