package com.grid.kwablemap.domain.repository;

import com.grid.kwablemap.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    @Query(value = """
            SELECT * FROM node n
            ORDER BY SQRT(POWER(n.x_coordinate - :x, 2) + POWER(n.y_coordinate - :y, 2))
            LIMIT 1
            """, nativeQuery = true)
    Node findNearestNode(@Param("x") Float x, @Param("y") Float y);
}