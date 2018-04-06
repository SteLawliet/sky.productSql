package sky.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import sky.Annotation.Column;
import sky.Annotation.TableName;

/**
 * Created by 赵子齐 on 17/12/20.
 */
public class DaoBean<T> {

    private static DataSource ds;

    static {
        ds = new ComboPooledDataSource();
    }

    private String tableName;
    private Class beanClass;

    public DaoBean(String tableName) {

        this.tableName = tableName;

    }

    public DaoBean(Class<T> tClass) {
        this.beanClass = tClass;
        tableName = ((TableName) beanClass.getAnnotation(TableName.class)).value();
    }

    public DaoBean(Class<T> tClass, String tableName) {
        this.beanClass = tClass;
        this.tableName = tableName;
    }

    protected DaoBean() {
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType type1 = (ParameterizedType) type;
        beanClass = (Class) type1.getActualTypeArguments()[0];
        tableName = ((TableName) beanClass.getAnnotation(TableName.class)).value();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 查询单个记录
     *
     * @param name where name = ?
     * @return 注解bean
     */

    public T SelectByName(String name) {


        String sql = "select * from " + tableName + " where product_no =?";


        QueryRunner qR = new QueryRunner(ds);

        T t1 = null;
        try {

            t1 = (T) qR.query(sql, new MyBeanHandler<T>(beanClass), name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t1;
    }


    public T SelectBy(String val, String byColumn) {


        String sql = "select * from " + tableName + " where " + byColumn + " =" + "'" + val + "'";
        //todo
        System.out.println(sql);

        QueryRunner qR = new QueryRunner(ds);

        T t1 = null;
        try {

            t1 = (T) qR.query(sql, new MyBeanHandler<T>(beanClass));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return t1;

    }


    /**
     * 查询所有的记录
     *
     * @return 返回注解bean
     */
    public List<T> FindAll() {
        String sql = "Select * from " + this.tableName;
        QueryRunner qR = new QueryRunner(ds);
        List<T> list = null;
        try {
            list = (List<T>) qR.query(sql, new MyListBeanHandler<T>(beanClass));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 查询符合条件的记录
     *
     * @param search 查询条件或分类
     * @return 返回注解bean
     */
    public List<T> FindAll(String search) {
        String sql = "Select * from " + this.tableName + " where product_name  like \'%" + search + "%\' or category_no = " + "\'" + search + "\'";
        //todo
        System.out.println(sql);
        QueryRunner qR = new QueryRunner(ds);
        List<T> list = null;
        try {
            list = (List<T>) qR.query(sql, new MyListBeanHandler<T>(beanClass));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;

    }


    public List<Map<String, Object>> selectListMap() {

        String sql = "select * from " + tableName;
        QueryRunner qr = new QueryRunner(ds);
        List<Map<String, Object>> map = null;
        try {
            map = qr.query(sql, new MapListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    public Map<String, Object> selectMap(String whereNo, String noName) {
        String sql = "select * from " + tableName + " where " + noName + " = \'" + whereNo + "\'";
        System.out.println(sql);
        QueryRunner qr = new QueryRunner(ds);
        Map<String, Object> map = null;
        try {
            map = qr.query(sql, new MapHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;

    }


    /**
     * change 更新记录
     *
     * @param change 更新的column和条件
     * @return true or false
     */
    public Boolean set(String change) {
        String sql = " UPDATE " + tableName + " SET " + change;
        System.out.println(sql);
        int re = 0;
        try {
            re = new QueryRunner(ds).update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return re > 0;
    }

    public String update(String changeColumn, String changeVal, String noName, String whereNo) {
        String sql = " UPDATE " + tableName + " SET "
                + changeColumn + " = \'" + changeVal + "\' where "
                + noName + " =\'" + whereNo + "\'";
        try {
            new QueryRunner(ds).update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return changeVal;
    }

    public String update(String changeColumn, String changeVal, String whereNo) {

        update(changeColumn, changeVal, "product_no", whereNo);

        return changeVal;
    }

    public void tempChange(String changeNo, int changeVal) {
        String sql = " UPDATE " + tableName + " SET " + changeNo + " = " + changeVal;
        System.out.println(sql);
        try {
            new QueryRunner(ds).update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tempChange(String[] changes) {
        StringBuffer valPre = new StringBuffer("?");

        for (int i = 0; i < changes.length - 1; i++) {
            valPre = valPre.append(",?");
        }
        String sql = "Insert " + this.tableName + " VALUES (" + valPre.toString() + ")";
        QueryRunner qr = new QueryRunner(ds);
        try {
            qr.update(sql, changes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加交易记录
     *
     * @param colName 订单编号
     */
    public void addColumn(String colName) {
        String name = "`" + colName + "`" + "INT(10)";
        String sql = "ALTER TABLE sky_db.update_pro ADD " + name;
        QueryRunner qr = new QueryRunner(ds);
        try {
            qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addIsert(String colName0, String val0, String colName1, String val1) {
        String sql = "INSERT " + tableName + " (" + colName0 +
                "," + colName1 + ") VALUE (\'" + val0 + "\',\'" +
                val1 + "\')";
        QueryRunner qr = new QueryRunner(ds);
        try {
            qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIsert(String colName0, String val0) {
        String sql = "INSERT " + tableName + " (" + colName0 + ") VALUE (\'" + val0 + "\')";
        //todo debug
        System.out.println(sql);
        QueryRunner qr = new QueryRunner(ds);
        try {
            qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public int AddMap(List<Map<String, String>> mapList) {

        int size = mapList.get(0).size();

        StringBuffer valPre = new StringBuffer("?");

        for (int i = 0; i < size - 1; i++) {
            valPre = valPre.append(",?");
        }
        String sql = "Insert " + this.tableName + "(product_no, product_name, unitprice, date_product, pic_no, category,category_no) VALUES (" + valPre.toString() + ")";

        String[][] params = new String[55][];

        for (int i = 0; i < params.length; i++) {
            Map<String, String> map = mapList.get(i);
            String[] strings = new String[size];
            int j = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                strings[j] = entry.getValue();
                j++;
            }
            params[i] = strings;
            System.out.println(params);
            System.out.println(params.length);
        }

        QueryRunner qr = new QueryRunner(ds);
        int re = 0;
        try {
            re = qr.batch(sql, params).length;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void Add(T t) {
        int len = getValue(t).length;

        StringBuffer s = new StringBuffer("?");
        for (int i = 0; i < len - 1; i++) {
            s = s.append(",?");
        }
        String parm = s.toString();
        String sql = "Insert " + this.tableName + " VALUES(" + parm + ")";


        Object[] p = getValue(t);
        QueryRunner queryRunner = new QueryRunner(ds);
        try {
            int count = queryRunner.update(sql, p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Delete(String name) {
        try {
            String sql = "Delete From " + this.tableName + " where username=?";
            QueryRunner queryRunner = new QueryRunner(ds);
            queryRunner.update(sql, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Field> getHasAoMethods() {
        Field[] fields = this.beanClass.getDeclaredFields();
        List<Field> fieldList = new ArrayList<Field>();
        for (Field f : fields) {
            if (f.getAnnotation(Column.class) != null) {
                fieldList.add(f);
            }

        }
        return fieldList;
    }

    private String[] getValue(T t) {

        List<Field> fieldList = getHasAoMethods();
        String[] val = new String[fieldList.size()];
        for (int i = 0; i < val.length; i++) {
            try {
                fieldList.get(i).setAccessible(true);
                val[i] = String.valueOf(fieldList.get(i).get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return val;
    }

    public int getCount() {

        String sql = "Select Count(*) from " + this.tableName;
        QueryRunner qr = new QueryRunner(ds);
        Number number = null;
        try {
            number = (Number) qr.query(sql, new ScalarHandler<Number>());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return number != null ? number.intValue() : 0;

    }

    public DataSource getDs() {
        return ds;
    }

}


