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



