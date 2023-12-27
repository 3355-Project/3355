package com.example.project3355.user.service;

import com.example.project3355.global.exception.user.AlreadyExistEmailException;
import com.example.project3355.global.exception.user.AlreadyExistUsernameException;
import com.example.project3355.global.exception.user.PasswordMismatchException;
import com.example.project3355.global.exception.user.UserNotFoundException;
import com.example.project3355.user.dto.UserInfoResponseDto;
import com.example.project3355.user.dto.UserLoginRequestDto;
import com.example.project3355.user.dto.UserSignupRequestDto;
import com.example.project3355.user.entity.User;
import com.example.project3355.user.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signup(UserSignupRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = requestDto.getPassword();
    String checkPassword = requestDto.getCheckPassword();
    String email = requestDto.getEmail();
    String introduce = requestDto.getIntroduce();

    // 비밀번호와 비밀번호 확인이 일치하지 않는 경우
    if (!Objects.equals(password, checkPassword)) {
      throw new PasswordMismatchException();
    }

    String encodePassword = passwordEncoder.encode(password);

    // 유저네임의 중복유무
    if (userRepository.findByUsername(username).isPresent()) {
      throw new AlreadyExistUsernameException();
    }

    // 이메일 중복유무
    if (userRepository.findByEmail(email).isPresent()) {
      throw new AlreadyExistEmailException();
    }

    User user = new User(username, encodePassword, email, introduce);
    userRepository.save(user);
  }

  public void login(UserLoginRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = requestDto.getPassword();

    // 저장된 회원이 없는 경우
    User user = userRepository.findByUsername(username)
        .orElseThrow(UserNotFoundException::new);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new UserNotFoundException();
    }
  }

  public UserInfoResponseDto getProfile(Long userId) {
    User user = getUser(userId);
    return new UserInfoResponseDto(user);
  }

  public User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }
}
