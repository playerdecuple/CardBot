# CardBot || 카드봇

## Introduce
> 말은 쉽지. '카'드를 보여줘.

데큐플이 심심해서 만들어본 카드 대결 봇! 팀 데큐플 내에서 세XX이츠가 유행할 때 나온 아이디어로 만들고 있는 봇입니다.

### Play
플레이 방식은 다양한 방법으로 카드를 모아서 다른 사람과 싸우는 방식입니다!  
턴이 돌 때마다 카드를 하나씩 꺼내 1대1 구도를 만들어 싸우게 합니다! 그리고 3점을 적에게 준다면 패배하게 됩니다!  
플레이어가 여기서 고민해야 할 부분은 다음과 같습니다.
 * 카드의 형식 (ATK와 DEF의 관계. ATK > DEF인 경우 공격형, ATK < DEF인 경우 방어형, ATK = DEF인 경우 듀얼형)
 * 카드의 공격력과 방어력
 * 카드 레벨
 * 카드 등급 (n성)
 * 카드 고유 스킬의 효과

### Progress
 - [x] 카드 클래스
 - [x] 플레이어 클래스
 - [x] JDA를 이용하여 디스코드 봇으로 만들기
 - [x] 카드봇 DB에 플레이어 등록
 - [x] 카드봇 DB에 카드 등록
 - [x] 카드봇 DB에 플레이어의 덱 등록
 - [x] 카드봇 DB 조회 및 플레이어에게 표시
 - [x] 코어 클래스 완성
 - [x] 배틀 기능 생성
 - [ ] 매칭 기능 생성
 - [ ] 카드 추가
 - [ ] 갓챠 제작
 - [ ] 캐쉬(?) 시스템 제작 ~~자낳괴 데큐플~~
 - [ ] 모험 생성
 - [ ] Quintuple API V2를 이용하여 플을 골드로 전환하게 만드는 시스템
 - [ ] 카드 제작
 - [ ] 여러 가지 아이템 제작
 - [ ] 스테이터스를 올릴 수 있는 주문서 아이템 제작
 
# OpenCardBot
카드봇은 오픈소스입니다. (GPLv3) 개발자 한정으로 자신만의 팩을 만들어 배포할 수 있습니다. (단, GPLv3 라이선스를 적용해야 하고 원작자 표기를 해 두어여 합니다.)

## Installation

### Requirement
 * JDK 1.8
 * IntelliJ IDEA (Community, Ultimate 상관 없음)
 * Git (Recommended)
 * Discord Account
 
### Setting
1. Git bash로 클론합니다.
```git clone https://github.com/playerdecuple/CardBot.git```
2. IntelliJ에서 Open Project로 프로젝트를 엽니다.
3. 카드 정보는 `~/CardList.txt`에 있습니다.
4. 디스코드 개발자 대시보드에서 애플리케이션을 생성하고 봇을 추가한 다음 토큰을 복사하여 `~/token.txt`에 입력합니다.
5. 빌드해 줍니다.

## Editting Database
기본적으로 카드봇은 `.svc` 포맷을 가지고 데이터베이스를 저장합니다. (확장명은 `.txt`를 사용합니다.)  
다음 문서들을 참고해 주세요. **콤마 뒤에 공백을 넣지 마세요.**

1. `~/CardList.txt`
```
ID,Name,Star,ATK,DEF,Description
ex) 0,Decu,5,116,88,테스트입니다.
```

2. `~/PlayerList.txt`
```
DiscordID,Name,Level,EXP,Gold,Cash,0
ex) 0,데큐플,10,10,99999,99999,0
```

3. `~/Database/:id/deck.txt`
```
ID,Name,Star,ATK,DEF,Description
ex) 16,갓챠겜,3,54,49,이 게임은 망겜입니다
```
