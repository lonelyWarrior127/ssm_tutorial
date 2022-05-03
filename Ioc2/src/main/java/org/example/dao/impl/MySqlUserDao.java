package org.example.dao.impl;

import org.example.dao.UserDao;
import org.example.domain.SysUser;
import org.springframework.stereotype.Repository;

@Repository("mysqlDao")
public class MySqlUserDao implements UserDao {
    @Override
    public void insertUser(SysUser user) {
        System.out.println("执行数据库insert操作");

    }
}
