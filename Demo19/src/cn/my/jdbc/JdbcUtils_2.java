package cn.my.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils_2 {
	//使用默认配置
	private static ComboPooledDataSource dataSource=new ComboPooledDataSource();
	//事务专用连接
	private static ThreadLocal<Connection>tlc=new ThreadLocal<Connection>();
	/**
	 * 使用连接池返回一个连接对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn=tlc.get();//拿自己的线程
		//当conn不等于null，说明已经调用过beginTransaction，表示开启了事务
		if(conn!=null){
			return conn;
		}
		return dataSource.getConnection();
	}
	/**
	 * 返回连接对象         
	 */
	public static DataSource getDataSource() {
		
		return dataSource;
		
	}
	/**
	 * 开启事务
	 * 1.获取一个Connection，设置它的setAutoCommit(flase)
	 * 2.还要保证dao中使用的连接是我们刚创建的连接
	 * ----------------------------
	 * 1.创建一个Connection，设置为手动提交
	 * 2.把这个Connection给dao用
	 * 3.还要让commitTransaction或者rollbackTranscation可以获取到！
	 * @throws SQLException 
	 */
	public static void beginTransaction() throws SQLException{
		Connection conn=tlc.get();//拿自己的线程
		if(conn!=null){
			throw new SQLException("已经开启了事务，不能重复开启了！");
		}
		/**
		 * 给连接conn赋值；设置手动提交
		 */
		
		conn=JdbcUtils_2.getConnection();//表示事务开始
		conn.setAutoCommit(false);//设为手动提交
		tlc.set(conn);//把当前的线程的连接保存起来
	}
	/**
	 * 提交事务
	 * @throws SQLException 
	 */
	public static void commitTransaction() throws SQLException{
		Connection conn=tlc.get();//拿自己的线程的专用的连接
		if(conn==null){
			throw new SQLException("还没有开启事务，不能提交！");
		}
		/*
		 * 直接使用conn.commit（）
		 */
		conn.commit();
		conn.close();
		//设为null表示事务结束，下次调用getConnection()返回就不是con了
		tlc.remove();//从tlc中移除连接
	}
	/**
	 * 回滚事务
	 * @throws SQLException 
	 */
	public static void rollbackTransaction() throws SQLException{
		Connection conn=tlc.get();//拿自己的线程连接
		if(conn==null){
			throw new SQLException("还没有开启事务，不能回滚！");
		}
		conn.rollback();
		conn.close();
	}
	public static void releaseConnection(Connection connection) throws SQLException{
		Connection conn=tlc.get();//拿自己的线程连接
		/**
		 * 判断事务是不是专用的，是的就不关闭
		 */
		///等于null说明没有事务
		if(conn==null){
			connection.close();
		}
		//如果conn!=null,然后接着判断参数是否与conn相等，如果不等，说明这个不是事务专用连接
		if(conn!=connection){
			connection.close();
		}
		
	}
}
