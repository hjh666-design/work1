package com.stu.helloserver.controller;

import com.stu.helloserver.common.Result;
import com.stu.helloserver.common.ResultCode;
import com.stu.helloserver.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // GET - 查询用户（通过ID）
    @GetMapping("/{id}")
    public Result<String> getUserById(@PathVariable Long id) {
        String data = "查询成功，正在返回 ID为 " + id + " 的用户信息";
        return Result.success(data);
    }

    // POST - 新增用户
    @PostMapping
    public Result<String> createUser(@RequestBody User user) {
        String data = "新增成功，接收到用户：" + user.getName() + "，年龄：" + user.getAge();
        return Result.success(data);
    }

    // PUT - 更新用户（通过ID）
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        String data = "更新成功，用户ID：" + id + " 的新信息为：姓名=" + user.getName() + "，年龄=" + user.getAge();
        return Result.success(data);
    }

    // DELETE - 删除用户（通过ID）
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        String data = "删除成功，已删除ID为 " + id + " 的用户";
        return Result.success(data);
    }
}