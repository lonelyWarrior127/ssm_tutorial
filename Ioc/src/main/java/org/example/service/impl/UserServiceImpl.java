package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.domain.SysUser;
import org.example.service.UserService;

//利用一些算法实现业务逻辑
public class UserServiceImpl implements UserService {

    private UserDao userDao=null;
    @Override
    public void addUser(SysUser user) {
        userDao.insertUser(user); //可以换成Mybatis的代理对象
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
