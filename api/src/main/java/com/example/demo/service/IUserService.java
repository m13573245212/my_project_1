package com.example.demo.service;

import com.example.demo.bean.User;

/**
 * @author 将来的大拿:徐林飞
 */
public interface IUserService {
    int insert(User user);
    User selectByName(String userName);
    User selectById(String id);
    void updateById(User user);
}
