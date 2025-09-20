package com.grid.kwablemap.api.service.map;

import com.grid.kwablemap.api.controller.map.request.RouteRequest;
import com.grid.kwablemap.api.service.map.response.RouteResponse;
import com.grid.kwablemap.domain.Edge;
import com.grid.kwablemap.domain.Node;
import com.grid.kwablemap.domain.repository.EdgeRepository;
import com.grid.kwablemap.domain.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService {
    private final NodeRepository nodeRepository;
    private final EdgeRepository edgeRepository;
    private final DijkstraService dijkstraService;

    public RouteResponse findRoute(RouteRequest request) {
        // 1. 시작점과 끝점 좌표에서 가장 가까운 노드 찾기
        Node startNode = nodeRepository.findNearestNode(
            request.getStart().getXCoordinate(),
            request.getStart().getYCoordinate()
        );
        Node endNode = nodeRepository.findNearestNode(
            request.getEnd().getXCoordinate(),
            request.getEnd().getYCoordinate()
        );

        if (startNode == null || endNode == null) {
            return createEmptyResponse();
        }

        // 2. 모든 노드와 엣지 데이터 가져오기
        List<Node> allNodes = nodeRepository.findAll();
        List<Edge> allEdges = edgeRepository.findAll();

        // 3. 다익스트라 알고리즘으로 최단 경로 찾기
        List<Node> shortestPath = dijkstraService.findShortestPath(startNode, endNode, allNodes, allEdges);

        if (shortestPath.isEmpty()) {
            return createEmptyResponse();
        }

        // 4. 경로 응답 생성
        return createRouteResponse(shortestPath);
    }

    private RouteResponse createEmptyResponse() {
        return RouteResponse.builder()
            .route(List.of())
            .totalDistance(0.0f)
            .estimatedTime(0)
            .build();
    }

    private RouteResponse createRouteResponse(List<Node> path) {
        List<RouteResponse.Coordinate> coordinates = path.stream()
            .map(node -> RouteResponse.Coordinate.builder()
                .xCoordinate(node.getXCoordinate())
                .yCoordinate(node.getYCoordinate())
                .build())
            .collect(Collectors.toList());

        float totalDistance = calculateTotalDistance(path);
        int estimatedTime = calculateEstimatedTime(totalDistance);

        return RouteResponse.builder()
            .route(coordinates)
            .totalDistance(totalDistance)
            .estimatedTime(estimatedTime)
            .build();
    }

    private float calculateTotalDistance(List<Node> path) {
        float total = 0.0f;
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);

            float dx = next.getXCoordinate() - current.getXCoordinate();
            float dy = next.getYCoordinate() - current.getYCoordinate();
            total += Math.sqrt(dx * dx + dy * dy);
        }
        return total;
    }

    private int calculateEstimatedTime(float distance) {
        // 평균 속도 5km/h 기준으로 계산 (보행 속도)
        float speedKmPerHour = 5.0f;
        float distanceKm = distance / 1000.0f;
        float timeHours = distanceKm / speedKmPerHour;
        return Math.round(timeHours * 60); // 분 단위로 반환
    }
}
