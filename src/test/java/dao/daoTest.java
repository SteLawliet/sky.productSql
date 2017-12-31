package dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import sky.dao.DaoBean;
import sky.domain.Teacher;

/**
 * Created by Stelawliet on 17/12/29.
 */
public class daoTest {
    @Test
    @Ignore
    public void fun1(){
        DaoBean<Teacher> daoBean = new DaoBean<>(Teacher.class);

        List<Teacher> list = daoBean.FindAll();

        JSONArray jsonArray = JSONArray.fromObject(list);

        Object t =  jsonArray.get(0);

        JSONObject.toBean((JSONObject) t,Teacher.class);

        System.out.println(t);
    }
}
