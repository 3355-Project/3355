package com.example.project3355.board.service;


import com.example.project3355.board.dto.BoardRequestDto;
import com.example.project3355.board.dto.BoardResponseDto;
import com.example.project3355.board.entity.Board;
import com.example.project3355.board.entity.UserBoard;
import com.example.project3355.board.repository.BoardRepository;
import com.example.project3355.board.repository.UserBoardRepository;
import com.example.project3355.user.UserDetailsImpl;
import com.example.project3355.user.entity.User;
import com.example.project3355.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserBoardRepository userBoardRepository;

    // 생성
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
        Board board = new Board(boardRequestDto, user);
        board.setUser(user);
        var saved = boardRepository.save(board);
        UserBoard userBoard = new UserBoard(board,user);
        userBoardRepository.save(userBoard);
        return new BoardResponseDto(saved);
    }

    // 조회
    public BoardResponseDto getBoard(Long boardId) {
        return new BoardResponseDto(findBoardById(boardId));

    }
    private Board findBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 boardId입니다."));
    }

    // 수정
    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto , UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Board board = verifyUser(user, boardId);

        board.update(boardRequestDto);

        // BoardResponseDto로 업데이트된 정보를 반환하도록 변경
        return new BoardResponseDto(board);
    }

    // 삭제
    public String deleteBoard(Long boardId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Board board = verifyUser(user, boardId);
        boardRepository.delete(board);
        return "삭제 성공";
    }

    private Board verifyUser(User user, Long boardId) {
        Board board = findBoardById(boardId);
        if(!board.getUser().getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("해당사용자가 아닙니다.");
        }
        return board;
    }

    //초대
    @Transactional
    public void inviteUserToBoard(Long userId, Long boardId, Long invitedUserId) {
        Optional<Board> boardOptional = findByBoard(boardId);
        if (boardOptional.isEmpty()) {
            throw new IllegalArgumentException("보드를 찾을 수 없습니다.");
        }
        Board board = boardOptional.get();
        // 본인 초대 불가
        if (board.getOwner().getId().equals(invitedUserId)) {
            throw new IllegalArgumentException("자기 자신을 초대할 수 없습니다.");
        }
        // 보드 소유자 확인
        if (!board.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("보드 소유자만 초대할 수 있습니다.");
        }
        User invitedUser = userRepository.findById(invitedUserId)
                .orElseThrow(() -> new IllegalArgumentException("초대받은 유저를 찾을 수 없습니다."));

        if (userBoardRepository.findByBoardIdAndUserId(boardId, invitedUserId).isPresent()) {
            throw new IllegalArgumentException("이미 초대된 유저입니다.");
        }
        UserBoard userBoard = new UserBoard(board, invitedUser);
        userBoardRepository.save(userBoard);
    }
    private Optional<Board> findByBoard(Long boardId) {
        return boardRepository.findById(boardId);
    }
}

