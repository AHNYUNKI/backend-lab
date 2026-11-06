# 저장소 작업 안내

모든 설명, PR 본문 및 리뷰는 한국어로 작성하고 예의 바르고 명확한 존댓말을 사용합니다.

## 프로젝트와 학습 범위

- 각 실습은 독립 프로젝트입니다. 사용자 요청 없이 기존 실습 코드를 수정하거나 멀티모듈로 연결하지 않습니다.
- Java 21을 사용합니다. Maven 실습은 각 `pom.xml`, GoldGuard는 해당 폴더의 Gradle Wrapper를 사용합니다.
- 학습자가 구현할 도메인, API, 보안 해결책은 사용자가 구현을 요청한 경우에만 작성합니다.
- 기존 작업과 스테이징 상태를 보존하고, 실제 비밀번호가 든 `.env`를 커밋하지 않습니다.

## Git Flow

- `CONTRIBUTING.md`를 따릅니다. 새 기능은 최신 `develop`에서 `feature/*` 브랜치로 시작합니다.
- `main`과 `develop`에 직접 커밋을 푸시하지 않습니다. PR을 통해 반영합니다.
- 커밋·푸시·PR 생성은 사용자가 요청한 범위에서 진행하고, 사용자의 명시적인 요청 없이 PR을 머지하지 않습니다.

## 검증 명령

저장소 루트에서 변경한 프로젝트에 해당하는 명령을 실행합니다.

```bash
mvn --batch-mode --no-transfer-progress -f java/01-product-inventory/pom.xml verify
mvn --batch-mode --no-transfer-progress -f spring/01-product-api/pom.xml verify
./spring/02-goldguard/gradlew -p spring/02-goldguard --no-daemon --console=plain build
```

## Code Review Rules

- 실습별 빌드와 데이터가 독립적인지 확인합니다. 다른 실습의 데이터·실행 환경에 의존하게 된 변경은 재현 가능한 영향을 설명합니다.
- GoldGuard의 `test` 프로필은 메모리 H2를 사용해야 합니다. 테스트가 실제 MySQL이나 개인 비밀번호를 요구하도록 바뀌면 지적합니다.
- 미구현 상태로 명시한 도메인·API는 그 자체로 결함이 아닙니다. 현재 PR에서 새로 생긴 실제 오류를 구체적인 조건과 함께 지적하며, 학습 과제의 정답이나 구현을 선제적으로 작성하지 않습니다.
