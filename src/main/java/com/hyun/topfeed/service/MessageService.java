package com.hyun.topfeed.service;

import com.hyun.topfeed.entity.Feed;
import com.hyun.topfeed.repository.FeedJpaRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  @Value("${spring.mail.username}")
  private String mailHost;

  private final FeedJpaRepository feedJpaRepository;
  private final JavaMailSender javaMailSender;


  /**
   * 이메일 발송
   */
  public String sendSimpleMail(String message, String email) {
    // SMTP 설정
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    // 발신자
    simpleMailMessage.setFrom(mailHost);

    // 수신자
    simpleMailMessage.setTo(email);

    // 제목
    String title = "[TopFeed] 오늘의 핫 게시글";
    simpleMailMessage.setSubject(title);

    // 내용
    message += "자세한 내용은 사이트에서 확인해주세요!\nhttps://www.catholic.ac.kr/index.do"; // 링크 수정
    simpleMailMessage.setText(message);

    try {
      javaMailSender.send(simpleMailMessage);
      return "이메일 전송 성공! " + email;
    } catch (MailException e) {
      return "이메일 전송 실패: " + e.getMessage();
    }
  }

  /**
   * 메세지 발송에 대한 응답 취합
   */
  public void sendMessage(String email) {
    List<String> communityList = List.of(new String[]{"디시인사이드", "에펨코리아", "네이트판", "더쿠"});

    // 메세지 만들기
    String message = "";
    for (String community : communityList) {
      message += makeMessage(community);
    }

    // 이메일 보내기
    String responseByEmail = sendSimpleMail(message, email);
    System.out.println(responseByEmail);
  }

  /**
   * 기본 메세지 만들기
   */
  public String makeMessage(String community) {
    String message = "";
    List<Feed> feeds = feedJpaRepository.findTop3ByCommunity(community, PageRequest.of(0, 3));
    message += "[" + community + "]\n";
    for (int i = 0; i < 3; i++) {
      message += feeds.get(i).getTitle() + "\n";
    }
    message += "\n";
    return message;
  }

  /**
   * 인증 코드 전송하기 - 등록 이메일로
   */
  public String sendAuthenticationMessage(String email, String code)
      throws IOException {
    // SMTP 설정
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    // 발신자
    simpleMailMessage.setFrom(mailHost);

    // 수신자
    simpleMailMessage.setTo(email);

    // 제목
    String title = "[TopFeed] 이메일 등록을 위한 인증번호입니다.";
    simpleMailMessage.setSubject(title);

    // 내용
    String message = "인증번호는 " + code + "입니다.\n5분 이내에 입력해주시기 바랍니다."; // 링크 수정
    simpleMailMessage.setText(message);

    try {
      javaMailSender.send(simpleMailMessage);
      return "이메일 전송 성공! " + email;
    } catch (MailException e) {
      return "이메일 전송 실패: " + e.getMessage();
    }
  }

}

