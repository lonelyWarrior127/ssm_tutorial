SpringMVC注解式开发
需求：用户发起一个请求，SpringMVC接收请求，显示请求的处理结果

servlet的实现方式：
   jsp发起------servlet-----jsp显示结果

步骤：
 1. 新建web应用 webapp
 2. 加入web依赖
     spring-webmvc依赖（springmvc框架依赖），servlet依赖
 3. 声明SpringMVC核心对象DispatcherServlet
    1)DispatcherServlet是一个Servlet对象，也叫做前端控制器
    2）DispatcherServlet作用：
       a. 在servlet的init()方法中，创建SpringMVC中的容器对象
       b. 作为servlet，接收请求
 4. 创建jsp发起请求
 5. 创建一个普通类，作为控制器使用（代替之前的servlet）
     1) 在类的上面加入@Controller注解
     2）在类中定义方法，方法的上面加入@RequestMapping注解
        方法处理请求的，相当于servlet的doGet,doPost
     3）普通类，相当于servlet，但是不是servlet类
 6. 创建作为结果的jsp页面
 7. 创建SpringMVC的配置文件（Spring的配置文件一样）
   1）声明组件扫描器，指定@Controller注解所在包名
   2）声明视图解析器对象
 8. 使用逻辑视图名称