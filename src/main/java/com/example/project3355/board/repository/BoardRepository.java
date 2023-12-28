package com.example.project3355.board.repository;

import com.example.project3355.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findTop10ByOrderByCreatedAtDesc();

    List<Board> findAllByUserIdOrderByCreatedAtDesc(Long id);
}