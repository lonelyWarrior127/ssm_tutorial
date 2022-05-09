package controller;

import domain.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import service.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strName = request.getParameter("name");
        String strAge = request.getParameter("age");
        //调用service
//        //创建容器，获取service,一个请求创建次数多，比较浪费，没有必要
//        String config = "spring-beans.xml";
//        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

        //使用了监听器已将创建好了容器对象，从ServletContext作用域中获取容器对象
        //使用Spring提供的工具方法，获取容器对象
        ServletContext sc = getServletContext();
        WebApplicationContext ctx =  WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        // WebApplicationContext ctx =  WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());


        StudentService service= (StudentService) ctx.getBean("studentService");
        Student  student = new Student();
        student.setStuname(strName);
        student.setStuage(Integer.valueOf(strAge));
        service.addStudent(student);
        //给用户现实请求的结果
        request.getRequestDispatcher("/show.jsp").forward(request,response);

    }
}
