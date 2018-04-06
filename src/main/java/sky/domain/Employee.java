package sky.domain;

import sky.Annotation.Column;
import sky.Annotation.TableName;

/**
 * Created by 赵子齐 on 18/1/2.
 */
@TableName("employee")
public class Employee {
    @Column("emp_no")
    private String no;
    @Column("emp_name")
    private String name;
    @Column("pwd")
    private String pwd;
    @Column("email")
    private String email;
    @Column("security_no")
    private String security_no;

    public Employee(String no, String name, String pwd, String email, String security_no) {
        this.no = no;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.security_no = security_no;
    }

    public Employee() {
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurity_no() {
        return security_no;
    }

    public void setSecurity_no(String security_no) {
        this.security_no = security_no;
    }

    @Override
    public String toString() {
        return "employee{" +
                "no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", email='" + email + '\'' +
                ", security_no='" + security_no + '\'' +
                '}';
    }
}
