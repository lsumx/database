package entity;

public class CourseEntity {
    String course_name;
    String time;
    String room_number;
    String exam_time;
    String exam_type;

    public CourseEntity(String course_name,String time,String room_number,String exam_time,String exam_type){
        this.course_name =course_name;
        this.time =time;
        this.room_number =room_number;
        this.exam_time =exam_time;
        this.exam_type =exam_type;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getRoom_number() {
        return room_number;
    }

    public String getTime() {
        return time;
    }

    public String getExam_time() {
        return exam_time;
    }

    public String getExam_type() {
        return exam_type;
    }

    public void setExam_time(String exam_time) {
        this.exam_time = exam_time;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }
}
