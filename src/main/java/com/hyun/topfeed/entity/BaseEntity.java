package com.hyun.topfeed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity {

  // 수정 시간
  @LastModifiedDate
  @Column(name = "date", nullable = false)
  private LocalDateTime date;

  BaseEntity() {
    // 한국 시간으로 설정하기 위해서 9시간 더함
    ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
    date = ZonedDateTime.now(koreaZoneId).plusHours(9).toLocalDateTime();
  }
}
