package pane;

import dao.StudentDAO;
import entity.CourseEntity;
import entity.GradesEntity;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CoursePane extends Application {
    StudentDAO studentDAO =new StudentDAO();
    @Override
    public void start(Stage primaryStage) throws Exception {
        String id ="S001";
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show courses");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        TableView<CourseEntity> tableView =new TableView<>();
        ObservableList<CourseEntity> data = FXCollections.observableArrayList(studentDAO.getCourses(id));
        tableView.setItems(data);
        Label label =new Label("课程表");
        label.setFont(new Font("Arial",20));

//String course_name,String time,String room_number,String exam_time,String exam_type
        TableColumn firstCol =new TableColumn("课程名称");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        TableColumn secCol = new TableColumn("上课时间");
        secCol.setMinWidth(100);
        secCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn thirdCol =new TableColumn("上课地点");
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("room_number"));
        thirdCol.setMinWidth(100);
        TableColumn fourthCol =new TableColumn("考试时间");
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("exam_time"));
        TableColumn fifthCol =new TableColumn("考试方式");
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("exam_type"));
        fifthCol.setMinWidth(100);
        tableView.getColumns().addAll(firstCol,secCol,thirdCol,fourthCol,fifthCol);

        VBox vBox =new VBox();
        vBox.getChildren().addAll(label,tableView);


        ((Group)scene.getRoot()).getChildren().addAll(vBox);


        primaryStage.setScene(scene);
        primaryStage.show();



    }

    public Stage course_pane(String id){
        Stage primaryStage =new Stage();
        Scene scene =new Scene(new Group());
        primaryStage.setTitle("show courses");
        primaryStage.setWidth(450);
        primaryStage.setHeight(500);

        TableView<CourseEntity> tableView =new TableView<>();
        ObservableList<CourseEntity> data = FXCollections.observableArrayList(studentDAO.getCourses(id));
        tableView.setItems(data);
        Label label =new Label("课程表");
        label.setFont(new Font("Arial",20));

//String course_name,String time,String room_number,String exam_time,String exam_type
        TableColumn firstCol =new TableColumn("课程名称");
        firstCol.setMinWidth(100);
        firstCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));

        TableColumn secCol = new TableColumn("上课时间");
        secCol.setMinWidth(150);
        secCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn thirdCol =new TableColumn("上课地点");
        thirdCol.setMinWidth(100);
        thirdCol.setCellValueFactory(new PropertyValueFactory<>("room_number"));

        TableColumn fourthCol =new TableColumn("考试时间");
        fourthCol.setMinWidth(150);
        fourthCol.setCellValueFactory(new PropertyValueFactory<>("exam_time"));

        TableColumn fifthCol =new TableColumn("考试方式");
        fifthCol.setMinWidth(100);
        fifthCol.setCellValueFactory(new PropertyValueFactory<>("exam_type"));
        fifthCol.setMinWidth(100);
        tableView.getColumns().addAll(firstCol,secCol,thirdCol,fourthCol,fifthCol);

        VBox vBox =new VBox();
        vBox.getChildren().addAll(label,tableView);


        ((Group)scene.getRoot()).getChildren().addAll(vBox);


        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setWidth(630);

        primaryStage.setHeight(500);

//        primaryStage.setResizable(false);
        return  primaryStage;
    }
}
