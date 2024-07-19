package com.hyun.topfeed.service;

import com.hyun.topfeed.entity.Feed;
import com.hyun.topfeed.exception.ApiKeyNotValidException;
import com.hyun.topfeed.repository.FeedJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedService {

  @Value("${spring.security.x-api-key}")
  private String xApiKey;

  private final FeedJpaRepository feedJpaRepository;

  /**
   * 게시글 제공
   */
  @Transactional
  public List<Feed> showFeedByCommunity(String community, String apiKey) {
    // API KEY 유효성 검사
    if (apiKey == null || !apiKey.equals(xApiKey)) {
      throw new ApiKeyNotValidException("API KEY가 올바르지 않습니다.");
    }

    List<Feed> feedList = feedJpaRepository.findAllByCommunity(community);
    return feedList;
  }

}
