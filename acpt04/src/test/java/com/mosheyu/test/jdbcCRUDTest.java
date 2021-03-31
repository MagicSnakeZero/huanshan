package com.mosheyu.test;


import com.mosheyu.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class jdbcCRUDTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //更新操作
    @Test
    public void testUpdate(){
        int row = jdbcTemplate.update("update account set money=? where name=?",456,"tom");

    }
    //更新操作
    @Test
    public void testDeleter(){
        int row = jdbcTemplate.update("delete from account where name=?","tom");
        System.out.println(row);

    }
    //查询操作，查询多个对象。
    @Test
    public void testQuery(){
        List<Account> accounts = jdbcTemplate.query("select * from account", new BeanPropertyRowMapper<Account>(Account.class));
        Iterator<Account> it = accounts.iterator();
        while (it.hasNext()){
            System.out.println(it.next().toString());
        }
    }
    //查询操作，查询具体的一行
    @Test
    public  void testQueryOne() {
        Account a = jdbcTemplate.queryForObject("select * from account where name = ?",
                new BeanPropertyRowMapper<Account>(Account.class), "tom");
        System.out.println(a.toString());

    }
    //查询操作，查询总行数
    @Test
    public void testQueryCount(){
        Long aLong = jdbcTemplate.queryForObject("select count(*) from account", Long.class);
        System.out.println(aLong);

    }

}
