package com.example.project3355.card.service;

import com.example.project3355.card.entity.Card;
import com.example.project3355.card.entity.Watch;
import com.example.project3355.card.repository.CardRepository;
import com.example.project3355.card.repository.WatchRepository;
import com.example.project3355.global.exception.columns.ApiException;
import com.example.project3355.global.exception.common.ErrorCode;
import com.example.project3355.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WatchServiceImpl implements WatchService {
  private final WatchRepository watchRepository;
  private final CardRepository cardRepository;

  @Override
  public void createWatch(Long id, User user) {
    Card card = cardRepository.findById(id).orElseThrow(()-> new ApiException(ErrorCode.INVALID_CARD));
    if(!watchRepository.existsByCardIdAndUserId(card.getId(), user.getId())){
      Watch watch = new Watch(card,user);
      watchRepository.save(watch);
    }
    else {
      throw  new ApiException(ErrorCode.INVALID_WATCH_EXIST);
    }


  }

  @Override
  public void deleteWatch(Long id, User user) {
    Watch watch = watchRepository.findById(id).orElseThrow(()-> new ApiException(ErrorCode.INVALID_WATCH));

    watchRepository.delete(watch);

  }
}
