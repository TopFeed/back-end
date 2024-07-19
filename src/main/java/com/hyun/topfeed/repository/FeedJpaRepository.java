package com.hyun.topfeed.repository;

import com.hyun.topfeed.entity.Feed;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FeedJpaRepository extends JpaRepository<Feed, Long> {

  List<Feed> findAllByCommunity(String community);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM feed WHERE community = :community", nativeQuery = true)
  void deleteFeedsByCommunity(String community);
}
