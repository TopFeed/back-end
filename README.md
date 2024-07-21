# 🔜 TopFeed
**국내 유명 커뮤니티의 인기 글을 한번에 볼 수 있는 웹사이트입니다.<br>
이메일을 등록하시면 이메일로 인기 게시글들을 받아보실 수 있습니다.<br>**
<br>

# 👨🏻‍💻 Contributors
|  <div align = center>조현태 </div> |
|:----------|
|<div align = center> <img src = "https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fnoticon-static.tammolo.com%2Fdgggcrkxq%2Fimage%2Fupload%2Fv1567128822%2Fnoticon%2Fosiivsvhnu4nt8doquo0.png&blockId=865f4b2a-5198-49e8-a173-0f893a4fed45&width=256" width = "17" height = "17"/> [hyuntae99](https://github.com/hyuntae99)| </div> 
<br>

## 📖 Development Tech
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white">
<br>

# 💼 Server Architecture
<img src="https://velog.velcdn.com/images/jmjmjmz732002/post/a6c7a7be-ff27-4723-bfe2-d458ed641fab/image.png">
<br>

# 🗂️ Directory
```
├── java
│   └── com
│       └── hyun
│           └── topfeed
│               ├── TopfeedApplication
│               ├── controller
│               │   ├── FeedController
│               │   ├── MainController
│               │   └── UserController
│               ├── dto
│               │   ├── ApiStandardResponse
│               │   └── ErrorResponse
│               ├── entity
│               │   │── BaseEntity
│               │   │── Feed
│               │   └── User
│               ├── exception
│               │   │── ApiKeyNotValidException
│               │   │── ApiNotFoundException
│               │   │── ErrorStatus
│               │   │── GlobalExceptionHandler
│               │   │── UnauthorizedException
│               │   └── UserNorFoundException
│               ├── repository
│               │   │── FeedJpaRepository
│               │   └── UserJpaRepositoty
│               └── service
│                   ├── FeedService
│                   ├── MessageService
│                   ├── UserService
│                   └── WebCrawlerService
└── resources
    ├── config 
    │   └── application-local.yml
    ├── templates
    │   ├── index.html
    │   ├── register.html
    │   └── withdraw.html
    └── application.yml
```

# 📝 Service

**동영상을 GIF로 변환하는 과정에서 부득이하게 화질이 낮아진 점 먼저 양해 구합니다.**

## 1. 메인 페이지

국내 인기 커뮤니티의 핫게시글을 한번에 볼 수 있게 만들었습니다. <br>
오전 9시부터 오후 9시까지 3시간 간격으로 업데이트되도록 만들었습니다. <br>
에펨코리아 커뮤니티의 경우, 웹크롤링 제한으로 인해서 제외하게 되었습니다.

<img src= "https://velog.velcdn.com/images/hyuntae99/post/458c985b-5095-41b0-9bc4-792c5fe96859/image.png"><br><br>

토글 형식을 채용하였으며 총 20개의 게시글의 제목과 링크를 한눈에 볼 수 있게 만들었습니다.<br>
만약 크롤링이 실패했을 경우 이전 크롤링 결과를 제공하도록 구성하였습니다. <br>
또한 실시간으로 데이터가 변하는 것이 아니기 때문에 <br>
한번 받아온 데이터를 객체로 저장하여 사용하므로 쓸데없는 서버 통신을 줄였습니다. <br>

<img src="https://velog.velcdn.com/images/hyuntae99/post/b94773b9-1e79-42a6-995d-75798ab19a4f/image.gif">
<br><br>

## 2. 이메일 알림 서비스 등록 (이메일 인증)
오전, 오후 9시에 각 커뮤니티의 핫 게시글을 간추려서 알려주는 서비스를 만들었습니다.

<img src="https://velog.velcdn.com/images/hyuntae99/post/00838a85-db11-4ff9-90b8-9e0fcf6510fa/image.gif">

아래와 같이 이메일로 인증번호를 전송합니다.<br>
<img src="https://velog.velcdn.com/images/hyuntae99/post/52b5762e-78d9-44e1-8ebf-2baad1d55985/image.png" height=150 width=350>

아래 처럼 핫게시글을 이메일로 보냅니다.
<img src="https://velog.velcdn.com/images/hyuntae99/post/1e47be9b-7e5e-44cb-a439-7f731c5515fb/image.png" height=350 width=300>
<br><br>

## 3. 이메일 알림 서비스 해지
<img src="https://velog.velcdn.com/images/hyuntae99/post/ca8582b6-51f0-4b9c-8ff2-1f7d9ae5cb90/image.gif">

# 🎞️ 느낀점
**1. 저는 커뮤니티를 처음 들어가보았는데 생각보다 유저 수도 많았고 최근 이슈에 대한 논쟁이 많았습니다. <br>
물론 부적절한 내용들도 많겠지만 정보를 찾거나 최근 이슈를 쉽게 알 수 있을 것 같아 만들게 되었습니다. <br>**

**2. 여러 사이트에 대해서 웹크롤링을 구현하면서 html 구조를 좀 더 잘 알게 되었고 <br>
User-Agent, 서버 타임아웃 등 여러 트러블 이슈를 해결하며 웹크롤링에 큰 자심감을 얻게 되었습니다. <br>
또한 스케쥴링을 이용하기 위해서 Cron 정규식에 대해서도 좀 더 공부하게된 계기가 되었습니다. <br>**
