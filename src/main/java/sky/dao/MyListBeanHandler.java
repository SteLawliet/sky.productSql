package sky.dao;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.ResultSetHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sky.Annotation.Column;

/**
 * Created by Stelawliet on 17/12/21.
 */
public class MyListBeanHandler<T> implements ResultSetHandler {

    private Class TClass;

    private boolean flag;

    private Map<String, String> beanMap;

    private  List<T> list = new ArrayList<>();

    public MyListBeanHandler(Class<T> tClass) {
        this.TClass = tClass;
        this.flag = false;
    }

    @Override
    public Object handle(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        int cols = metaData.getColumnCount();

        int i0 = 0;

        while (rs.next()) {

            flag = true;

            this.beanMap = getAnnotations();
            for (int i = 1; i < cols + 1; i++) {

                for (Map.Entry<String, String> e : beanMap.entrySet()) {

                    if (e.getValue()!=null&&e.getValue().equals(metaData.getColumnName(i))) {
                        beanMap.put(e.getKey(), rs.getString(i));

                        break;
                    }
                }
            }

            if (flag) {
                try {
                    T t = (T) TClass.newInstance();
                    BeanUtils.populate(t, beanMap);
                    list.add(t);
                } catch (IllegalAccessException
                        | InvocationTargetException |
                        InstantiationException e) {
                    e.printStackTrace();
                }

            } else {

                return null;

            }
        }




        return list;
    }

    private Map<String, String> getAnnotations() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        Field[] fields = TClass.getDeclaredFields();
        for (Field f : fields) {

            if (f.getAnnotation(Column.class) == null) {
                continue;
            }
            map.put(f.getName(), f.getAnnotation(Column.class).value());
        }

        return map;
    }

}
