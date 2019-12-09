package entity;

import Utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorEntity {
    JDBCUtils utils =new JDBCUtils();

    public boolean login_success(String id){

        try {
            String sql ="select * from instructor where i_id = ?";
            Connection connection = utils.getConnection();
            PreparedStatement ps =connection.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                System.out.println("登陆成功");
                return true;
            }
            else {
                System.out.println("登录失败");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }



}
