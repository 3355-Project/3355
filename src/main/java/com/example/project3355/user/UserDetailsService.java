package com.example.project3355.user;

import com.example.project3355.global.exception.user.UserNotFoundException;
import com.example.project3355.user.entity.User;
import com.example.project3355.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

  private final UserRepository userRepository;

  public UserDetails getUserDetails(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(UserNotFoundException::new);
    return new UserDetailsImpl(user);
  }
}
