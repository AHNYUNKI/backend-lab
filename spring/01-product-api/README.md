# 01. 첫 상품 API 실습

## 먼저 할 일과 완료 기준

IntelliJ에서 **이 폴더 `spring/01-product-api`를 독립 Maven 프로젝트로 열고 Java 21을 선택**한다. 아래의 `mvn test`와 `mvn spring-boot:run`으로 준비 상태부터 확인한다. 그다음 [요구사항](REQUIREMENTS.md)을 읽고 `web` 패키지에 `ProductController`를 직접 만든다.

오늘의 목표는 **JSON → Controller → Inventory → HTTP 상태와 JSON 응답**의 흐름을 직접 구현하고 설명하는 것이다.

- [ ] `POST /products`로 등록하면 `201`과 상품 JSON, `Location`을 반환한다.
- [ ] `GET /products/{id}`로 방금 등록한 상품을 조회하면 `200`과 같은 값이 나온다.
- [ ] 가격·재고 `0`은 허용하고, 잘못된 요청 `400`·없는 ID `404`·중복 ID `409`를 구분한다.
- [ ] HTTP 요청과 중단점으로 값의 이동을 확인하고, 직접 작성한 기능 테스트가 통과한다.
- [ ] 재시작하면 메모리 데이터가 사라지는 이유와 각 객체의 책임을 설명한다.

**준비된 것은 실행 골격이다.** Controller, Product, Inventory, DTO와 상품 기능 테스트는 아직 없다. 현재 `POST /products`와 `GET /products/{id}`는 모두 **404가 예상되는 미구현 경로**다. 기본 컨텍스트 테스트 1개가 통과해도 상품 기능이 완성된 것은 아니다.

## 실행과 IntelliJ 설정

이 README가 있는 폴더에서 실행한다.

```bash
mvn test
mvn spring-boot:run
```

서버가 실행된 상태에서 다른 터미널로 준비 상태를 확인한다. 현재는 **HTTP 404**가 나오면 서버에 연결된 것이고, `Connection refused`는 서버나 포트를 확인해야 한다.

```bash
curl -i -H 'Accept: application/json' http://127.0.0.1:8080/products/101
```

패키징과 JAR 실행도 가능하다. 두 실행 방법을 동시에 쓰면 같은 포트가 충돌하므로 먼저 기존 서버를 `Ctrl+C`로 종료한다.

```bash
mvn package
java -jar target/product-api-0.1.0.jar
```

- IntelliJ **Open**: `/Users/ahnyunki/app/backend-lab/spring/01-product-api`.
- `pom.xml`을 Maven 프로젝트로 불러오고 Project SDK와 Maven Runner JRE를 **Java 21**로 맞춘다.
- 현재 JDK 경로: `/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home`.
- 실행/디버깅 진입점은 `ProductApiApplication.main()`이다.
- 기본 주소는 `http://127.0.0.1:8080`이다. 8080이 사용 중이면 JAR 실행에 `--server.port=8081`을 붙이고 HTTP 파일의 `baseUrl`도 바꾼다.
- 첫 빌드에는 Maven Central에서 의존성을 내려받으므로 인터넷 연결이 필요하다. Maven Wrapper 없이 설치된 `mvn`을 사용한다.

## 오늘의 순서: 기본 4시간 + 선택 1시간

기본 일정은 **학습 220분 + 휴식 20분 = 240분**이다. 아래 시간은 시작 시각부터의 경과 시간이다.

| 경과 시간 | 분 | 할 일과 남길 결과 |
| --- | ---: | --- |
| 00:00–00:35 | 35 | 이론: HTTP 메서드·경로·JSON·상태 코드를 읽고 `@RestController`, `@RequestBody`, `@PathVariable`, 생성자 주입의 역할을 한 문장씩 적는다. |
| 00:35–00:55 | 20 | 준비: 테스트와 서버를 실행하고 미구현 경로의 404를 확인한다. Application과 패키지 설명을 읽는다. |
| 00:55–01:05 | 10 | 휴식. |
| 01:05–02:15 | 70 | 직접 코딩: Product/Inventory를 검토해 옮기고 Bean으로 연결한다. 등록 → 단건 조회 → 입력 검증과 상태 코드 순으로 구현한다. |
| 02:15–02:25 | 10 | 휴식. |
| 02:25–03:05 | 40 | 요청 실행·디버깅: `products.http` 또는 curl로 등록/조회를 실행한다. Controller와 Inventory에 중단점을 걸어 ID와 필드 값을 따라간다. |
| 03:05–03:45 | 40 | 테스트: 등록 후 조회, 0 허용, 중복·없는 ID·음수 및 누락 입력을 자동 검증한다. 각 테스트의 데이터가 섞이지 않게 한다. |
| 03:45–04:00 | 15 | 정리: 완료 체크를 확인하고 실패했던 요청, DTO 선택 이유, 이해한 요청 흐름을 짧게 기록한다. |

선택 시간은 **휴식 10분 + 테스트 보강 35분 + 회고 15분 = 60분**이다. 합계는 휴식 포함 5시간이다. 필수 기능이 끝났다면 같은 ID를 재등록한 뒤 원래 값이 보존되는지, 테스트 실행 순서를 바꿔도 통과하는지 보강한다. 오늘은 목록 조회나 재고 변경 API까지 늘릴 필요가 없다.

## 직접 작성할 자리

```text
src/main/java/dev/backendlab/productapi/
├── ProductApiApplication.java       # 준비됨: Spring Boot 진입점
├── web/package-info.java           # TODO: ProductController, 요청 객체, 필요시 응답 DTO
└── product/package-info.java       # TODO: Product, Inventory, Bean 등록 방식 선택
src/main/resources/application.properties
src/test/java/dev/backendlab/productapi/ProductApiApplicationTests.java
REQUIREMENTS.md                     # 상태 코드·필드·완료 기준
products.http                       # 구현 후 순서대로 실행할 요청 예제
```

Application의 하위 패키지에 클래스를 두면 컴포넌트 탐색 범위를 이해하기 쉽다. Inventory를 Spring이 관리할 Bean으로 등록하고 Controller 생성자에서 받는다. `@Component`와 설정 클래스의 `@Bean` 중 한 방식을 선택해 이유를 설명해 본다. Service/Repository 인터페이스나 별도 계층은 오늘의 필수 조건이 아니다.

## 기존 Java 실습에서 가져올 것

원본인 `../../java/01-product-inventory`는 수정하지 않는다. 기존 코드를 읽고 아래 두 클래스에서 필요한 부분만 **이 프로젝트에 직접 작성하거나 복사한 뒤 package/import를 수정**한다. 원본 프로젝트를 Maven 의존성으로 연결하지 않는다.

| 기존 코드 | 오늘 연결할 책임 | 확인할 점 |
| --- | --- | --- |
| [Product](../../java/01-product-inventory/src/main/java/dev/backendlab/inventory/shop/Product.java) | 상품 상태와 가격·재고 규칙 | 기존 구현도 가격·재고 0을 허용하고 음수를 거절한다. 생성자가 private이고 팩터리를 사용하므로 HTTP 요청에서 바로 생성된다고 가정하지 않는다. |
| [Inventory](../../java/01-product-inventory/src/main/java/dev/backendlab/inventory/Inventory.java) | 등록·중복 확인·ID 조회·메모리 보관 | `createProduct`, `existsById`, `findByProductId`를 읽는다. 단건 조회는 없으면 null을 반환하므로 HTTP 404 연결을 결정해야 한다. |

`Main`, `InventoryConsole`, Scanner와 콘솔 출력 클래스는 HTTP 프로젝트에 옮기지 않는다. 저장소는 요청마다 새로 만들지 않아야 등록 후 다음 요청에서 조회할 수 있다.

## DTO와 검증을 생각할 기준

콘솔 실습에서 DTO를 생략해 복잡도를 줄였던 판단은 그 당시 범위에 맞춘 선택이었다. 이번에는 외부 JSON을 Java 값으로 바꾸고 필수 필드를 검증하는 **HTTP 요청 경계**가 생긴다. 작은 요청 객체(예: `CreateProductRequest`)를 두면 이 역할을 상품 객체의 생성 규칙과 구분하기 쉽다. 응답 DTO는 외부에 보여 줄 필드를 고정할 필요와 추가되는 코드를 비교해 선택한다. DTO를 도입하는 행위 자체가 목표는 아니다.

Validation 스타터는 요청 검증을 연습할 수 있도록 준비했다. `jakarta.validation`의 `@Valid`, `@NotNull`, `@NotBlank`, `@PositiveOrZero`가 각각 무엇을 확인하는지 찾아본다. 누락과 0을 구분하려면 요청 객체의 `Long`/`Integer` 같은 참조형과 필수 값 검증의 관계도 확인한다. 도메인에서 가격·재고 규칙을 지키는 책임은 계속 필요하다.

## 요청 실행과 테스트

[products.http](products.http)는 HTTP Client를 지원하는 IDE에서 실행할 수 있다. 지원하지 않는 환경에서는 터미널로 같은 요청을 보낸다. **아래 201/200은 직접 구현을 마친 뒤의 목표**다.

```bash
curl -i -X POST http://127.0.0.1:8080/products \
  -H 'Content-Type: application/json' \
  -d '{"id":101,"name":"노트","price":1000,"stock":10}'
curl -i -H 'Accept: application/json' http://127.0.0.1:8080/products/101
```

Controller 진입 시 요청 객체, Inventory 등록 직전의 ID, 조회 후 반환되는 객체를 Debug로 확인한다. 잘못된 JSON은 Controller 메서드에 들어오기 전에 거절될 수 있으므로 HTTP 응답도 함께 본다.

준비된 `contextLoads()`는 설정 로딩만 확인한다. 직접 추가할 기능 검증은 [요구사항의 검증 순서](REQUIREMENTS.md#직접-확인할-순서)를 따른다. Inventory 규칙은 일반 JUnit 테스트로, HTTP 계약은 Spring Boot의 MockMvc 지원 등으로 확인할 수 있다. 컨텍스트가 재사용되면 메모리 데이터도 남을 수 있으므로 각 테스트가 독립적인 ID/저장소 상태를 갖게 한다. 처음부터 Mockito나 전역 예외 처리 구조를 추가할 필요는 없다.

## 범위와 한계

- 저장은 메모리에서만 한다. 서버 재시작 시 모든 상품이 사라지는 것이 오늘의 동작이다.
- 요청은 한 번에 하나씩 보내며 정상 흐름을 익힌다. Spring 서버는 여러 요청을 동시에 받을 수 있지만 기존 `ArrayList`와 중복 확인 후 추가 방식은 동시성 안전성을 보장하지 않는다. 운영 수준의 동시성 처리는 후속 과제다.
- HTTP 400/404/409는 Controller의 명시적인 분기나 `ResponseStatusException` 등 작은 방식으로 연결해도 된다. `@RestControllerAdvice`, 공통 오류 계층, 예외 메시지 문자열에 따른 분기는 필수가 아니다.
- 목록 조회·재고 변경 API, DB/JPA, 인증·보안, Docker, Lombok, 배포는 후속 학습으로 남긴다.

## 선택한 버전과 공식 자료

2026-10-07 확인 기준, 공식 [Spring Initializr 메타데이터](https://start.spring.io/metadata/client)의 안정 기본값인 **Spring Boot 4.1.1**을 사용한다. [공식 시스템 요구사항](https://docs.spring.io/spring-boot/system-requirements.html)은 Java 17–26과 Maven 3.6.3 이상을 지원하므로 현재 Java 21·Maven 3.9.16과 호환된다.

Initializr의 Java 21 / Maven / Spring Web / Validation 선택을 바탕으로 `spring-boot-starter-webmvc`, `spring-boot-starter-validation`, 테스트용 `spring-boot-starter-webmvc-test`만 선언했다. 별도 validation-test 스타터는 현재 실습에 필요하지 않아 제외했다. 하위 라이브러리와 JUnit Jupiter 버전은 Spring Boot가 관리한다. 프리뷰·스냅샷 Spring Boot 의존성이나 별도 저장소는 사용하지 않는다.

- [공식 REST 입문](https://spring.io/guides/gs/rest-service/): 요청과 Controller, JSON 응답을 읽는다.
- [Spring MVC 요청 검증](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-validation.html): `@Valid`와 요청 객체 검증을 확인한다.
- [Spring Boot 테스트](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html): 현재 Boot 4.1 문서의 테스트 설정과 패키지를 참고한다.
