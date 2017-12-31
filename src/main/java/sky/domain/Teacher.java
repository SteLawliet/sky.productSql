package sky.domain;


import sky.Annotation.Column;
import sky.Annotation.TableName;

/**
 * Created by Stelawliet on 17/11/6.
 */
@TableName("table_user")
public class Teacher {
    private int id;
    @Column("uid")
    private String uid;
    @Column("username")
    private String username;
    @Column("password")
    private String password;

    public Teacher(int id, String uid, String username, String password) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public Teacher(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Teacher() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
