package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
        JDBCUtils jdbcUtils =new JDBCUtils();
        Connection connection =jdbcUtils.getConnection();

        String sql ="show databases";
        PreparedStatement preparedStatement =null;
        ResultSet resultSet =null;

        try {
            preparedStatement =connection.prepareStatement(sql);
            resultSet =preparedStatement.executeQuery();
            while (resultSet.next())
                System.out.println(resultSet.getString("Database"));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbcUtils.close(resultSet,preparedStatement,connection);
        }
    }
}
