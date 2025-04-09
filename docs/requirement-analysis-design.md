# 📚 요구사항 분석 및 설계


## 📊 ERD
![ERD](img/ERD.png)
<br/>

## 〽️ Flowchart
![Flowchart](img/flowchart.png)
<br/>

## 🎼 시퀀스 다이어그램
<details>
<summary><strong>1. 대기열 토큰 발급 API</strong></summary>

```mermaid
sequenceDiagram
    autonumber

    actor 사용자
    participant 대기열

    사용자->>대기열: 토큰 발급 요청 (사용자 ID)
    
    activate 대기열
    대기열->>대기열: 기존 대기열 토큰 확인
    alt 토큰 없음
        대기열->>대기열: 새 토큰 생성 및 대기열 등록
    else 토큰 있음
        대기열->>대기열: 기존 토큰 반환
    end
    
    대기열-->>사용자: 토큰 및 대기열 정보 응답 (위치, 만료시간 등)
    deactivate 대기열
```
</details>
<br/>

<details>
<summary><strong>2. 예약가능날짜 API</strong></summary>

```mermaid
sequenceDiagram
    autonumber

    actor 사용자
    participant 대기열
    participant 예약가능날짜

    사용자->>대기열: 토큰 인증 요청
    activate 대기열
    alt 유효하지 않은 토큰
        대기열-->>사용자: 400 에러 반환
    deactivate 대기열

    else
        사용자->>예약가능날짜: 콘서트 ID로 날짜 조회 요청
        activate 예약가능날짜
        예약가능날짜->>예약가능날짜: 콘서트 회차 조회
        예약가능날짜-->>사용자: 예약 가능한 날짜 리스트 반환
        deactivate 예약가능날짜
    end
```
</details>
<br/>

<details>
<summary><strong>3. 예약 가능 좌석 조회 API</strong></summary>

```mermaid
sequenceDiagram
autonumber

    actor 사용자
    participant 대기열
    participant 예약가능좌석

    사용자->>대기열: 토큰 인증 요청
    activate 대기열
    alt 유효하지 않은 토큰
        대기열-->>사용자: 400 에러 반환
        deactivate 대기열
    else
        사용자->>예약가능좌석: 회차 ID로 좌석 조회 요청
        activate 예약가능좌석
        예약가능좌석->>예약가능좌석: 좌석 정보 및 상태 조회 (1~50번)
        예약가능좌석-->>사용자: 예약 가능한 좌석 번호 목록 반환
        deactivate 예약가능좌석
    end

```
</details>
<br/>

<details>
<summary><strong>4. 좌석 예약 API</strong></summary>

```mermaid
sequenceDiagram
    autonumber

    actor 사용자
    participant 대기열
    participant 좌석
    participant 예약

    사용자->>대기열: 토큰 인증 요청
    alt 유효하지 않은 토큰
        대기열-->>사용자: 400 에러 반환
    else
        사용자->>예약: 좌석 예약 요청 (날짜, 좌석 번호)
        예약->>좌석: 좌석 점유 상태 확인
        alt 이미 임시 배정됨
            예약-->>사용자: 예약 실패 (이미 점유됨)
        else 점유 가능
            예약->>좌석: 좌석 임시 점유 (락 생성, 5분 만료)
            예약->>예약: 예약 엔티티 생성 (status=임시)
            예약-->>사용자: 임시 예약 완료, 유효시간 응답
        end
    end

```
</details>
<br/>

<details>
<summary><strong>4. 잔액 조회 및 충전 API</strong></summary>

```mermaid
sequenceDiagram
    autonumber

    actor 사용자
    participant 잔액

    %% 잔액 조회 %%
    opt 잔액조회
        사용자->>잔액: 잔액 조회 요청 (사용자 ID)
        activate 잔액
        잔액->>잔액: 현재 잔액 조회
        잔액-->>사용자: 잔액 조회 응답
        deactivate 잔액
    end

    %% 잔액 충전 %%
    rect rgb(0, 128, 0)
        사용자->>잔액: 잔액 충전 요청 (충전 금액)
        activate 잔액
        잔액->>잔액: 현재 잔액 + 충전 금액
        잔액->>잔액: 잔액 변경 내역 기록
        잔액-->>사용자: 충전 완료 응답
    end
```
</details>
<br/>

<details>
<summary><strong>5. 결제 API</strong></summary>

```mermaid
sequenceDiagram
    autonumber

    actor 사용자
    participant 결제
    participant 예약
    participant 잔액
    participant 좌석
    participant 대기열

    사용자->>결제: 결제 요청
    activate 결제

    결제->>예약: 예약 유효성 검증
    activate 예약
    deactivate 예약

    alt 예약이 만료됨
        결제-->>사용자: 결제 실패 (예약 만료)
    else 예약 유효함
        결제->>잔액: 잔액 차감 가능 여부 확인
        activate 잔액
        deactivate 잔액

        alt 잔액 부족
            opt 사용자 충전 시도
                사용자-->>잔액: 잔액 충전 요청
                activate 잔액
                잔액-->>결제: 충전 결과
                deactivate 잔액
            end
            결제-->>사용자: 결제 실패 (잔액 부족)
        else 잔액 충분
            결제->>잔액: 잔액 차감
            activate 잔액
            deactivate 잔액

            결제->>좌석: 소유권 이전
            activate 좌석
            deactivate 좌석

            결제->>예약: 예약 상태 확정
            activate 예약
            deactivate 예약

            결제->>대기열: 토큰 만료 처리
            activate 대기열
            deactivate 대기열

            결제-->>사용자: 결제 성공 응답
        end
    end

    deactivate 결제

```
</details>