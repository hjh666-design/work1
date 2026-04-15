package com.stu.helloserver.dto;

public class UserDTO {
    private String username;
    private String password;

    // 必须：无参构造（Spring 反序列化所需）
    public UserDTO() {
    }

    // 可选：全参构造（方便在服务层构造对象）
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter 和 Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}