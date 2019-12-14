package com.example.demo.service;

import com.example.demo.bean.User;
import com.example.demo.mapperMySQL.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 将来的大拿:徐林飞
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserMapper iUserMapper;

    @Override
    public int insert(User user) {
        int sum=iUserMapper.insertUser(user);
        return sum;
    }

    @Override
    public User selectByName(String userName) {
        User user= iUserMapper.selectUserByName(userName);
        return user;
    }

    @Override
    public User selectById(String id) {
        return iUserMapper.selectUserById(id);
    }

    @Override
    public void updateById(User user) {
        iUserMapper.update(user);
    }
}
