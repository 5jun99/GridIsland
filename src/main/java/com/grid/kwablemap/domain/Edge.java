package com.grid.kwablemap.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "edges")
@Getter
public class Edge {

    @Id
    private Long id;

    private Float distance;
    private Integer cost;

    // 출발 노드
    @ManyToOne
    @JoinColumn(name = "from_node_id", nullable = false)
    private Node fromNode;

    // 도착 노드
    @ManyToOne
    @JoinColumn(name = "to_node_id", nullable = false)
    private Node toNode;
}
