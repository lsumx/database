package entity;


//数据库表单到java的数据共享
public class StudentEntity {
    private String id;
    private String name;
    private String dept_name;
    private String school_name;


    public String getDept_name() {
        return dept_name;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }
}
