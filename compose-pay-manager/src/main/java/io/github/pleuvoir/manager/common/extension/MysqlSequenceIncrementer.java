package io.github.pleuvoir.manager.common.extension;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.incrementer.AbstractColumnMaxValueIncrementer;

import lombok.extern.slf4j.Slf4j;

/**
 * 该类修改自
 * #{org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer}<br>
 * 原始类只能在表中设置一行记录进行序列的获取，增强后可在单表中多列进行序列的获取，当超出设置的最大值时重置为cacheSize，也就是序号重新从0开始
 * 
 * @author pleuvoir
 * 
 */
@Slf4j
public class MysqlSequenceIncrementer extends AbstractColumnMaxValueIncrementer {

	private static final String VALUE_SQL = "select last_insert_id()";

	private long nextId = 0;

	/**
	 * 缓存后的id
	 */
	private long maxId = 0;

	/**
	 * 每个序列的名称
	 */
	private String sequenceName;

	@Override
	protected synchronized long getNextKey() {
		if (this.maxId == this.nextId) {
			/*
			 * Need to use straight JDBC code because we need to make sure that the insert
			 * and select are performed on the same connection (otherwise we can't be sure
			 * that last_insert_id() returned the correct value)
			 */
			Connection con = DataSourceUtils.getConnection(getDataSource());
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
				// Increment the sequence column...
				String columnName = getColumnName();

				String sql = "update " + getIncrementerName() + " set " + columnName + " = last_insert_id(" + columnName
						+ " + " + getCacheSize() + ") where sequence_name = '" + getSequenceName() + "';";

				stmt.executeUpdate(sql);
				// Retrieve the new max of the sequence column...
				ResultSet rs = stmt.executeQuery(VALUE_SQL);
				try {
					if (!rs.next()) {
						throw new DataAccessResourceFailureException(
								"last_insert_id() failed after executing an update");
					}
					this.maxId = rs.getLong(1);
				} finally {
					JdbcUtils.closeResultSet(rs);
				}
				this.nextId = this.maxId - getCacheSize() + 1;
			} catch (SQLException ex) {
				//超长重置为一个数，目前为cacheSize，实际上可以针对具体的业务设置一个起始值避免和原有的重复，程序中获取到的序列值为0
				if (ex.getSQLState() == "22001") { //Data TooLong Exception
					String sql = "update " + getIncrementerName() + " set " + getColumnName() + " =  " + getCacheSize()
							+ " where sequence_name = '" + getSequenceName() + "';";
					try {
						stmt.executeUpdate(sql);
					} catch (SQLException e) {
						log.error("超长重置为cacheSize失败", e);
						throw new DataAccessResourceFailureException("超长重置为cacheSize失败");
					}
					this.maxId = getCacheSize();
					this.nextId = this.maxId - getCacheSize() + 1;
				}
				throw new DataAccessResourceFailureException("Could not obtain last_insert_id()", ex);
			} finally {
				JdbcUtils.closeStatement(stmt);
				DataSourceUtils.releaseConnection(con, getDataSource());
			}
		} else {
			this.nextId++;
		}
		return this.nextId;
	}
	
	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

}
