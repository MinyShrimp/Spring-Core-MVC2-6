# 로그인 처리 1 - 쿠키, 세션

## 로그인 요구사항

### 홈 화면 - 로그인 전

![img.png](img.png)

* 회원 가입
* 로그인

### 홈 화면 - 로그인 후

![img_1.png](img_1.png)

* 본인 이름 (XX님 환영합니다.)
* 상품 관리
* 로그 아웃

### 보안 요구사항

![img_3.png](img_3.png)

* 로그인 사용자만 상품에 접근하고, 관리할 수 있음
* 로그인 하지 않은 사용자가 상품 관리에 접근하면 로그인 화면으로 이동

### 회원 가입

![img_2.png](img_2.png)

### 상품 관리

![img_4.png](img_4.png)

## 프로젝트 생성

### package 구조

- hello.login
    - domain
        - item
        - login
        - member
    - dto
        - item
    - web
        - item
        - login
        - member

### 도메인이 가장 중요하다.

> 도메인 = 화면, UI, 기술 인프라 등등의 영역은 제외한 시스템이 구현해야 하는 핵심 비즈니스 업무 영역을 말함.

향후 web을 다른 기술로 바꾸어도 도메인은 그대로 유지할 수 있어야 한다.
이렇게 하려면 web은 domain을 알고있지만 domain은 web을 모르도록 설계해야 한다.
이것을 web은 domain을 의존하지만, domain은 web을 의존하지 않는다고 표현한다.
예를 들어 web 패키지를 모두 삭제해도 domain에는 전혀 영향이 없도록 의존관계를 설계하는 것이 중요하다.
반대로 이야기하면 domain은 web을 참조하면 안된다.

## 홈 화면

### HomeController

```java

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
```

### home.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <link href="css/bootstrap.min.css" rel="stylesheet" th:href="@{/css/bootstrap.min.css}">
</head>
<body>
<div class="container" style="max-width: 600px">
    <div class="text-center py-5">
        <h2>홈 화면</h2>
    </div>

    <div class="row">
        <div class="col">
            <button class="w-100 btn btn-secondary btn-lg" th:onclick="|location.href='@{/members/add}'|" type="button">
                회원 가입
            </button>
        </div>
        <div class="col">
            <button class="w-100 btn btn-dark btn-lg"
                    onclick="location.href='items.html'"
                    th:onclick="|location.href='@{/login}'|"
                    type="button">
                로그인
            </button>
        </div>
    </div>
    <hr class="my-4">
</div> <!-- /container -->
</body>
</html>
```

## 회원 가입

## 로그인 기능

## 로그인 처리하기 - 쿠키 사용

## 쿠키와 보안 문제

## 로그인 처리하기 - 세션 동작 방식

## 로그인 처리하기 - 세션 직접 만들기

## 로그인 처리하기 - 직접 만든 세션 적용

## 로그인 처리하기 - 서블릿 HTTP 세션 1

## 로그인 처리하기 - 서블릿 HTTP 세션 2

## 세션 정보와 타임아웃 설정