package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.domain.SysUser;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//利用一些算法实现业务逻辑
@Service("userService")
public class UserServiceImpl implements UserService {

    //@Autowired()
    @Resource
    private UserDao userDao=null;
    @Override
    public void addUser(SysUser user) {
        userDao.insertUser(user); //可以换成Mybatis的代理对象
    }

}
