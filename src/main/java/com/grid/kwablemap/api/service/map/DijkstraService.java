package com.grid.kwablemap.api.service.map;

import com.grid.kwablemap.domain.Edge;
import com.grid.kwablemap.domain.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DijkstraService {

    public List<Node> findShortestPath(Node startNode, Node endNode, List<Node> allNodes, List<Edge> allEdges) {
        Map<Long, Float> distances = new HashMap<>();
        Map<Long, Node> previous = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparing(nd -> nd.distance));
        Set<Long> visited = new HashSet<>();

        // 그래프 구성 - 노드별 연결된 엣지들
        Map<Long, List<Edge>> nodeEdges = new HashMap<>();
        for (Edge edge : allEdges) {
            nodeEdges.computeIfAbsent(edge.getFromNode().getId(), k -> new ArrayList<>()).add(edge);
            nodeEdges.computeIfAbsent(edge.getToNode().getId(), k -> new ArrayList<>()).add(edge);
        }

        for (Node node : allNodes) {
            distances.put(node.getId(), Float.MAX_VALUE);
        }
        distances.put(startNode.getId(), 0.0f);
        pq.offer(new NodeDistance(startNode, 0.0f));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Long currentNodeId = current.node.getId();

            if (visited.contains(currentNodeId)) {
                continue;
            }
            visited.add(currentNodeId);

            if (currentNodeId.equals(endNode.getId())) {
                break;
            }

            List<Edge> edges = nodeEdges.getOrDefault(currentNodeId, new ArrayList<>());
            for (Edge edge : edges) {
                Node neighbor = getNeighborNode(edge, current.node);
                if (neighbor == null || visited.contains(neighbor.getId())) {
                    continue;
                }

                float cost = calculateCost(edge, current.node, neighbor);
                float newDistance = distances.get(currentNodeId) + cost;

                if (newDistance < distances.get(neighbor.getId())) {
                    distances.put(neighbor.getId(), newDistance);
                    previous.put(neighbor.getId(), current.node);
                    pq.offer(new NodeDistance(neighbor, newDistance));
                }
            }
        }

        return reconstructPath(previous, startNode, endNode);
    }

    private Node getNeighborNode(Edge edge, Node currentNode) {
        if (edge.getFromNode().getId().equals(currentNode.getId())) {
            return edge.getToNode();
        } else if (edge.getToNode().getId().equals(currentNode.getId())) {
            return edge.getFromNode();
        }
        return null;
    }

    private float calculateCost(Edge edge, Node startNode, Node endNode) {
        float distance = edge.getDistance();
        float averageScore = (startNode.getScore() + endNode.getScore()) / 2.0f;
        return distance + averageScore;
    }

    private List<Node> reconstructPath(Map<Long, Node> previous, Node startNode, Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(current);
            current = previous.get(current.getId());
        }

        if (!path.get(path.size() - 1).getId().equals(startNode.getId())) {
            return new ArrayList<>(); // 경로가 없음
        }

        Collections.reverse(path);
        return path;
    }

    private static class NodeDistance {
        Node node;
        Float distance;

        NodeDistance(Node node, Float distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}