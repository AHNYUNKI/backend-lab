# Git Flow 작업 안내

이 저장소는 **작업 브랜치 → 커밋·푸시 → PR → CI와 AI 리뷰 → 직접 머지** 순서로 진행합니다.
별도 git-flow 확장을 설치하지 않아도 아래의 기본 Git 명령으로 작업할 수 있습니다.

## 브랜치 역할

| 브랜치 | 시작 위치 | PR 대상 | 역할 |
| --- | --- | --- | --- |
| `main` | 기존 안정 버전 | 직접 작업하지 않음 | 안정 버전과 릴리스 이력 |
| `develop` | `main` | 릴리스 준비 시 `release/*` 생성 | 개발 결과 통합, 기본 브랜치 |
| `feature/<작업명>` | `develop` | `develop` | 기능, 테스트, 문서와 환경 설정 |
| `release/<버전>` | `develop` | `main` | 릴리스 준비와 최종 수정 |
| `hotfix/<작업명>` | `main` | `main` | 안정 버전 긴급 수정 |

릴리스나 긴급 수정이 `main`에 반영되면 `main` → `develop` PR로 동기화합니다.
릴리스 준비 중 수정 사항을 먼저 공유해야 하면 `release/*` → `develop` PR도 허용합니다.
`feature/*` → `main` PR은 CI에서 차단합니다.

## 기능 작업 시작

저장소 루트에서 실행합니다. 작업명은 실제 작업에 맞게 바꾸세요.

```bash
git switch develop
git pull --ff-only origin develop
git switch -c feature/daily-reward-schema
```

한 브랜치에서는 하나의 목적을 다루고, 과제의 설계 근거를 PR에 적습니다.
커밋 메시지는 `feat:`, `fix:`, `test:`, `docs:`, `refactor:`, `chore:`, `ci:`처럼 변경 목적을 붙입니다.

```bash
git status
git diff
git add spring/02-goldguard/src
git diff --cached
git commit -m "feat: 일일 보상 데이터 모델 작성"
git push -u origin feature/daily-reward-schema
gh pr create --base develop
```

`git add`에는 실제로 변경한 경로만 지정하고, `.env`와 비밀번호를 포함하지 않습니다.
GitHub 화면에서 PR을 만들 때도 대상 브랜치가 `develop`인지 확인하세요.

## 테스트와 AI 리뷰

PR의 `verify` 검사는 Java 21에서 아래 세 프로젝트를 빌드하고 테스트합니다.
문서 변경 PR도 동일한 검사를 실행하므로 필수 검사가 대기 상태로 남지 않습니다.

```bash
mvn --batch-mode --no-transfer-progress -f java/01-product-inventory/pom.xml verify
mvn --batch-mode --no-transfer-progress -f spring/01-product-api/pom.xml verify
./spring/02-goldguard/gradlew -p spring/02-goldguard --no-daemon --console=plain build
```

GoldGuard 테스트는 H2를 사용하므로 GitHub Actions에 MySQL 비밀번호나 별도 DB를 등록할 필요가 없습니다.
CI에 실패하면 같은 브랜치에서 수정하고 다시 커밋·푸시합니다. 기존 PR에서 검사가 다시 실행됩니다.

이 저장소의 Codex 자동 리뷰는 **켜짐 / 모든 PR / 푸시할 때마다**로 설정되어 있습니다.
자동 리뷰 결과를 확인하고, 필요하면 PR 댓글에 아래 문구로 다시 AI 리뷰를 요청합니다.

```text
@codex review
```

자동 리뷰 대상과 실행 시점은 [Codex 코드 리뷰 설정](https://chatgpt.com/settings/code-review)에서
`AHNYUNKI/backend-lab`을 선택해 확인할 수 있습니다.
리뷰 기준은 루트 `AGENTS.md`에 있으며, [공식 연동 안내](https://learn.chatgpt.com/docs/third-party/github)를 따릅니다.
수정 후에는 같은 PR에서 다시 리뷰를 요청하거나 설정된 자동 리뷰 결과를 확인합니다.

## 머지 조건

- `main`과 `develop`의 변경은 PR을 거칩니다.
- `verify` 검사 통과, 대상 브랜치의 최신 변경 반영, 리뷰 대화 해결이 필요합니다.
- 혼자 작업하는 저장소이므로 다른 사람의 승인 수는 0으로 둡니다.
- AI 리뷰 완료 여부는 브랜치 보호가 별도로 강제하지 않습니다. AI 결과를 확인하고 필요한 수정을 반영한 뒤 직접 머지합니다.
- Git Flow의 브랜치 관계를 유지하려면 **Create a merge commit**을 사용합니다. 릴리스와 동기화 PR에는 Squash를 사용하지 않습니다.

자동 머지는 사용하지 않습니다. 머지된 작업 브랜치는 GitHub에서 자동 삭제되며 `main`과 `develop`은 보호됩니다.
머지 후 로컬 개발 브랜치를 갱신합니다.

```bash
git switch develop
git pull --ff-only origin develop
git branch -d feature/daily-reward-schema
```

## 릴리스와 긴급 수정

릴리스는 최신 `develop`에서 준비합니다.

```bash
git switch develop
git pull --ff-only origin develop
git switch -c release/0.1.0
git push -u origin release/0.1.0
gh pr create --base main
```

CI와 AI 리뷰를 확인해 `main`에 머지한 뒤, 아래 명령으로 개발 브랜치에 되돌려 반영합니다.

```bash
gh pr create --base develop --head main
```

긴급 수정은 최신 `main`에서 `hotfix/<작업명>`을 생성하고 `main`을 대상으로 PR을 만듭니다.
머지 후에는 릴리스와 동일하게 `main` → `develop` PR을 만듭니다.
