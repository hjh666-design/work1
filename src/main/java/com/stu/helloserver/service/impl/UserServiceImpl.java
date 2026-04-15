package com.stu.helloserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu.helloserver.common.Result;
import com.stu.helloserver.common.ResultCode;
import com.stu.helloserver.dto.UserDTO;
import com.stu.helloserver.entity.User; // 【重点】导入数据库实体类User
import com.stu.helloserver.mapper.UserMapper; // 【重点】导入MyBatis-Plus的Mapper接口
import com.stu.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


@Service
public class UserServiceImpl implements UserService {

    // 1. 【核心改造】注入MyBatis-Plus的UserMapper，取代原来的内存Map
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 1. 使用LambdaQueryWrapper构建查询条件：检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        // 2. 如果用户已存在，返回错误
        if (dbUser != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }

        // 3. 用户不存在，将UserDTO转换为数据库实体类User
        User user = new User();
        user.setUsername(userDTO.getUsername());
        // 【注意】真实项目中，密码必须加密存储！这里为了演示直接存储明文。
        user.setPassword(userDTO.getPassword());

        // 4. 调用insert方法，将用户数据持久化到数据库
        userMapper.insert(user);
        return Result.success("注册成功！");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        // 1. 根据用户名查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        // 2. 校验用户是否存在
        if (dbUser == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 3. 校验密码 (注意：与注册同理，真实项目应对比加密后的密码)
        if (!dbUser.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        // 4. 登录成功，生成并返回Token（模拟JWT）
        String token = "Bearer " + UUID.randomUUID().toString();
        return Result.success(token);
    }

    @Override
    public Result<String> getUserById(Long id) {
        // 【新增方法】根据文档要求，新增通过ID查询用户的方法
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        // 这里为了简单，只返回用户名。您可以根据业务需求返回完整的用户信息
        return Result.success("用户名为: " + user.getUsername());
    }

    @Override
    public Result<Object> getUserPage(Integer pageNum, Integer pageSize) {
        // 1. 创建分页对象（参数1：当前页码，参数2：每页显示条数）
        Page<User> pageParam = new Page<>(pageNum, pageSize);

        // 2. 执行分页查询
        // 参数1：分页对象
        // 参数2：查询条件 Wrapper，这里传 null 代表无条件查询全部
        // 框架会自动执行一条 COUNT 查询总数，然后拼接 LIMIT 执行分页数据查询
        Page<User> resultPage = userMapper.selectPage(pageParam, null);

        // 3. 组装并返回结果
        // resultPage 对象包含了：records(当前页数据列表)、total(总条数)、pages(总页数) 等属性
        return Result.success(resultPage);
    }
}