## 아파트 주민관리 프로그램

이 프로그램은 아파트 관리자 및 일반 사용자가 아파트 주민 정보를 관리하고 업무를 효율적으로 처리하기 위한 솔루션입니다.

## 설치 및 실행

1. 환경 설정:

이 프로그램은 Java와 MySQL이 필요합니다.<br>
로컬 환경에 Java와 MySQL이 설치되어 있어야 합니다. <br>
MySQL 데이터베이스를 설치하고, resident과 같은 데이터베이스를 생성하세요. <br>

2. 데이터베이스 연동:

database.properties 파일을 열어 MySQL 연결 정보를 수정하세요.<br>

예시:

```java
db.url=jdbc:mysql://localhost:3306/resident 
db.username=root
db.password=password
```

## 사용 방법

프로그램을 실행하면 다음과 같은 기능을 사용할 수 있습니다: <br>
주민 정보 조회 <br>
주민 추가/수정/삭제 <br>
관리비 조회 <br>
관리비 입력/수정/삭제 <br>
알림 보내기 <br>
시설 & 정비 요청 <br>

## 파일 구조

src/: 소스 코드 폴더 <br>
lib/: 의존성 라이브러리 폴더 <br>
database.properties: 데이터베이스 연결 정보 설정 파일 <br>
db.sql: 데이터베이스 테이블 스키마를 정의하는 파일입니다. <br>

## 연락처

문제가 발생하거나 추가적인 정보가 필요한 경우 이메일을 보내주세요: <br>
lwlstjdl@naver.com

