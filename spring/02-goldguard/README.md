# GoldGuard — AI vs Backend Developer

일일 보상과 데이터베이스 설계를 직접 구현하며 배우는 독립 Spring Boot 실습입니다.
현재는 실행 환경과 애플리케이션 진입점, 시작 확인용 테스트만 준비되어 있습니다.
User, Wallet, DailyRewardClaim, 보상 API 및 보안 기능은 직접 구현할 과제입니다.

## 개발 환경

- Java 21, Spring Boot 3.5.16, Gradle Wrapper 8.14.3
- Spring Web, Spring Data JPA, Validation, Spring Security, Lombok, MySQL Connector/J
- Spring Boot Test와 JUnit Jupiter 5.12.2, 테스트 전용 H2
- 로컬 데이터베이스: Docker Compose의 MySQL 8.4

같은 저장소의 `spring/01-product-api`는 Java 21, Spring Boot 4.1.1과 Maven을 사용합니다.
GoldGuard도 Java 21, `dev.backendlab` 패키지, 실습별 독립 프로젝트 구조를 따릅니다.
이 프로젝트는 요청한 Gradle과 JUnit 5를 사용하며, JUnit 5와 호환되는 Boot 3.5 계열을 선택했습니다.
Boot 4.x가 사용하는 Spring Framework 7의 테스트 확장은 JUnit 6 이상이 필요합니다.
라이브러리 버전은 Spring Boot의 의존성 관리에 맡깁니다.

버전 기준: [Boot 3.5 시스템 요구사항](https://docs.spring.io/spring-boot/3.5/system-requirements.html),
[Spring 테스트의 JUnit 요구사항](https://docs.spring.io/spring-framework/reference/testing/testcontext-framework/support-classes.html).

## 시작하기

모든 명령은 이 프로젝트 폴더에서 실행합니다.

```bash
cd /Users/ahnyunki/app/backend-lab/spring/02-goldguard
./gradlew test
```

첫 실행에는 Gradle과 의존성을 내려받을 인터넷 연결이 필요합니다.
시스템에 설치된 Gradle 대신 `./gradlew`를 사용하세요.
IntelliJ에서는 이 폴더를 Gradle 프로젝트로 열고 Project SDK와 Gradle JVM을 Java 21로 맞춥니다.

테스트는 `test` 프로필의 메모리 H2로 Spring 컨텍스트, JPA와 DB 연결을 확인합니다.
Docker나 로컬 비밀번호가 필요하지 않으며, MySQL 고유 동작이나 보상 기능을 검증하는 테스트는 아닙니다.

## 로컬 MySQL과 애플리케이션 실행

1. Docker Desktop을 실행합니다.
2. `.env.example`을 `.env`로 복사합니다.
3. `.env`의 `DB_PASSWORD`와 `MYSQL_ROOT_PASSWORD`에 서로 다른 임의의 비밀번호를 입력합니다.
   `openssl rand -hex 24`를 각각 실행해 값을 만들 수 있습니다.
4. MySQL이 준비된 뒤 애플리케이션을 실행합니다.

```bash
cp .env.example .env
# .env의 비밀번호 두 개를 입력한 뒤 실행하세요.
docker compose up -d --wait
./gradlew bootRun
```

`.env`는 Compose와 Spring Boot가 읽으므로 셸에서 따로 불러올 필요가 없습니다.
Spring Boot는 실행 폴더 기준으로 `.env`를 찾습니다. IDE의 작업 디렉터리도 이 프로젝트 폴더로 지정하세요.
기본 프로필은 `local`이며, 비밀번호 기본값은 제공하지 않습니다.
`application-local.yml`은 로컬 연결 예제로, DB 접속 정보는 환경변수 또는 `.env`로 공급합니다.
셸에 같은 이름의 환경변수가 있으면 `.env`보다 우선하므로 두 프로그램에 같은 값을 사용하세요.

| 대상 | 기본값 |
| --- | --- |
| 애플리케이션 | `http://localhost:8082` |
| MySQL | `127.0.0.1:13307` |
| 데이터베이스 / 일반 계정 | `goldguard` / `goldguard` |
| Compose 프로젝트 | `backend-lab-goldguard` |

포트가 사용 중이면 `.env`의 `SERVER_PORT` 또는 `DB_PORT`를 바꾸세요.
MySQL 포트는 로컬 루프백에만 열리며, 네트워크와 데이터 볼륨은 Compose 프로젝트별로 구분됩니다.
애플리케이션은 root 계정을 사용하지 않습니다. JDBC URL의 TLS 비활성화 옵션은 로컬 실습용입니다.
실제 비밀번호가 들어간 `.env`는 `.gitignore`에서 제외하며, 예제 파일에는 값을 넣지 않습니다.

엔드포인트는 아직 구현하지 않았습니다. Spring Security의 기본 자동 설정이 적용되어
인증하지 않은 요청에는 **401** 또는 로그인 페이지로의 리다이렉트가 반환될 수 있습니다.
인증 방식과 보상 API의 보안 정책은 이후 직접 구현할 과제입니다.
JPA의 `ddl-auto`는 `none`입니다. 엔티티를 작성해도 테이블이 자동으로 생성되지 않으므로
스키마 관리 방식을 정하는 작업도 이후 실습에 포함됩니다.

## 빌드와 종료

```bash
./gradlew clean build
java -jar build/libs/goldguard-0.0.1-SNAPSHOT.jar
```

`bootRun`이나 JAR 실행은 `Ctrl+C`로 종료하고, MySQL은 아래 명령으로 종료합니다.

```bash
docker compose down
```

일반 종료 시 데이터 볼륨은 유지됩니다. `.env`의 초기 계정 정보는 빈 볼륨에서 처음 시작할 때 적용됩니다.
실습 데이터를 모두 지우고 초기화하려는 경우에만 `docker compose down --volumes`를 사용하세요.

## 다음 구현 과제

1. User, Wallet, DailyRewardClaim의 책임과 관계를 글로 정리하세요.
2. DailyRewardClaim에 단일 PK와 복합 PK 중 무엇을 사용할지 선택하고 근거를 적으세요.
3. 설계를 바탕으로 엔티티와 스키마를 작성하세요.
4. 보상 요청의 정상 흐름과 실패 조건을 정리하고 테스트와 API를 직접 추가하세요.

PK, 제약조건, 동시성 및 보안 해결책은 실습에서 직접 판단할 수 있도록 미리 구현하지 않았습니다.
