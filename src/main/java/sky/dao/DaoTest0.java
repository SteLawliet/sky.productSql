package sky.dao;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import sky.domain.Product;
import sky.domain.Teacher;


/**
 * Created by Stelawliet on 17/12/28.
 */
public class DaoTest0 {
    @Test
    @Ignore
    public void fun1(){

    }
    @Test
    @Ignore
    public void fun2(){

    }
    @Test
    @Ignore
    public void fun6(){
        DaoBean<Product> daoBean = new DaoBean<>(Product.class);
        daoBean.update("quantity","1","b001");

    }
    @Test
    @Ignore
    public void fun3(){
        DaoBean<Teacher> daoBean = new DaoBean<Teacher>(Teacher.class);
        int id = daoBean.getCount()+1;
        Teacher t = new Teacher(id,"lpt000","123");
        t.setUid(String.valueOf(id));
        daoBean.Add(t);
    }

    @Test
    @Ignore
    public void SelectAll(){

        DaoBean<Product> daoBean =new DaoBean<>(Product.class);
        List<Product> list = daoBean.FindAll();
        for (Product p:list){
            System.out.println(p);
        }

        Product p = daoBean.SelectByName("手工糯米锅巴");
        System.out.println(p);
    }
    @Test
    @Ignore
    public void SelectAll0(){

        DaoBean<Teacher> daoTeacher = new DaoBean<Teacher>(Teacher.class);
        List list =daoTeacher.FindAll();
        for (Object t : list){
            System.out.println(t);
        }
    }

}
