package com.example.project3355.card.service;

import com.example.project3355.user.entity.User;

public interface WatchService {

  void createWatch(Long id, User user);

  void deleteWatch(Long id, User user);
}
