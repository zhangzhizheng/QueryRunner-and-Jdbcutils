package cn.my.jdbc;

import java.sql.SQLException;

import org.junit.Test;

public class DemoTest1 {
	//模拟的是service代码测试
	private AccountDao dao=new AccountDao();
	@Test
	public void serviceMethod() throws Exception{
		try {
			JdbcUtils_2.beginTransaction();
			dao.update("zhangsan",+200);
			if (true) {
				//验证数据是否能回滚
				throw new RuntimeException();
			}
			dao.update("wanger", -200);
			
			JdbcUtils_2.commitTransaction();
		} catch (Exception e) { 
			try {
				JdbcUtils_2.rollbackTransaction();
			} catch (SQLException e1) {
			}
			throw e;//看回滚的异常
		}
		
		
	}
}
