package com.hyun.topfeed.service;

import com.hyun.topfeed.entity.Feed;
import com.hyun.topfeed.entity.User;
import com.hyun.topfeed.repository.FeedJpaRepository;
import com.hyun.topfeed.repository.UserJpaRepository;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
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

  @Value("${community.fmkorea}")
  private String fmkoreaLink;

  @Value("${community.nate}")
  private String nateLink;

  @Value("${community.theqoo}")
  private String theqooLink;

  private final UserJpaRepository userJpaRepository;
  private final FeedJpaRepository feedJpaRepository;
  private final MessageService messageService;

  private static final List<String> USER_AGENTS = Arrays.asList(
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:123.0) Gecko/20100101 Firefox/123.0",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36 Edg/121.0.0.0 Unique/97.7.7239.70",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
      "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Mobile Safari/537.36",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Agency/98.8.8175.80",
      "Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Mobile Safari/537.36",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:123.0) Gecko/20100101 Firefox/123.0",
      "Mozilla/5.0 (Android 8.1.0; Mobile; rv:123.0) Gecko/123.0 Firefox/123.0",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36 Edg/121.0.0.0"
  );

  private static final Random RANDOM = new Random();

  private String getRandomUserAgent() {
    return USER_AGENTS.get(RANDOM.nextInt(USER_AGENTS.size()));
  }

  @Scheduled(cron = "0 0 9-21/3 * * *", zone = "Asia/Seoul") // 오전 9시부터 오후 9시까지 3시간 간격으로 실행
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
      System.out.println("--- fmkorea ---");
      crawlWebsite_fmkorea();
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

  /**
   * 디시인사이드 크롤링
   */
  @Transactional
  public void crawlWebsite_dcinside() throws IOException {
    // Jsoup을 활용한 크롤링 설정
    try {
      Document doc = Jsoup.connect(dcinsideLink)
          .userAgent(getRandomUserAgent())
          .timeout(5000)
          .get();
      feedJpaRepository.deleteFeedsByCommunity("dcinside");

      Elements rows = doc.select("tr.ub-content.us-post.thum");

      int count = 0;
      for (Element row : rows) {
        if (count >= 20) {
          break;
        }
        Element titleElement = row.selectFirst("td.gall_tit.ub-word a");
        String title = titleElement.text();
        String url = "https://gall.dcinside.com" + titleElement.attr("href");
        Feed feed = Feed.createFeed(title, url, "dcinside");
        feedJpaRepository.save(feed);
        count++;
      }
    } catch (HttpStatusException e) {
      System.err.println(
          "HTTP error fetching URL. Status=" + e.getStatusCode() + ", URL=" + e.getUrl());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 에펨코리아 크롤링
   */
  @Transactional
  public void crawlWebsite_fmkorea() throws IOException {
    // Jsoup을 활용한 크롤링 설정
    try {
      Document doc = Jsoup.connect(fmkoreaLink)
          .userAgent(getRandomUserAgent())
          .timeout(5000)
          .get();
      feedJpaRepository.deleteFeedsByCommunity("fmkorea");

      Elements elements = doc.select("li.li_best2_pop0.li_best2_hotdeal0");

      int count = 0;
      for (Element element : elements) {
        if (count >= 20) {
          break;
        }
        Element linkElement = element.selectFirst("h3.title a.hotdeal_var8");
        String title = linkElement.text();
        String url = "https://www.fmkorea.com" + linkElement.attr("href");
        Feed feed = Feed.createFeed(title, url, "fmkorea");
        feedJpaRepository.save(feed);
        count++;
      }
    } catch (HttpStatusException e) {
      System.err.println(
          "HTTP error fetching URL. Status=" + e.getStatusCode() + ", URL=" + e.getUrl());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 네이트판 크롤링
   */
  @Transactional
  public void crawlWebsite_nate() throws IOException {
    // Jsoup을 활용한 크롤링 설정
    try {
      Document doc = Jsoup.connect(nateLink)
          .userAgent(getRandomUserAgent())
          .timeout(5000)
          .get();
      feedJpaRepository.deleteFeedsByCommunity("nate");

      Elements elements = doc.select("li");

      int count = 0;
      for (Element element : elements) {
        if (count >= 20) {
          break;
        }

        Element titleElement = element.selectFirst("h2 a");
        if (titleElement != null) {
          String title = titleElement.attr("title");
          String url = "https://pann.nate.com" + titleElement.attr("href");

          Feed feed = Feed.createFeed(title, url, "nate");
          feedJpaRepository.save(feed);
          count++;
        }
      }
    } catch (HttpStatusException e) {
      System.err.println(
          "HTTP error fetching URL. Status=" + e.getStatusCode() + ", URL=" + e.getUrl());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 더쿠 크롤링
   */
  @Transactional
  public void crawlWebsite_theqoo() throws IOException {
    // Jsoup을 활용한 크롤링 설정
    try {
      Document doc = Jsoup.connect(theqooLink)
          .userAgent(getRandomUserAgent())
          .timeout(5000)
          .get();
      feedJpaRepository.deleteFeedsByCommunity("theqoo");

      Elements elements = doc.select("tr");

      int count = 0;
      for (Element element : elements) {
        if (count >= 20) {
          break;
        }

        if (element.selectFirst("td.no") != null && element.select("strong").isEmpty()) {
          Element titleElement = element.selectFirst("td.title a");
          if (titleElement != null) {
            String title = titleElement.text();
            String url = "https://theqoo.net" + titleElement.attr("href");

            Feed feed = Feed.createFeed(title, url, "theqoo");
            feedJpaRepository.save(feed);
            count++;
          }
        }
      }
    } catch (HttpStatusException e) {
      System.err.println(
          "HTTP error fetching URL. Status=" + e.getStatusCode() + ", URL=" + e.getUrl());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}