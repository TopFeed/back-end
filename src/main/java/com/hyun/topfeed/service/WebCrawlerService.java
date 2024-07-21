package com.hyun.topfeed.service;

import com.hyun.topfeed.entity.Feed;
import com.hyun.topfeed.entity.User;
import com.hyun.topfeed.repository.FeedJpaRepository;
import com.hyun.topfeed.repository.UserJpaRepository;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebCrawlerService {

  @Value("${community.dcinside}")
  private String dcinsideLink;

  @Value("${community.ruliweb}")
  private String ruliwebLink;

  @Value("${community.nate}")
  private String nateLink;

  @Value("${community.theqoo}")
  private String theqooLink;

  private final UserJpaRepository userJpaRepository;
  private final FeedJpaRepository feedJpaRepository;
  private final MessageService messageService;

  private static final List<String> USER_AGENTS = Arrays.asList(
      "Lynx/2.9.0dev.5 libwww-FM/2.14",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
      "Lynx/2.8.8dev.3 libwww-FM/2.14 SSL-MM/1.4.1",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36",
      "Lynx/2.8.7rel.2 libwww-FM/2.14 SSL-MM/1.4.1 OpenSSL/1.0.0a",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0"
  );

  @PostConstruct
  public void init() {
    // 애플리케이션 시작 시 즉시 실행
    crawler();
  }

  @Scheduled(cron = "0 0 9-21/6 * * *", zone = "Asia/Seoul") // 오전 9시부터 오후 9시까지 6시간 간격으로 실행
  public void crawler() {
    ZoneId koreaZoneId = ZoneId.of("Asia/Seoul"); // 대한민국 시간대
    ZonedDateTime dateTime = ZonedDateTime.now(koreaZoneId);
    System.out.println("Crawler executed at: " + dateTime.toString());

    // 각 사이트에 대해서 웹크롤링 실시
    try {
      System.out.println("--- dcinside ---");
      crawlWebsite_dcinside();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      System.out.println("--- ruliweb ---");
      crawlWebsite_ruliweb();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      System.out.println("--- nate ---");
      crawlWebsite_nate();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      System.out.println("--- theqoo ---");
      crawlWebsite_theqoo();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // 서비스 설정을 완료한 유저들에게 메세지 전송
    List<User> users = userJpaRepository.findAllByStatusTrue();
    if (!users.isEmpty()) {
      for (User user : users) {
        System.out.println(user.getEmail());
        messageService.sendMessage(user.getEmail());
      }
    }
  }

  @Transactional
  public void crawlWebsite_dcinside() throws IOException {
    tryWithUserAgents(dcinsideLink, "dcinside", "tr.ub-content.us-post.thum", "td.gall_tit.ub-word a", "https://gall.dcinside.com");
  }

  @Transactional
  public void crawlWebsite_ruliweb() throws IOException {
    tryWithUserAgents(ruliwebLink, "ruliweb", "div.title.row", "a.subject_link.deco", "");
  }

  @Transactional
  public void crawlWebsite_nate() throws IOException {
    tryWithUserAgents(nateLink, "nate", "li", "h2 a", "https://pann.nate.com");
  }

  @Transactional
  public void crawlWebsite_theqoo() throws IOException {
    tryWithUserAgents(theqooLink, "theqoo", "tr:not(.notice)", "td.title a", "https://theqoo.net");
  }

  private void tryWithUserAgents(String url, String community, String rowSelector, String titleSelector, String baseUrl) {
    for (String userAgent : USER_AGENTS) {
      if (tryWithUserAgent(url, community, rowSelector, titleSelector, baseUrl, userAgent)) {
        break;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  private boolean tryWithUserAgent(String url, String community, String rowSelector, String titleSelector, String baseUrl, String userAgent) {
    try {
      Connection connection = Jsoup.connect(url)
          .userAgent(userAgent)
          .header("Accept", "text/html")
          .header("Accept-Encoding", "gzip, deflate")
          .header("Connection", "keep-alive")
          .timeout(30000)
          .maxBodySize(0)
          .followRedirects(true);
      Document doc = connection.get();
      feedJpaRepository.deleteFeedsByCommunity(community);
      System.out.println(community + " db 초기화");

      Elements rows = doc.select(rowSelector);

      int count = 0;
      for (Element row : rows) {
        if (count >= 20) {
          break;
        }
        Element titleElement = row.selectFirst(titleSelector);
        if (titleElement != null) {
          String title = titleElement.text();
          String urlWithBase = baseUrl + titleElement.attr("href");
          Feed feed = Feed.createFeed(title, urlWithBase, community);
          feedJpaRepository.save(feed);
          count++;
        }
      }
      System.out.println("크롤링 완료 for " + community + " with User-Agent: " + userAgent);
      return true;
    } catch (HttpStatusException e) {
      System.err.println(
          "HTTP error fetching URL. Status=" + e.getStatusCode() + ", URL=" + e.getUrl());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
