package com.example.project3355.user.service;

import com.example.project3355.global.exception.user.AlreadyExistEmailException;
import com.example.project3355.global.exception.user.AlreadyExistUsernameException;
import com.example.project3355.global.exception.user.AuthenticationMismatchException;
import com.example.project3355.global.exception.user.PasswordConfirmationException;
import com.example.project3355.global.exception.user.PasswordMismatchException;
import com.example.project3355.global.exception.user.UserNotFoundException;
import com.example.project3355.user.dto.UserInfoResponseDto;
import com.example.project3355.user.dto.UserLoginRequestDto;
import com.example.project3355.user.dto.UserPasswordUpdateRequestDto;
import com.example.project3355.user.dto.UserProfileUpdateRequestDto;
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
      throw new PasswordConfirmationException();
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

  @Transactional
  public void updateProfile(
      Long userId, UserProfileUpdateRequestDto requestDto, User loginUser) {

    User user = getUser(userId);

    // 로그인한 유저와 수정할 프로필의 유저가 일치하는지 확인
    if (!loginUser.getUsername().equals(user.getUsername())) {
      throw new AuthenticationMismatchException();
    }

    user.update(requestDto);
  }

  @Transactional
  public void updatePassword(
      Long userId, UserPasswordUpdateRequestDto requestDto, User loginUser) {
    String password = requestDto.getPassword();
    String updatePassword = requestDto.getUpdatePassword();
    String checkUpdatePassword = requestDto.getCheckUpdatePassword();

    User user = getUser(userId);


    if (!loginUser.getUsername().equals(user.getUsername())) {
      throw new AuthenticationMismatchException();
    }

    if (!updatePassword.equals(checkUpdatePassword)) {
      throw new PasswordConfirmationException();
    }

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new PasswordMismatchException();
    } else {
      updatePassword = passwordEncoder.encode(updatePassword);
      user.setPassword(updatePassword);
    }

    userRepository.save(user);
  }

  public User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }

}
