package com.hyun.topfeed.entity;

import jakarta.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity {

  // 최근 수정 시간
  @LastModifiedDate
  @Column(name = "date", nullable = false)
  private ZonedDateTime date;

  BaseEntity() {
    // 한국 시간으로 설정
    ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
    date = ZonedDateTime.now(koreaZoneId);
  }

}
