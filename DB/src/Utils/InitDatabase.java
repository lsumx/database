package Utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public class InitDatabase {
    private static JDBCUtils jdbcUtils =new JDBCUtils();
    private static BufferedWriter bufferedWriter;
    private static String prefix ="com.mysql.cj.jdbc.ClientPreparedStatement: ";



//    初始化教师
    private static void initInstructors() throws IOException{
        Random random =new Random(System.currentTimeMillis());
        String[] name={"Alice","John","Tom","Alexander","Andrew","Ann","Ben","Ava","Adam","Alan"};
        String[] dept_name ={"CS","SS","DS","Math","Biology"};

        for (int i =0;i<10;i++){
            String id = "T"+new DecimalFormat("00").format(i);
           bufferedWriter.write("insert into instructor(i_id,name,dept_name) values("+id+","+name[i]+","+dept_name[i%dept_name.length]+")\r\n");
        }

    }

//    初始化教室
private static void initDepartment() throws IOException{
    String[] dept_name ={"CS","SS","DS","Math","Biology"};

    for (int i =0;i<dept_name.length;i++){
//        String id = "T"+new DecimalFormat("00").format(i);
        bufferedWriter.write("insert into department(id,dept_name) values("+i+","+dept_name[i]+")\r\n");
    }

}

    private static void initClassroom() throws IOException{

        int [] capacities={100,25,150,200,50};
        String[] room_numbers={"Z2201","Z2103","Z3206","Z4001","Z2204","Z5304"};

        for (int i =0;i<room_numbers.length;i++){
//        String id = "T"+new DecimalFormat("00").format(i);
            bufferedWriter.write("insert into classroom(room_number,capacity) values("+room_numbers[i]+","+capacities[i%capacities.length]+")\r\n");
        }

    }



}
