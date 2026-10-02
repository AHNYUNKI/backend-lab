# backend-lab

Java부터 Spring, SQL까지 직접 구현하고 테스트하며 배우는 백엔드 실습 저장소입니다.
각 실습은 독립 프로젝트로 관리합니다. 루트에는 통합 빌드나 멀티모듈 설정이 없습니다.

## 폴더 구조

```text
backend-lab/
├── README.md
├── .gitignore
├── java/
│   └── 01-product-inventory/   # 오늘: 순수 Java 상품·재고 실습
│       ├── README.md
│       ├── pom.xml
│       └── src/
│           ├── main/java/dev/backendlab/inventory/Main.java
│           └── test/java/dev/backendlab/inventory/
│               ├── MainTest.java
│               └── ProductInventoryTest.java
├── spring/
│   └── 01-product-api/         # 다음 실습을 위한 안내
│       └── README.md
└── database/                  # SQL 실습을 위한 안내
    └── README.md
```

## 오늘 시작하기

1. IntelliJ에서 `java/01-product-inventory` 폴더를 열고 Maven 프로젝트로 불러옵니다.
2. Project SDK와 Maven 실행 JDK를 **Java 21**로 맞춥니다.
3. [실습 안내](java/01-product-inventory/README.md)를 읽고 `Main.java`를 실행합니다.
4. 상품 등록 → 목록 조회 → 재고 변경을 직접 구현하고 테스트를 하나씩 활성화합니다.

터미널에서는 저장소 루트 기준으로 실행합니다.

```bash
cd java/01-product-inventory
mvn test
mvn compile
java -cp target/classes dev.backendlab.inventory.Main
```

준비된 코드는 진입점과 테스트 골격입니다. 상품 기능은 아직 구현되어 있지 않습니다.
처음 테스트를 실행하면 시작 코드 검증 1개가 통과하고, 상품 과제 테스트 4개는 건너뜁니다.
상품 기능을 완성했다는 뜻은 아닙니다.

## 개발 환경

- Java 21, Maven 3.9 계열을 기준으로 합니다.
- 준비 시 확인한 Mac 환경: Homebrew OpenJDK 21.0.11, Maven 3.9.16.
- JUnit 5.10.5와 Maven 플러그인은 기존 로컬 캐시에 있는 버전으로 고정했습니다.
- 외부 서비스나 데이터베이스 없이 Java 실습을 실행할 수 있습니다.
- 다른 컴퓨터에서는 첫 Maven 실행에 의존성 다운로드가 필요할 수 있습니다.

## 학습 기록

실습 README에 객체별 책임, 컬렉션 선택 이유, 테스트 결과, 디버거로 확인한 내용을 짧게 남깁니다.
정상 동작을 먼저 만든 뒤 중복 ID, 없는 상품, 음수 재고 등 경계 조건을 추가합니다.
빌드 결과와 IntelliJ 개인 설정은 `.gitignore`로 제외합니다.
