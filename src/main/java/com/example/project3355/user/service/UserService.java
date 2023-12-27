package com.example.project3355.user.service;

import com.example.project3355.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private UserRepository userRepository;

}
