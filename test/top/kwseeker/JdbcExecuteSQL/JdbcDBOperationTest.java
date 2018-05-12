package top.kwseeker.JdbcExecuteSQL;

import org.junit.Test;
import top.kwseeker.JdbcConnection.Jdbc4Connection;

import java.sql.Connection;
import java.util.HashMap;

import static org.junit.Assert.*;

public class JdbcDBOperationTest {
    Jdbc4Connection jdbc4Connection = new Jdbc4Connection("db_url", "user_name",
                                                  "password", "resources/mysql-conn-properties.xml");

    Connection conn = jdbc4Connection.getConn();
    JdbcDBOperation jdbcDBOperation = new JdbcDBOperation(conn);

    @Test
    public void insertRow() throws Exception {
        jdbcDBOperation.insertRow("GioneeS11","深圳", 1699, 643, "009.jpg");
        jdbcDBOperation.selectQuery();
    }

    @Test
    public void updateStorage() throws Exception {

        HashMap<String, Integer> stock = new HashMap<>();
        stock.put("沃特篮球鞋", 888);
        stock.put("小米3", 222);

        jdbcDBOperation.updateStorage(stock);
        jdbcDBOperation.selectQuery();
    }

}