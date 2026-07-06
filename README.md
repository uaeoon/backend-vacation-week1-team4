# backend-vacation-week1-team4
백엔드 방학 1주차 과제 4조

##공통 세팅 파일 수정 시에 선수정 X, 먼저 말하기!!!!

### 폴더 기본 구조

```text
src/main/java/mutsa.team4
├── 기능
    ├── code(errorCode)
    ├── controller
    └── domain
    └── dto
    └── serivce
    └── repository
```

### 기능별 에러코드 양식
(기능)(에러타입별 번호)_(에러번호 형식)
Ex) CART400_1
대문자 통일

### Branch Convention

- type/이슈번호-작업내용


    | Type | 의미 | 예시 |
    | --- | --- | --- |
    | feature | 새로운 기능 개발 | `feature/signup` |
    | fix | 버그 수정 | `fix/login-error` |
    | docs | 문서 수정 | `docs/readme` |
    | refactor | 코드 리팩토링 | `refactor/member-service` |
    | chore | 설정, 빌드, 기타 작업 | `chore/github-template` |
    | test | 테스트 코드 작성 | `test/member-service` |

### Commit Convention

- [Type] 작업 내용


    | Type | 의미 |
    | --- | --- |
    | Feat | 새로운 기능 추가 |
    | Fix | 버그 수정 |
    | Docs | 문서 수정 |
    | Style | 코드 포맷팅, 세미콜론 수정 등 기능 변화 없는 수정 |
    | Refactor | 코드 리팩토링 |
    | Test | 테스트 코드 추가 또는 수정 |
    | Chore | 빌드 설정, 패키지 설정, 기타 작업 |
    | Rename | 파일명 또는 폴더명 수정 |
    | Remove | 파일 삭제 |
