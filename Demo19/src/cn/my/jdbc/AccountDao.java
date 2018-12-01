package cn.my.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class AccountDao {
	public static void update(String name,double money) throws SQLException{
		QueryRunner qR=new TXQueryRunner();
		String sqlString="update account set money=money+? where name=?";
		Object[] params={money,name};
		//我们需要提供 自己提供连接，以保证多次使用的是同一个连接
		//Connection conn=JdbcUtils_2.getConnection();
		qR.update(sqlString, params);
		//JdbcUtils_2.releaseConnection(conn);//用来判断事务连接后进行相应的处理
		
	}
}
