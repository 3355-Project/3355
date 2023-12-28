package com.example.project3355.board.repository;

import com.example.project3355.board.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

    Optional<Object> findByBoardIdAndUserId(Long boardId, Long invitedUserId);
}
