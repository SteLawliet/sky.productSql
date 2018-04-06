package sky.domain;

import java.util.ArrayList;
import java.util.List;

import sky.Annotation.Column;
import sky.Annotation.TableName;

/**
 * Created by 赵子齐 on 18/1/2.
 */
@TableName("transaction")
public class Transaction {
    @Column("id")
    private String id;
    @Column("tran_no")
    private String tran_no;
    @Column("tran_date")
    private String tran_date;
    @Column("tran_desc")
    private String tran_desc;
    @Column("unitprice")
    private int unitprice;
    @Column("units_ordered")
    private String units_ordered;
    @Column("units_sold")
    private String units_sold;
    @Column("change_quantity")
    private String change_quantity;
    @Column("product_no")
    private String product_no;
    private List<Product> productList = new ArrayList<>();
    private boolean confirm = false;

    public Transaction(String id, String tran_no, String tran_date, String tran_desc, int unitprice, String units_ordered, String units_sod, String change_quantity, String product_no, List<Product> productList) {
        this.id = id;
        this.tran_no = tran_no;
        this.tran_date = tran_date;
        this.tran_desc = tran_desc;
        this.unitprice = unitprice;
        this.units_ordered = units_ordered;
        this.units_sold = units_sod;
        this.change_quantity = change_quantity;
        this.product_no = product_no;
        this.productList = productList;
    }

    public Transaction() {
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTran_no() {
        return tran_no;
    }

    public void setTran_no(String tran_no) {
        this.tran_no = tran_no;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public String getTran_desc() {
        return tran_desc;
    }

    public void setTran_desc(String tran_desc) {
        this.tran_desc = tran_desc;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(int unitprice) {
        this.unitprice = unitprice;
    }

    public String getUnits_ordered() {
        return units_ordered;
    }

    public void setUnits_ordered(String units_ordered) {
        this.units_ordered = units_ordered;
    }

    public String getUnits_sold() {
        return units_sold;
    }

    public void setUnits_sold(String units_sod) {
        this.units_sold = units_sod;
    }

    public String getChange_quantity() {
        return change_quantity;
    }

    public void setChange_quantity(String change_quantity) {
        this.change_quantity = change_quantity;
    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", tran_no='" + tran_no + '\'' +
                ", tran_date='" + tran_date + '\'' +
                ", tran_desc='" + tran_desc + '\'' +
                ", unitprice=" + unitprice +
                ", units_ordered='" + units_ordered + '\'' +
                ", units_sold='" + units_sold + '\'' +
                ", change_quantity='" + change_quantity + '\'' +
                ", product_no='" + product_no + '\'' +
                ", productList=" + productList +
                ", confirm=" + confirm +
                '}';
    }
}
