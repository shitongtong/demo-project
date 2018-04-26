package cn.stt.sqllite;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/10/25.
 */
public class SqlLiteRead {
    public static void main(String[] args) throws Exception {

    }

    public static void parseDBFile() throws Exception {
        String sql = "jdbc:sqlite://d:/ddf07d57825d4004bee6d37d73af669b10.db";
        Connection conn = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            // 1 建立一个数据库名ddf07d57825d4004bee6d37d73af669b10.db的连接，如果不存在就在当前目录下创建之
            conn = DriverManager.getConnection(sql);
            Statement stat = conn.createStatement();
            rs = stat.executeQuery("select * from board_message"); // 查询数据
            while (rs.next()) {
                String data = rs.getString("data");
                if ("start-course".equals(data)) {
                    continue;
                }
                JSONObject jsonObject = JSON.parseObject(data);
                jsonObject.get("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close(); // 结束数据库的连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
