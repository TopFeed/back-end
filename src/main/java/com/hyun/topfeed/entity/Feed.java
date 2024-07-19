package com.hyun.topfeed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "feed")
@Getter
@ToString(exclude = "feed_id")
@NoArgsConstructor
public class Feed extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feed_id")
  private Long feedId;

  // 게시글 제목
  @Column(name = "title")
  private String title;

  // 해당 게시글의 링크
  @Column(name = "link")
  private String link;

  // 어떤 커뮤니티의 게시글인가?
  @Column(name = "community")
  private String community;

  private Feed(String title, String link, String community) {
    this.title = title;
    this.link = link;
    this.community = community;
  }

  public static Feed createFeed(String title, String link, String community) {
    return new Feed(title, link, community);
  }
}
