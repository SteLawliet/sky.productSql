package sky.service;

import net.sf.json.JSONArray;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import sky.dao.DaoBean;
import sky.domain.Product;

/**
 * Created by Stelawliet on 17/12/30.
 */
public class ProductServ {
    public JSONArray select(){
        DaoBean<Product> daoBean = new DaoBean<>(Product.class);
        List<Product> list =daoBean.FindAll();
        return JSONArray.fromObject(list);
    }

    public String selectJson(){

        return select().toString();
    }

    public int updateQuantity(String changeVal,String whereNo){

        System.out.println("serv up");

        DaoBean<Product> daoBean = new DaoBean<>(Product.class);

        daoBean.update("quantity",changeVal,whereNo);

        return daoBean.SelectByName(whereNo).getQuantity();
    }

    @Test
    @Ignore
    public void fun1(){
    }
}
