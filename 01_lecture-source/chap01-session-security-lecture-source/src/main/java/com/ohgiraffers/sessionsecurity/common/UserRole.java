package com.ohgiraffers.sessionsecurity.common;


public enum UserRole {

    /* 필기.
    *   enum 은 "열거형 상수" 라고 한다. (상수는 묶을 수 없으니, 상수들이 묶일 수 있도록 enum 이 나옴!)
    *   미리 정의 된 상수 값들의 집합인 클래스이며,
    *   하나의 집합으로서 반복문을 사용할 수 있으며 컬렉션과 함께 사용할 수 있다.
    *   코드의 의미 전달하는데 도움을 줄 수 있으며 인스턴스를 생성할 수 있다!
    *
    * */

    // 이 2개를 가지고 올 수 있고, 접근할 수 있다.
    USER("USER"),
    ADMIN("ADMIN");

    private String role;
    UserRole(String role) { this.role = role; }

    public String getRole() { return role; }

    @Override
    public String toString() {
        return "UserRole{" +
                "role='" + role + '\'' +
                '}';
    }


}
