package sky.domain;

import sky.Annotation.Column;
import sky.Annotation.TableName;

/**
 * Created by 赵子齐 on 17/12/30.
 */
@TableName("product")
public class Product {
    @Column("product_no")
    private String no;
    @Column("product_name")
    private String name;
    @Column("pic_no")
    private String path;
    @Column("category")
    private String category;
    private String categoryNo;
    @Column("unitprice")
    private int unitPrice;
    @Column("quantity")
    private int quantity;
    @Column("date_product")
    private String date;
    private String serialNo;
    @Column("reorder_level")
    private String reorderLevel;
    private String reorderQuantity;
    private String reorderLeadtime;
    private int temp = 0;
    //    product_no, product_name, unitprice, date_product, pic_no, category,category_no

    public Product() {
    }


    public Product(String no) {
        this.no = no;
    }

    public Product(String no, String name) {
        this.no = no;
        this.name = name;
    }


    public Product(String no, String name, String path, String category,
                   int unitPrice, int quantity, String reorderLevel) {
        this.no = no;
        this.name = name;
        this.path = path;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
    }

    public Product(String no, String name, String path,
                   String category, String categoryNo, int unitPrice,
                   int quantity, String date, String serialNo, String
                           reorderLevel, String reorderQuantity, String reorderLeadtime, int temp) {
        this.no = no;
        this.name = name;
        this.path = path;
        this.category = category;
        this.categoryNo = categoryNo;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.date = date;
        this.serialNo = serialNo;
        this.reorderLevel = reorderLevel;
        this.reorderQuantity = reorderQuantity;
        this.reorderLeadtime = reorderLeadtime;
        this.temp = temp;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(String categoryNo) {
        this.categoryNo = categoryNo;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(String reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(String reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public String getReorderLeadtime() {
        return reorderLeadtime;
    }

    public void setReorderLeadtime(String reorderLeadtime) {
        this.reorderLeadtime = reorderLeadtime;
    }

    public String toString0() {
        return "Product{" +
                "no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", category='" + category + '\'' +
                ", categoryNo='" + categoryNo + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", reorderLevel='" + reorderLevel + '\'' +
                ", reorderQuantity='" + reorderQuantity + '\'' +
                ", reorderLeadtime='" + reorderLeadtime + '\'' +
                ", temp=" + temp +
                '}';
    }

    @Override
    public String toString() {
        return "Product{" +
                "no='" + no + '\'' +
                ", temp=" + temp +
                '}';
    }
}
