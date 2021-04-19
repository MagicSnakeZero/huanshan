package com.mosheyu.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateTypeHandler extends BaseTypeHandler<Date> {
    //将java的类型转换为数据库需要的类型
    //i是对应的列
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        long time = date.getTime();
        preparedStatement.setLong(i,time);
    }
    //将数据库中的数据类型转换为java类型。
    //String是数据库中字段的名称，ResultSet是查询结果集
    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        //获取数据库中需要的数据（long)转换为需要的类型。
        long time = resultSet.getLong(s);
        Date date = new Date(time);
        return date;
    }
    //将数据库中的数据类型转换为java类型。
    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        long time = resultSet.getLong(i);
        Date date = new Date(time);
        return date;
    }
    //将数据库中的数据类型转换为java类型。
    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        long time = callableStatement.getLong(i);
        Date date = new Date(time);
        return date;
    }
}
