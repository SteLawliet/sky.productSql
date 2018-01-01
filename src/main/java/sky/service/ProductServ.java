package sky.service;

import net.sf.json.JSONArray;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

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

        DaoBean<Product> daoBean = new DaoBean<>(Product.class);

        daoBean.update("quantity",changeVal,whereNo);

        return daoBean.SelectByName(whereNo).getQuantity();
    }


    public void tempChange(String[] changeVal,String whereNo){

        DaoBean<Product> daoBean = new DaoBean<>("temp_change");

        String uuid = UUID.randomUUID().toString();

          daoBean.tempChange(changeVal);

    }

    public String search(String search){
        DaoBean<Product> daoBean =new DaoBean<>(Product.class);
        List<Product> list= daoBean.FindAll(search);

        return JSONArray.fromObject(list).toString();
    }



    public Boolean intChangeQuan(int changeVal,String whereNo){

        DaoBean<Product> daoBean = new DaoBean<>(Product.class);

        return false;
    }

    public void tempChange(){

    }

    @Test
    @Ignore
    public void fun1(){
        String s =search("文具");
        System.out.println(s);
    }
}
