package sky.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sky.dao.DaoBean;
import sky.domain.Employee;
import sky.domain.Product;
import sky.domain.Transaction;

/**
 * Created by 赵子齐 on 17/12/30.
 */
public class ProductServ {
    public JSONArray select() {
        DaoBean<Product> daoBean = new DaoBean<>(Product.class);
        List<Product> list = daoBean.FindAll();
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        return jsonArray;
    }

    public String selectJson() {
        DaoBean<Product> daoBean = new DaoBean<>(Product.class);
        List<Product> list = daoBean.FindAll();
        return JSON.toJSONString(list);
    }

    public String getTransactions() {
        DaoBean<Transaction> daoBean = new DaoBean<>(Transaction.class);
        DaoBean<Product> daoBean1 = new DaoBean<>(Product.class);
        List<Transaction> list = daoBean.FindAll();
        List<Transaction> transactionList = new ArrayList<>();
        String no = null;
        Transaction t0 = null;
        String col = "product_no";
        for (Transaction t1 : list) {
            Product p = daoBean1.SelectBy(t1.getProduct_no(), col);
            String s = t1.getChange_quantity();
            p.setTemp(Integer.parseInt(s));
            if (null != no && no.equals(t1.getTran_no())) {
                t0.getProductList().add(p);
            } else {
                t0 = t1;
                no = t1.getTran_no();
                t0.getProductList().add(p);
                transactionList.add(t0);
            }
        }

        for (Transaction t : transactionList) {
            daoBean.setTableName("purchase");
            int con = (int) daoBean.selectMap(t.getTran_no(), "purchase_no").get("purchase_desc");
            int price = 0;
            boolean con0 = con > 0;

            for (Product p : t.getProductList()) {
                price = price + p.getUnitPrice() * p.getTemp();
            }
            t.setUnitprice(price);
            t.setConfirm(con0);

        }
        return JSON.toJSONString(transactionList);
    }

    public int updateQuantity(String changeVal, String whereNo) {

        DaoBean<Product> daoBean = new DaoBean<>(Product.class);

        daoBean.update("quantity", changeVal, whereNo);

        return daoBean.SelectByName(whereNo).getQuantity();
    }


    public Product selectByNo(String no) {
        DaoBean<Product> daoBean = new DaoBean(Product.class);
        return daoBean.SelectBy(no, "product_no");
    }


    public void tempChange(List<Map> changeVal, String empNo, String supplier) {
        DaoBean daoBean = new DaoBean<>("transaction");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String nowTime = sdf.format(new Date());
        String tran_no = UUID.randomUUID().toString().substring(0, 6);
        daoBean.setTableName("purchase");//获取到前端发送的订单后加入到订单表
        daoBean.addIsert("purchase_no", tran_no, "employee_no", empNo);
        daoBean.update("purchase_desc", "0", "purchase_no", tran_no);
        daoBean.setTableName("transaction");//根据前端选择的产品库存变动开始多交易记录表操作
        for (Map m : changeVal) {
            String uuid = UUID.randomUUID().toString().substring(0, 6);
            daoBean.addIsert("tran_no", tran_no, "id", uuid);
            String pro_no = m.get("name").toString();
            String pro_change = m.get("value").toString();
            Product p = selectByNo(pro_no);
            daoBean.update("product_no", pro_no, "id", uuid);
            daoBean.update("change_quantity", pro_change, "id", uuid);
            daoBean.update("tran_date", nowTime, "id", uuid);
            daoBean.update("tran_desc", empNo, "id", uuid);
            daoBean.update("purchase_no", tran_no, "id", uuid);
            daoBean.update("units_ordered", supplier, "id", uuid);
            daoBean.update("unitprice", String.valueOf(p.getUnitPrice() * Integer.parseInt(pro_change)), "id", uuid);
        }
    }


    public void confirmChange(List<Map> changeVal) {
        DaoBean<Product> daoBean = new DaoBean<>(Product.class);
        for (Map m : changeVal) {
            String pro_no = m.get("no").toString();
            String pro_temp = m.get("temp").toString();
            String pro_quantity = m.get("quantity").toString();
            int change = Integer.parseInt(pro_temp) + Integer.parseInt(pro_quantity);
            daoBean.update("quantity", String.valueOf(change),
                    "product_no", pro_no);
        }
    }

    public String search(String search) {
        DaoBean<Product> daoBean = new DaoBean<>(Product.class);
        List<Product> list = daoBean.FindAll(search);
        return JSONArray.toJSONString(list);
    }


    public Boolean intChangeQuan(int changeVal, String whereNo) {

        DaoBean daoBean = new DaoBean("purchase");
        return false;
    }

    public Boolean isConfirm(String whereNo) {

        DaoBean daoBean = new DaoBean("purchase");
        String ren = daoBean.update("purchase_desc", "1", "purchase_no", whereNo);
        return false;
    }

    public void tempChange() {

    }


    public Employee findEmp(String uname, String pwd) {

        DaoBean<Employee> daoBean = new DaoBean<>(Employee.class);
        Employee employee = null;
        employee = daoBean.SelectBy(uname, "emp_name");
        if (employee != null && employee.getPwd() != null && employee.getPwd().equals(pwd)) {
            return employee;
        } else {
            return null;
        }
    }

    public Employee findEmp(String uname) {

        DaoBean<Employee> daoBean = new DaoBean<>(Employee.class);
        Employee employee = null;
        employee = daoBean.SelectBy(uname, "emp_name");
        if (employee != null) {
            return employee;
        } else {
            return null;
        }
    }

    public Employee addEmp(String name, String pwd) {
        DaoBean<Employee> daoBean = new DaoBean<>(Employee.class);
        Employee employee = null;
        int count = daoBean.getCount() + 1;
        String cou = "00" + String.valueOf(count);
        daoBean.addIsert("emp_no", cou);
        daoBean.update("emp_name", name, "emp_no", cou);
        daoBean.update("pwd", pwd, "emp_no", cou);
        return findEmp(name);
    }
}
