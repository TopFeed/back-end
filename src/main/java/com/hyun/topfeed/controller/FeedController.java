package com.hyun.topfeed.controller;

import com.hyun.topfeed.dto.ApiStandardResponse;
import com.hyun.topfeed.entity.Feed;
import com.hyun.topfeed.service.FeedService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {

  private final FeedService feedService;

  /**
   * 커뮤니티 별 핫게시글 제공
   */
  @GetMapping()
  public ResponseEntity<ApiStandardResponse<List<Feed>>> showFeedByCommunity(
      @RequestHeader(value = "x-api-key", required = false) String apiKey,
      @RequestParam("community") String community) {
    List<Feed> feeds = feedService.showFeedByCommunity(community, apiKey);
    return ResponseEntity.ok(ApiStandardResponse.success(feeds));
  }
}
