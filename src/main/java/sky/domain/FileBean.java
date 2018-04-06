package sky.domain;

/**
 * Created by 赵子齐 on 17/12/17.
 */
public class FileBean {

    private String id;
    private String name;
    private String type;
    private String update;
    private String size;
    private String path;

    public FileBean() {
    }

    public FileBean(String name, String type, String update, String size) {
        this.name = name;
        this.type = type;
        this.update = update;
        this.size = size;
    }

    public FileBean(String name, String type, String update, String size, String path) {
        this.name = name;
        this.type = type;
        this.update = update;
        this.size = size;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", update='" + update + '\'' +
                ", size='" + size + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
