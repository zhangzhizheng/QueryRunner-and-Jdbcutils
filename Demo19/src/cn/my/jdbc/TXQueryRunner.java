package cn.my.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
/**
 * 重写父类QueryRunner的一些方法
 * 这个类中的方法，自己处理连接问题
 * 无需外接传递
 * 处理：通个过JdbcUtils_2.getConnection()得到来连接
 * 在通过JdbcUtils_2.releaseConnection()完成对接连接释放：如果普通连接关闭，事务连接不关
 * @author Administrator
 *
 */
public class TXQueryRunner extends QueryRunner{

	//批处理
	@Override
	public int[] batch(String sql, Object[][] params) throws SQLException {
		/**
		 * 得到连接，执行父类方法，释放连接，返回值
		 */
		Connection conn=JdbcUtils_2.getConnection();
		int []results=super.batch(conn, sql, params);
		JdbcUtils_2.releaseConnection(conn);
		return results;
		
	}

	@Override
	public <T> T query(String sql, Object param, ResultSetHandler<T> rsh) throws SQLException {
		Connection conn=JdbcUtils_2.getConnection();
		T results=super.query(conn, sql, param,rsh);
		JdbcUtils_2.releaseConnection(conn);
		return results;
	}

	@Override
	public <T> T query(String sql, Object[] params, ResultSetHandler<T> rsh) throws SQLException {
		Connection conn=JdbcUtils_2.getConnection();
		T results=super.query(conn, sql, params,rsh);
		JdbcUtils_2.releaseConnection(conn);
		return results;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		Connection conn=JdbcUtils_2.getConnection();
		T results=super.query(conn, sql, rsh);
		JdbcUtils_2.releaseConnection(conn);
		return results;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
		Connection conn=JdbcUtils_2.getConnection();
		T results=super.query(conn, sql,rsh);
		JdbcUtils_2.releaseConnection(conn);
		return results;
	}

	@Override
	public int update(String sql) throws SQLException {
		Connection conn=JdbcUtils_2.getConnection();
		int results=super.update(conn, sql);
		JdbcUtils_2.releaseConnection(conn);
		return results;
	}

	@Override
	public int update(String sql, Object param) throws SQLException {
		Connection conn=JdbcUtils_2.getConnection();
		int results=super.update(sql, param);
		JdbcUtils_2.releaseConnection(conn);
		return results;
	}

	@Override
	public int update(String sql, Object... params) throws SQLException {
		Connection conn=JdbcUtils_2.getConnection();
		int results=super.update(conn, sql,params);
		JdbcUtils_2.releaseConnection(conn);
		return results;
	}
	
}
