ch09-exception-handler:异常处理


步骤：
1.新建web 应用
2.加入web依赖
  spring-webmvc依赖（springmvc框架依赖）， servlet依赖。

3.声明springmvc核心对象DispatcherServlet.
  1)DispatcherServlet是一个Servlet对象。
  2)DispatcherServlet叫做前端控制器（front controller）
  3)DispatcherServlet作用：
     1.在servlet的init（）方法中，创建springmvc中的容器对象。
       WebApplicationContext ctx = new ClassPathXmlApplicationContext("appliationContext.xml")


     2.作为servlet，接收请求。

4.创建一个jsp，发起请求， 有参数name ，age

5.创建异常类MyUserException,
  子类 NameException , AgeException

6.创建一个普通的类，作为控制器使用（代替之前的servlet）
  1）在类的上面加入@Controller注解
  2）在类中定义方法. 根据请求参数name ，age的值抛出NameException ,AgeException

7.创建作为结果的jsp页面

8.创建一个普通类，做为异常的处理类。
  1）在类的上面加入@ControllerAdvice
  2) 在类中定义方法， 每个方法处理对应的异常。方法的上面加入@ExceptionHandler注解

9.创建处理异常的jsp页面。

10.创建springmvc的配置文件（spring的配置文件一样）
  1）声明组件扫描器，指定@Controller注解所在的包名
  2）声明视图解析器对象

  3）声明组件扫描器，找到@ControllerAdivce注解包名
  4）声明注解驱动


