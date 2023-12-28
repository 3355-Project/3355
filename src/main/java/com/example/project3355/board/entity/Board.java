package com.example.project3355.board.entity;


import com.example.project3355.board.dto.BoardRequestDto;
import com.example.project3355.global.common.Timestamped;
import com.example.project3355.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardTitle;

    @Column(nullable = false)
    private String boardColor;

    @Column(nullable = false)
    private String boardDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Board(BoardRequestDto requestDto, User user) {
        this.user = user;
        this.boardTitle = requestDto.getBoardTitle();
        this.boardColor = requestDto.getBoardColor();
        this.boardDescription = requestDto.getBoardDescription();
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.boardTitle=boardRequestDto.getBoardTitle();
        this.boardColor= boardRequestDto.getBoardColor();
        this.boardDescription= boardRequestDto.getBoardDescription();
    }
}
