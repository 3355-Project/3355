package com.example.project3355.comment.repository;


import com.example.project3355.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {


}
