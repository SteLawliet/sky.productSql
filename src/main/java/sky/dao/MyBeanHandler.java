package sky.dao;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Stelawliet on 17/12/29.
 */
public class MyBeanHandler<T> extends MyListBeanHandler<T> implements ResultSetHandler{
    public MyBeanHandler(Class t) {
        super(t);
    }

    public Object handle(ResultSet rs) throws SQLException {
       List<T> list = (List<T>) super.handle(rs);
        return list.get(0) ;
    }
}
