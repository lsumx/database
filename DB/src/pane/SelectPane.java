package pane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SelectPane {
    String id ="";
    public SelectPane(String id){
        this.id =id;
    }
    GradesPane gradesPane =new GradesPane();
    CoursePane coursePane =new CoursePane();
    SelectCoursePane selectCoursePane =new SelectCoursePane();
    SelectForCoursePane selectForCoursePane =new SelectForCoursePane();

    public Stage select_pane(String id){
        GridPane gridPane =new GridPane();
        gridPane.setPadding(new Insets(11.5,12.5,13.5,14.5));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5.5);
        gridPane.setVgap(5.5);
        Button grades =new Button("查看成绩");
        Button courses =new Button("查看课程");
        Button selectCourse =new Button("选课");
        Button selectForCourse =new Button("选课申请");
        gridPane.add(grades,0,0);
        gridPane.add(courses,0,1);
        gridPane.add(selectCourse,0,2);
        gridPane.add(selectForCourse,0,3);
        Scene scene =new Scene(gridPane);
        Stage primaryStage =new Stage();
        primaryStage.setScene(scene);

        grades.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println(1);
//                primaryStage.close();
                gradesPane.grade_pane(id).show();
            }
        });

        courses.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                primaryStage.close();
                coursePane.course_pane(id).show();
            }
        });

        selectCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                primaryStage.close();
                selectCoursePane.select_course_pane(id).show();
            }
        });


        selectForCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectForCoursePane.selectForCoursePane(id).show();
            }
        });

        primaryStage.setWidth(500);

        primaryStage.setHeight(500);

        primaryStage.setResizable(false);
        return primaryStage;
    }
}
