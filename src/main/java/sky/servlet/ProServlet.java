package sky.servlet;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sky.domain.Employee;
import sky.service.ProductServ;

/**
 * Created by 赵子齐 on 17/12/30.
 */
@WebServlet(name = "BServlet", urlPatterns = "/do")
public class ProServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String methodName = request.getParameter("fun");

        try {

            if (methodName == null) {
                throw new NoSuchMethodException();
            }

            Method method = this.getClass().getMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);
            String re = (String) method.invoke(this, request, response);


            if (re == null) {
                return;
            }
            String[] res = re.split(":");

            if (res[0].equals("f")) {
                request.getRequestDispatcher(res[1]).forward(request, response);
            } else {
                response.sendRedirect("/pro/index.html");
            }

        } catch (NoSuchMethodException e) {

            throw new RuntimeException("the method is null");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public String select(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String search = request.getParameter("search");

        ProductServ pro = new ProductServ();
        if (search != null && !search.equals("")) {

            search = search.trim().toLowerCase();
            response.getWriter().print(pro.search(search));
            return null;

        } else {

            response.getWriter().print(pro.selectJson());
            return null;

        }

    }

    public String updateQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String val = request.getParameter("changeval");
        String no = request.getParameter("whereno");

        //todo debug
        System.out.println("up");

        ProductServ pr = new ProductServ();

        int res = pr.updateQuantity(val, no);
        if (res == Integer.parseInt(val)) {
            response.getWriter().print(val);
        } else {
            response.getWriter().print(res);
        }
        return null;
    }

    public String tempChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = request.getParameter("json");
        String supplier = request.getParameter("supplier");
        Cookie[] cookies = request.getCookies();
        String empNo = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("no")) {
                empNo = c.getValue();
                break;
            }
        }
        if (json == null || empNo == null || supplier == null) {
            return null;
        }
        List<Map> mapList = JSONArray.parseArray(json, Map.class);
        ProductServ pr = new ProductServ();
        pr.tempChange(mapList, empNo, supplier);
        return null;
    }

    public String confirmChange(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = request.getParameter("json");
        String confirm = request.getParameter("purchase_no");
        if (json == null || confirm == null) {
            return null;
        }
        System.out.println(json);
        List<Map> mapList = JSONArray.parseArray(json, Map.class);
        //todo debug
        System.out.println(mapList);
        ProductServ pr = new ProductServ();
        pr.isConfirm(confirm);
        pr.confirmChange(mapList);
        return null;
    }

    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = request.getParameter("json");
        List<Map> json1 = JSONArray.parseArray(json, Map.class);
        String username = json1.get(0).get("value").toString().trim().toLowerCase();
        String pwd = json1.get(1).get("value").toString().trim();
        ProductServ pr = new ProductServ();
        Employee emp = pr.findEmp(username, pwd);
        //todo
        String json0 = JSON.toJSONString(emp);
        if (emp != null) {
            response.getWriter().print(json0);
        } else {
            response.getWriter().print("null");
        }
        return null;
    }


    public String signup(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ProductServ pr = new ProductServ();
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");

        Employee emp = pr.addEmp(username, pwd);
        String json0 = JSON.toJSONString(emp);
        if (emp != null) {
            response.getWriter().print(json0);
        }
        return null;
    }

    public String username(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductServ pr = new ProductServ();
        String username = request.getParameter("username");
        Employee emp = pr.findEmp(username);
        if (emp == null) {
            response.getWriter().print("1");
        } else {
            response.getWriter().print("0");

        }
        return null;
    }

    public String gettrans(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (checkCookies(request)) {
            ProductServ pr = new ProductServ();
            response.getWriter().print(pr.getTransactions());
        } else {
            response.getWriter().print("null");
        }

        return null;
    }

    protected Boolean checkCookies(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String no = null;
        String username = null;
        String pwd = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("no")) {
                no = c.getValue();
            }
            if (c.getName().equals("username")) {
                try {
                    username = URLDecoder.decode(c.getValue(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (c.getName().equals("pwd")) {
                pwd = c.getValue();
            }
        }
        ProductServ pr = new ProductServ();
        return no != null && username != null && pwd
                != null && pr.findEmp(username, pwd) != null;

    }
}
