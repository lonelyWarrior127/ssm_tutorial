package controller;

import domain.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import service.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class QueryStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stuId = request.getParameter("stuid");
        //调用service
        //创建容器，获取service,一个请求创建次数多，比较浪费，没有必要
//        String config = "spring-beans.xml";
//        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        WebApplicationContext ctx = null;
        String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        //ServletContext sc =  getServletContext(); // ServletContext,servlet中的方法
        ServletContext sc=  request.getServletContext(); //HttpServletRequest对象的饿方法
        Object attr = sc.getAttribute(key);
        if(attr !=null ){
            ctx = (WebApplicationContext) attr;

        }
        System.out.println("servlet中创建的对象容器===" + ctx);
        StudentService service= (StudentService) ctx.getBean("studentService");
        Student student = service.findStudentById(Integer.valueOf(stuId));
        System.out.println("Student查询到的对象："+student);
        request.setAttribute("stu",student);
        request.getRequestDispatcher("/show.jsp").forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
