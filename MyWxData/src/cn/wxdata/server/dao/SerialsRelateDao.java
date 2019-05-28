package cn.wxdata.server.dao;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import cn.wxdata.server.util.JDBCUtils;
public class SerialsRelateDao {
	public static int checkSerials(String serials) {
		String sql="select wx_serial ,serial_isactived from sys_wxserial where wx_serial=?";
		
		QueryRunner qRunner=new QueryRunner(JDBCUtils.getDataSource());
		try {
			List<Object[]> list=qRunner.query(sql,new ArrayListHandler(),serials);
			if(list.size()!=1) {
				return 0;
			}
			if(list.get(0)[1].toString().equals("1")) {
				return 1;
			}else {
				return 2;
			}
			//return list.get(0)[0].toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
}
