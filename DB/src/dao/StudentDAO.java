package dao;


import Utils.JDBCUtils;
import entity.CourseEntity;
import entity.GradesEntity;
import entity.StudentEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

//登录，选课，退课
//数据库执行语句
public class StudentDAO {
    JDBCUtils utils =new JDBCUtils();
    String id;
    StudentEntity studentEntity =new StudentEntity();
    public StudentDAO(String id){
        id =studentEntity.getId();

    }
    public StudentDAO(){
        new StudentDAO(studentEntity.getId());
    }

   public boolean login_success(String id){

       try {
           String sql ="select * from student where s_id = ?";
           Connection connection = utils.getConnection();
           PreparedStatement ps =connection.prepareStatement(sql);
           ps.setString(1,id);
           ResultSet resultSet = ps.executeQuery();
           if (resultSet.next()){
               System.out.println("登陆成功");
               studentEntity.setId(id);
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

   //成绩查询

   public ArrayList<GradesEntity> get_grades(String id){
        ArrayList<GradesEntity> gradesEntities =new ArrayList<>();
        try {
            String sql ="select course_id,grade from takes where s_id =?";
            PreparedStatement statement =utils.getStatement(sql);
            statement.setString(1,id);
            statement.execute();
            if (statement.getResultSet()!=null)
                while (statement.getResultSet().next()){
                    String key = statement.getResultSet().getString(1);
                    String grade =statement.getResultSet().getString(2);
                    String sql2 ="select title from course where course_id =?";
                    PreparedStatement statement1 =utils.getStatement(sql2);
                    statement1.setString(1,key);
                    statement1.execute();
                    String name ="";
                    if (statement1.getResultSet().next())
                        name =statement1.getResultSet().getString(1);
                    gradesEntities.add(new GradesEntity(key,name,grade));
                }
                return gradesEntities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
   }

//课表查看
    public ArrayList<CourseEntity> getCourses(String id){
        ArrayList<CourseEntity> courseEntities =new ArrayList<>();
        String room_number ="";
        String course_name ="";
        String course_time ="";
        String exam_time ="";
        String exam_type ="";
        try {
            String sql1 ="select course_id,sec_id from takes where s_id =?";
            PreparedStatement statement =utils.getStatement(sql1);
            statement.setString(1,id);
            statement.execute();
            if (statement.getResultSet()!=null){
                while (statement.getResultSet().next()){
//这是每一节课的相关内容
                    String course_id =statement.getResultSet().getString(1);
                    String sec_id =statement.getResultSet().getString(2);
                    String sql2 = "select room_number,time_slot_id,exam_id from section where course_id =? and sec_id =?";
                    PreparedStatement statement1 =utils.getStatement(sql2);
                    statement1.setString(1,course_id);
                    statement1.setString(2,sec_id);
                    statement1.execute();
//                    //用来查询room_number和time
                    ResultSet resultSet =statement1.getResultSet();
//                    获得相应的课程id
                    while (resultSet.next()){
//                        1次
                        room_number =resultSet.getString(1);
                        String time_slot_id =resultSet.getString(2);
                        String exam_id =resultSet.getString(3);
//                        System.out.println(room_number +" "+time_slot_id+" "+exam_id);
                        String[] times =time_slot_id.split(",");
//                        1次
                        for (int i =0;i<times.length;i++){
                            String sql3 ="select day,start_time,end_time from time_slot where time_slot_id =?";
                            PreparedStatement statement2 =utils.getStatement(sql3);
                            statement2.setString(1,times[i]);
                            statement2.execute();
                             course_time ="";
                            while (statement2.getResultSet().next()){
                                course_time +=" "+statement2.getResultSet().getString(1)+"-"+statement2.getResultSet().getString(2)+" "+statement2.getResultSet().getString(3);
                            }
                        }
                        String sql4 ="select day,start_time,end_time,exam_type from exam where exam_id=?";
                        PreparedStatement statement2 =utils.getStatement(sql4);
                        statement2.setString(1,exam_id);
//                        System.out.println(sql4);
                        statement2.execute();
//                        1次
                        if (statement2.getResultSet().next()){
                            exam_time =statement2.getResultSet().getString(1)+" "+statement2.getResultSet().getString(2)+"-"+statement2.getResultSet().getString(3);
                            exam_type =statement2.getResultSet().getString(4);
                        }
                        String sql5 ="select title from course where course_id =?";
                        PreparedStatement preparedStatement =utils.getStatement(sql5);
//                        System.out.println(course_id);
                        preparedStatement.setString(1,course_id);
                        preparedStatement.execute();
                        if (preparedStatement.getResultSet().next())
                            course_name = preparedStatement.getResultSet().getString(1);
//                        System.out.println(exam_time+" \n"+exam_type);


                    }
                }
                courseEntities.add(new CourseEntity(course_name,course_time,room_number,exam_time,exam_type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseEntities;
    }


//    没有考虑时间冲突
    public boolean select_course(String id,String course_id){
        System.out.println(is_time_valid());
        if (is_time_valid()){

            try {
//查看是否开设了这门课程
                String sql ="select * from section where course_id=?";
                PreparedStatement statement =utils.getStatement(sql);
                statement.setString(1,course_id);
                statement.execute();
                ResultSet resultSet =statement.getResultSet();
                if (resultSet.next()){
//                    这门课程是否已经选过，人数已满
                    if (select_valid(id,course_id)){
                        System.out.println(course_id);
                        String sql_insert ="insert into takes(s_id,course_id) values(?,?)";
                        PreparedStatement statement1 =utils.getStatement(sql_insert);
                        statement1.setString(1,id);
                        statement1.setString(2,course_id);
//                        System.out.println(statement1.);//
                        statement1.executeUpdate();
                        return true;
                    }else {
                        System.out.println("人数已满不可选或您已经修读过该课程");
                        return false;
                    }
                }else {
                    return  false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;

    }



//    没有加上按钮
    public void quit(String id,String course_id){
        try {
            if (is_time_valid()){
                String sql ="delete from takes where s_id =? and course_id =?";
                PreparedStatement preparedStatement =utils.getStatement(sql);
                preparedStatement.setString(1,id);
                preparedStatement.setString(2,course_id);
                preparedStatement.execute();
            }else {
                System.out.println("不在退课时间段内");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public boolean is_time_valid(){
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse("2019-12-1");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse("2019-12-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date =new Date();
        if (date.getTime()<=date2.getTime() && date.getTime()>=date1.getTime())
            return true;
        return false;
    }


    public boolean select_valid(String id,String course_id){
//        是否选过该课程
        try {
            String sql1 ="select * from takes where s_id=? and course_id=?";
            PreparedStatement statement =utils.getStatement(sql1);
            statement.setString(1,id);
            statement.setString(2,course_id);
            statement.execute();
            ResultSet resultSet =statement.getResultSet();
            if (resultSet.next())
                return false;
            else {
                String sql ="select count(*) from takes where course_id=?";
                PreparedStatement statement1 =utils.getStatement(sql);
                statement1.setString(1,course_id);
                statement1.execute();
                ResultSet resultSet1 =statement1.getResultSet();
                System.out.println(resultSet1);
                int row=0;
                if (resultSet1.next())
                    row = resultSet1.getInt(1);
//                System.out.println("row"+row);
                String sql2 ="select number from course where course_id=?";
                PreparedStatement preparedStatement =utils.getStatement(sql2);
                preparedStatement.setString(1,course_id);
                preparedStatement.execute();
               String number ="";
                if (preparedStatement.getResultSet().next()){
                    number =preparedStatement.getResultSet().getString(1);
                    System.out.println("number"+number);
                    if (row<Integer.parseInt(number))
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void select_for_course(String id,String course_id){
        try {
            if (is_time_valid()){
                if (can_select(id, course_id)){
                    String sql ="insert into course_select set s_id=? and i_id=? and course_id =?";
                    utils.getStatement(sql).execute();

                }else {
                    System.out.println("不符合选课申请条件");
                }
            }else {
                System.out.println("不在选课申请时间段内");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//
    public  boolean can_select(String id,String course_id){
        try {
            String sql1 ="select * from takes where s_id=? and course_id=?";
            PreparedStatement statement =utils.getStatement(sql1);
            statement.setString(1,id);
            statement.setString(2,course_id);
            statement.execute();
            ResultSet resultSet =statement.getResultSet();
            if (resultSet.next())
                return false;
            else {
                String sql ="select count(*) from takes where course_id=?";
                PreparedStatement statement1 =utils.getStatement(sql);
                statement1.setString(1,course_id);
                ResultSet resultSet1 =statement1.getResultSet();
                resultSet1.next();
                int row = resultSet1.getInt(1);
                String sql2 ="select number from course where course_id=?";
                PreparedStatement preparedStatement =utils.getStatement(sql2);
                preparedStatement.setString(1,course_id);
                String sql3 ="select room_number from section where course_id =? ";
                PreparedStatement preparedStatement1 =utils.getStatement(sql3);
                preparedStatement1.setString(1,course_id);
                preparedStatement1.execute();
                String room_number ="";
                if (preparedStatement1.getResultSet().next())
                    room_number =preparedStatement1.getResultSet().getString(1);
                String sqls ="select capacity from classroom where room_number=?";
                utils.getStatement(sqls).setString(1,room_number);
                utils.getStatement(sqls).execute();
                int capacity=utils.getStatement(sqls).getResultSet().getInt(1);


                int number =0;
                if (preparedStatement.getResultSet().next()){
                    number =preparedStatement.getResultSet().getInt(1);
                    if (row>=number &&row<capacity)
                        return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
