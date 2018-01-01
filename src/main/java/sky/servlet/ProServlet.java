package sky.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sky.service.ProductServ;

/**
 * Created by Stelawliet on 17/12/30.
 */
@WebServlet(name = "BServlet",urlPatterns = "/do")
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
            }else {
                response.sendRedirect("/pro/index.html");
            }

        } catch (NoSuchMethodException e) {

            throw new RuntimeException("the method is null");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public String select(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        String search = request.getParameter("search");

        ProductServ pro = new ProductServ();
        if(search!=null&&search.equals("")){
            search =search.trim().toLowerCase();
            response.getWriter().print(pro.search(search));
            return null;
        }else {
            response.getWriter().print(pro.selectJson());
            return null;
        }

    }
    public String updateQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String val = request.getParameter("changeval");
        String no = request.getParameter("whereno");

        System.out.println("up");

        ProductServ pr = new ProductServ();

        int res = pr.updateQuantity(val,no);
        if(res==Integer.parseInt(val)){
            response.getWriter().print(val);
        }else {
            response.getWriter().print(res);
        }
        return null;
    }

}
