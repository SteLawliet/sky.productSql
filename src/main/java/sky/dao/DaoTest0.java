package sky.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import sky.domain.Product;


/**
 * Created by 赵子齐 on 17/12/28.
 */
public class DaoTest0 {
    @Test
    @Ignore
    public void fun1() {

    }

    @Test
    @Ignore
    public void Test_selectList() throws Exception {
        DaoBean<Product> daoBean = new DaoBean(Product.class);
        Product p = daoBean.SelectBy("b001", "product_no");
        System.out.println(p);

    }

    @Test
    @Ignore
    public void fun7() {

    }

    @Test
    @Ignore
    public void fun8() {

    }

    @Test
    @Ignore
    public void fun9() {

        DaoBean daoBean = new DaoBean("purchase");
        Map<String, Object> map = daoBean.selectMap("d7183e", "purchase_no");
        System.out.println(map);

    }


    @Test
    @Ignore
    public void fun2() {
        DaoBean daoBean = new DaoBean("product");
        daoBean.set("quantity = quantity+1 where product_no = 'b001' ");
    }

    @Test
    @Ignore
    public void fun6() throws Exception {
        DaoBean<Product> daoBean = new DaoBean<>(Product.class);
        DataSource ds = daoBean.getDs();
        QueryRunner qr = new QueryRunner(ds);
        String sql = "select product_no from product ";
        List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler());
        List<String> list = new ArrayList<>();
        for (Map<String, Object> map1 : mapList) {
            String s = (String) map1.get("product_no");
            sql = "ALTER TABLE pro_change ADD " + s + " char(5)";
            qr.update(sql);
        }
    }

    @Test
    @Ignore
    public void fun3() {
    }

    @Test
    @Ignore
    public void SelectAll() {

        DaoBean<Product> daoBean = new DaoBean<>(Product.class);

        List<Product> list = daoBean.FindAll();

        for (Product p : list) {
            System.out.println(p);
        }

        Product p = daoBean.SelectByName("手工糯米锅巴");
        System.out.println(p);
    }

    @Test
    @Ignore
    public void SelectAll0() {

    }

}
