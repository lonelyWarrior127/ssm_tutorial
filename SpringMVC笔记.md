### 1. SpringMVC概述

#### 1.1 SpringMVC基本说明

- SpringMVC基于Spring的，是Spring中的一个模块，做web开发使用。也叫做Spring web mvc。说明是Spring的核心技术，做web技术，SpringMVC内部是MVC架构模式。

- SpringMVC是一个容器，管理对象，是用IOC核心技术。

- SpringMVC管理界面层中的控制器对象SpringMVC

- SpringMVC底层也是Servlet。以Servlet为核心，接收请求，处理请求，现实处理结果给用户。

- 处理用户请求：   用户发起请求----SpringMVC------Spring------MyBatis-----Mysql数据库

- 每一层对应着一个框架

  1）界面层 ---SpringMVC框架

  2）业务逻辑层 --- Spring框架

  3）持久层 --- MyBatis框架

#### 1.2 SpringMVC的核心Servlet——DispatcherServlet

- DispatcherServlet，也叫做前端控制器是框架的一个Servlet对象，负责接收请求，响应处理结果。

- DispatcherServlet的父类是HttpServlet

- SpringMVC是管理控制请对象，原来没有SpringMVC之前使用Servlet作为控制器对象使用。现在通过SpringMVC容器创建一种叫做控制器的对象，代理Servlet行驶控制器的角色功能。

- SpringMVC主要使用注解的方式创建控制器对象，处理请求。

- DispatcherServlet：1. init()中创建springmvc的容器对象 webApplicationContext. 创建springmvc配置文件中的所有JAVA对象——Controller对象

  ​									2. DispatcherServlet是一个servlet，能够接收请求

#### 1.3 SpringMVC注解式开发
需求：用户发起一个请求，SpringMVC接收请求，显示请求的处理结果

servlet的实现方式：
   jsp发起------servlet-----jsp显示结果

步骤：
 1. 新建web应用 webapp

 2. 加入web依赖
     spring-webmvc依赖（springmvc框架依赖），servlet依赖

     ```
     <!--    springmvc-->
         <dependency>
           <groupId>org.springframework</groupId>
           <artifactId>spring-webmvc</artifactId>
           <version>5.3.18</version>
         </dependency>
     <!--servlet-->
         <dependency>
           <groupId>javax.servlet</groupId>
           <artifactId>javax.servlet-api</artifactId>
           <version>3.1.0</version>
           <scope>provided</scope>
         </dependency>
     ```

 3. 声明SpringMVC核心对象DispatcherServlet
    1)DispatcherServlet是一个Servlet对象，也叫做前端控制器
    2）DispatcherServlet作用：
       a. 在servlet的init()方法中，创建SpringMVC中的容器对象
       b. 作为servlet，接收请求

    ```xml
    <web-app>
      <display-name>Archetype Created Web Application</display-name>
      <!--  声明springmvc核心对象
            默认情况下：servlet对象在用户访问时创建
           访问地址后，保存文件没有找到 /WEB-INF/springmvc-servlet.xml
           错误原因：
              在servlet的init方法中，创建springmvc使用的容器对象WebApplicationContext.
              WebApplicationContext ctx = new ClassPathXmlWebApplicationContext(配置文件)
          配置文件你的默认路径：/WEB-INF/<servlet-name>-servlet.xml  (spring-config)
    
          自定义配置文件位置:
                <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>classpath:springmvc.xml</param-value>
                </init-param>
      -->
      <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--自定义配置文件的位置    -->
        <init-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath:springmvc.xml</param-value>
        </init-param>
        <!--表示服务器tomcat创建对象的顺序，整数值，>=0 数值越小，创建对象的时间越早    -->
        <load-on-startup>1</load-on-startup>
      </servlet>
      <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <!--url-pattern：把一些请求交给指定的servlet处理
        使用中央调度器DispatcherServlet
          1. 使用扩展名方式 格式 *.xxx, xxx是自定义扩展名
              例如*.do *.action *.mvc等等。不能使用*.jsp
              http://localhost:8080/springmvc/some.do
              http://localhost:8080/springmvc/query.do
          2. 使用斜杠"/"
           -->
        <url-pattern>*.do</url-pattern>
      </servlet-mapping>
    </web-app>
    >
    ```

 4. 创建jsp发起请求

 5. 创建一个普通类，作为控制器使用（代替之前的servlet）
     1) 在类的上面加入@Controller注解
        2）在类中定义方法，方法的上面加入@RequestMapping注解
        方法处理请求的，相当于servlet的doGet,doPost

     ```java
     
     /**
      * @Controller：创建控制器（处理器）对象 控制器：叫做后端控制器，自定义的类处理请求的
      * 位置：在类的上面，表示此类的对象，对象放在springmvc的容器中
      */
     @Controller
     public class MyController {
         //定义方法，处理请求 public void doGet(){}
     
         /**
          * springmvc框架，使用控制器类中的方法，处理请求
          * 方法的特点：
          * 1. 方法的形参，表示请求中的参数
          * 2. 方法的返回值，表示本次请求的处理结果
          *
          * @RequestMapping 请求映射
          * 属性：value 请求中的url的地址唯一值，以/开头
          		 method 请求的方式，使用RequestMethod类的枚举值。不指定method属性，请求方式没有限制
      			  		例如：RequestMethod.GET  RequestMethod.POST
          * 位置： 1. 在方法的上面 2.在类定义的上面（可选）
          * 作用：把指定的请求，交给指定的方法处理，等同于url-pattern
          * <p>
          * ModelAndView：表示本次请求的处理结果（数据或视图）
          * Model :表示数据
          * View：表示视图
          */
     
         @RequestMapping(value = {"/some.do", "/first.do"})
         public ModelAndView doSome() { //相当于doGet()
             //使用这个方法处理请求。能处理请求的方法叫做控制器方法
             // 调用service对象，处理请求，返回数据
             ModelAndView mv = new ModelAndView();
             // 添加数据
             mv.addObject("msg", "处理了some.do请求");
             mv.addObject("fun", "执行了doSome方法");
             //指定视图   setViewName("视图的完整路径")
             //mv.setViewName("/WEB-INF/view/show.jsp");
             //当前配置了视图解析器，可使用文件名称作为视图名使用-视图逻辑名称
             mv.setViewName("show");
             //使用了逻辑名称，框架使用配置文件中视图解析器的前缀和后缀，拼接为完整的视图路径
     
             //返回结果
             return mv;
         }
     
         /**
          * 当框架调用完doSome方法后，得到返回的ModelAndView。
          * 框架会在后续的处理逻辑值，处理mv对象里面的数据和视图
          * 对数据执行request.setAttribute("msg","处理了some.do请求");把数据放入到request作用域
          * 对视图执行forward转发操作，等同于 request.getRequestDispather("/show.jsp").forward(..);
          */
     
     
         @RequestMapping(value = {"/other.do", "/test/second.do"})
         public ModelAndView doOther() { //相当于doGet()
     
             ModelAndView mv = new ModelAndView();
             mv.addObject("msg", "处理了other.do请求");
             mv.addObject("fun", "执行了doOther方法");
             mv.setViewName("show");
             return mv;
         }
     }
     ```
     
 6. 创建作为结果的jsp页面

 7. 创建SpringMVC的配置文件（Spring的配置文件一样）
       1）声明组件扫描器，指定@Controller注解所在包名
          2）声明视图解析器对象

 8. 使用逻辑视图名称

#### 1.4 SpringMVC请求的处理过程

简单的处理过程：

用户发起请求some.do → Tomcat接收了请求 → DispatcherServlet → 分配MyController(dosome方法返回mv对象) → mv显示给用户

省略Tomcat:   用户some.do -------DispatcherServlet --------MyController

如果使用servlet处理请求： 用户发起请求-------没有其他对象----------- Servlet

![image-20220511132644125](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220511132644125.png)

用户发起some.do请求 -----DispatcherServlet（Servelt接收请求）-------转给MyController

```java
//模拟代码
public class DispatcherServlet extends HttpServlet{
    public void service (HttpServletRequest request,HttpServletResponse reponse){
        if("some.do".equals(request.getURI())){
            //从容器中获取MyController
            MyController c = ctx.getBean("some");
            c.doSome();
        }
        else if ....
    }
}
```

##### 1.5web开发中配置文件的说明

1. web.xml 部署描述符文件，给服务器（Tomcat）。

   作用：服务器在启动的时候，读取webxml，根据文件中的声明创建各种对象

   ​			根据文件中的声明知道请求和servlet等对象的关系

2. 框架的配置文件，springmvc的配置文件

   作用：声明框架创建的项目中的各种对象，主要是创建Controller对象

配置文件的加载顺序和功能

 1. tomcat服务器启动，读取web.xml，根据web.xml文件中的说明，创建对象

    创建DispatcherServlet对象，执行init方法，在init方法中会执行springmvc容器对象的创建

    ​		WebApplicationContext ctx  = new ClassPathXmlApplicationContext("classpath:springmvc.xml")

 2. springmvc框架， new ClassPathXmlApplicationContext("classpath:springmvc.xml")读取springmvc.xml配置文件。

    使用组件扫描器遍历包中的所有类，找到类中的@Controller @RequestMapping注解，就能创建MyController对象。

    知道some.do的请求时执行doSome方法

    以上1.2都是项目启动过程，没有执行任何的用户请求

 3. 用户发起请求some.do ----DispatcherServlet

    DispatcherServlet有WebApplicationContext，WebApplicationContext里面有MyController对象。

    请求some.do，DispatcherServlet就是知道MyController处理的

    ![image-20220511174536230](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220511174536230.png)

![image-20220511174601993](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220511174601993.png)

### 2. SpringMVC注解式开发

#### 2.1 @RequestMapping定义请求规则

@RequestMapping：

​		属性：value请求的url地址    位置：1）在方法的上面，必须的，2）在类的上面作为模块名称

​			       method 请求的方式，使用RequestMethod类的枚举值。不指定method属性，请求方式没有限制
​		  		         例如：RequestMethod.GET  RequestMethod.POST				

##### 2.1.1 指定模块名称

```java

/**
 * @Controller：创建控制器（处理器）对象 控制器：叫做后端控制器，自定义的类处理请求的
 * 位置：在类的上面，表示此类的对象，对象放在springmvc的容器中
 * @RequestMapping
 *      属性： value:表示所有请求地址的公共前缀，相当于是模块的名称
 *      位置： 在类的上面
 *              @RequestMapping(value = "/test")
 */
@Controller
@RequestMapping(value = "/test")
public class MyController {
 
    @RequestMapping(value = {"/some.do", "/first.do"})
    public ModelAndView doSome() { //相当于doGet()
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "处理了some.do请求");
        mv.addObject("fun", "执行了doSome方法");
        mv.setViewName("show");
        return mv;
    }

    }
}
```

##### 2.1.2 对请求提交方式的定义

#### 2.2 接收请求中的参数

只需要在控制器方法的形参列表中定义即可。框架会给参数赋值，在控制器方法内部可以直接使用request\response\session参数

- HttpServletRequest
- HttpServletRespose
- HttpSession
- 请求中所携带的请求参数

接收请求的参数：逐个接收，对象接收



400 ：http status，表示客户端异常，主要发生在用户提交参数过程中

##### 2.2.1 逐个接收：请求中的参数名和控制器方法的形参名一样

逐个接收：**请求中的参数名和控制器方法的形参名一样**。按照名称对应接收参数。

```java
/**
 * 逐个接收请求参数
 * 要求：请求中的参数名和控制器方法的形参名,按照名称对应接收请求参数
 * 参数接收：
 *  1.框架使用request对象，接收参数
 *    String name =request.getParameter("name");
 *    String     age =request.getParameter("age");
 *  2. 在中央调度器的内部调用doPropertyParam方法时，按名称对象传递参数
 *      doPropertyParam(name,Integer.valueof(age));
 *      框架可以实现请求参数String到int long float等类型的转换
 */
@RequestMapping(value = "/received-property.do")
public ModelAndView doPropertyParam(String name, Integer age){
    System.out.println("执行doPropertyParam");
    ModelAndView mv = new ModelAndView();
    mv.addObject("myname",name);
    mv.addObject("myage",age);
    mv.setViewName("property");
    return mv;
}
```

```jsp
<p>逐个接收请求参数</p>
<form action="received-property.do" method = "post">
    姓名：<input type="text" name="name"> <br/>
    年龄：<input type="text" name="age"> <br/>
    <input type="submit" value ="提交参数">
</form>
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>show</title>
</head>
<body>

<h3>myname数据: ${myname}</h3>
<h3>myage数据: ${myage}</h3>
</body>
</html>
```

**解决post请求中的乱码问题**：字符集过滤器 org.springframework.web.filter.CharacterEncodingFilter

```xml
<!--   配置写在web.xml中-->   
<!--  声明过滤器，框架提供，解决post请求中的乱码问题-->forceRequestEncoding
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <!--    给过滤器属性赋值-->
    <init-param>
      <param-name>encoding</param-name>
      <param-value>utf-8</param-value>
    </init-param>
    <!--强制请求对象request，使用encoding的编码方式    -->
    <init-param>
      <param-name>forceRequestEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
    <!--强制响应对象response，使用encoding的编码方式    -->
    <init-param>
      <param-name>forceResponseEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <!--强制所有请求，先经过过滤器处理    -->
    <url-pattern>/*</url-pattern>
  </filter-mapping>


```

##### 2.2.2逐个接收请求参数：请求参数名和控制器方法的形参名不一样

@RequestParam ：解决名称不一样问题： 

​				属性：value 请求中的参数名

​							required:boolean类型，默认是true：请求中必须有此参数，没有就报错

​				位置： 在形参定义的前面

```java

@RequestMapping(value = "/received-property.do")
    public ModelAndView doPropertyParam(@RequestParam(value = "rname") String name, @RequestParam (value ="rage")Integer age){
        System.out.println("执行doPropertyParam");
        ModelAndView mv = new ModelAndView();
        mv.addObject("myname",name);
        mv.addObject("myage",age);
        mv.setViewName("property");
        return mv;
    }
```



```xml
 <p>逐个接收请求参数</p>
    <form action="received-property.do" method = "post">
        姓名：<input type="text" rname="name"> <br/>
        年龄：<input type="text" rname="age"> <br/>
        <input type="submit" value ="提交参数">
    </form>
```

##### 2.2.3 对象接收参数

对象接收：在控制器方法的形参是JAVA对象，使用JAVA独享的属性接收请求中的参数。

​			要求：JAVA对象的属性名和请求中参数名一样 & JAVA类需要又一个无参数构造方法，属性有Set方法。

​			框架的处理：

​					1. 调用JAVA对象的无参数构造方法创建对象

                       2. 调用对象set方法，同名的参数，调用对应的set方法。如参数是name，调用setName(参数值)

```java
@RequestMapping("/receive-object.do")
    public ModelAndView doReceiveObject(Student student){
        ModelAndView mv = new ModelAndView();
        mv.addObject("myname",student.getName());
        mv.addObject("myage",student.getAge());
        mv.setViewName("show");
        return mv;
    }
```



#### 2.3 控制器方法的返回值

控制器方法的返回值表示本次请求的处理结果，返回值有ModelAndView、Stirng、void、Object

请求的处理结果包含数据和视图（仅数据、仅视图、数据和视图）

##### 2.3.1 ModelAndView数据和视图

请求的结果有数据和视图，使用ModelAndView最方便

​			数据：存放request作用域

​			视图：执行forward转发操作

##### 2.3.2 String 视图

框架对返回值是String，执行的是forward转发操作

视图可以表示为完整视图路径或逻辑视图名称

```java
@RequestMapping(value = "/string-return.do")
public String doStringView(HttpServletRequest request, String name, Integer age){
   //处理数据
  request.setAttribute("myname",name);
  request.setAttribute("myage",age);
  //返回结果，forward转发到show.jsp
  return "show";//逻辑视图名称，需要项目中配置视图解析器
  // return "/WEB-INF/view/show.jsp";//完整视图路径  
}
```

##### 2.3.3 void没有数据和视图

void：没有数据和视图，可以使用HttpServletResponse对象输出数据，响应ajax请求。

 ```java
 //后端控制器方法代码
 @RequestMapping("/return-void-ajax.do")
     public void returnVoidAjax(HttpServletResponse response, String name, Integer age) throws IOException {
         System.out.println("处理void返回类型，name= "+name+ ",age="+age);
         //调用service得到对象
         Student student = new Student();
         student.setName(name);
         student.setAge(age);
         //把对象转为json
         ObjectMapper om = new ObjectMapper();
         String json = om.writeValueAsString(student);
         System.out.println("服务器端对象转为json==="+json);
         //输出json，响应ajax
         response.setContentType("application/json;charset=utf-8");
         PrintWriter pw = response.getWriter();
         pw.println(json);
         pw.flush();
         pw.close();
     }
 ```



##### 2.3.4 自定义类型对象Object

返回Object表示数据，所以控制器方法返回对象Object，用来响应ajax请求。

返回对象Object，如List,Student,Map,String,Integer....这些都是数据，而ajax请求需要的就是数据。在ajax请求中，一般需要从服务器返回的是json格式的数据，经常处理JAVA对象到json的转换，而且还需要输出响应数据响应ajax请求。框架提供了处理JAVA对象到json的转换，还有数据输出工作。

##### 2.3.4.1 HttpMessageConverter消息转换器

 HttpMessageConverter接口，作用是 1）实现请求的数据转为JAVA对象 2）把控制器方法返回的对象转为json\sml\text\二进制等不同格式的数据。

<img src="C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220513203427288.png" alt="image-20220513203427288" style="zoom:50%;" />

```java
public interface HttpMessageConverter<T> {
    /*
     * 作用：检查clazz这个类型对象，能否转为mediaType表示的数据格式
     *       如果能转为mediaType表示的类型，返回true，返回true调用read()
     */
    boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);
	
     /**
     * 作用：接收请求中的数据，把数据转为clazz表示的对象     
     */
    T read(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException;
    
    //作用：检查clazz这种数据类型，能否转为mediatype表示的数据格式
    boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
	
    //作用：把t对象（控制器方法的返回值），按照contentType说明的格式，把对象转为json或者xml
    void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException;  
    
}
```

- mediaType：媒体类型，表示互联网中数据的格式。例如application/json,text,html,image/gif

- HttpMessageConverter 接口的实现类：

  - MappingJackson2HttpMessageConverter：使用jackson工具库中的ObjectMapper把JAVA对象转为json数据格式

  - StringHttpMessageConverter：把字符串类型的数据进行格式转换和编码

    - 如何使用实现类：框架根据控制器方法的返回类型，自动查找使用的实现类

    - ```java
      @RequestMapping(value = "/received-property.do")
          public Student doPropertyParam(@RequestParam(value = "rname") String name, @RequestParam (value ="rage")Integer age){
              System.out.println("执行doPropertyParam");
             	Student student = new Student();     
              return student;
          }
      ```

      在默认情况下，springmvc使用了HttpMessageConverter消息转换器接口的4个实现类。包括StringHttpMessageConverter、MappingJackson2HttpMessageConverter。需要在springmvc的配置文件中加入注解驱动的标签mvc:annotation-driven。加入这个标签后，springmvc项目启动后，会创建HttpMessageConverter接口的7个实现类对象，包括StringHttpMessageConverter、MappingJackson2HttpMessageConverter

##### 2.3.4.2 @ResponseBody

@ResponseBody注解的作用：把student转换后的json通过HttpServletResponse对象输出给浏览器

```java
response.setContentType("application/json;charset=utf-8");
PrintWriter pw = response.getWriter();
pw.println(json);
pw.flush();
pw.close();
@ResponseBody注解作用等价于上面代码的实现
```

##### 2.3.4.3 控制器方法返回对象转为json的步骤

1. pom.xml加入Jackson依赖，springmvc框架默认处理json就是使用Jackson
2. 在springmvc的配置文件中，加入注解驱动的标签mvc:annotation-driven
3. 在控制器方法的上面加入@ResponseBody注解，表示返回值是数据，输出到浏览器

##### 2.3.4.4 控制器方法返回Student--json

```java
@RequestMapping("/doStudentJson.do")
@ResponseBody
public Student doAjaxJson(String name,Integer age){
   Student student = new Student();
   student.setName(name);
   student.setAge(age);
   return student;
}
```

框架处理模式：

 1. 框架根据控制器方法的返回值类型，找到HttpMessageConverter接口的实现类

    最后找到的是MappingJackson2HttpMessageConverter

 2. 使用实现的MappingJackson2HttpMessageConverter。执行它的write()方法，把student对象转为json格式的数据

 3. 框架使用@ResponseBody注解，把2中的json输出给浏览器

    设置的Content-Type: application/json;charset=utf-8

##### 2.3.4.5 控制器方法返回List<Student> --json array

```java

    @RequestMapping("/doStudentJsonArray.do")
    @ResponseBody
    public List<Student> doAjaxJsonArray(String name, Integer age){

        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(18);

        Student student = new Student();
        student.setName("卓卓");
        student.setAge(20);
        List<Student> stus = new ArrayList<>();
        stus.add(student1);
        stus.add(student);
        return stus;
    }
```

##### 2.3.4.6 控制器方法返回String--数据

```java
@RequestMapping("/doStringData.do",produces = "text/plain;charset=utf-8")
@ResponseBody
public String doStringData(String name,Integer age){
    return "hello SpringMVC注解式开发";
}
```

* 区分返回值字符串String是数据还是视图
     *  1. 方法上面有@ResponseBody注解就是数据
     *  2. 方法上面有无@ResponseBody就是视图
* ajax中中文乱码，解决中文，需要使用@RequestMapping的produces属性，produces属性：指定content-type的值
* 框架如何处理String返回值：
     * 框架使用StringHttpMessageConverter
     * StringHttpMessageConverter使用的是text/plain;charset=ISO-8859-1

#### 2.4 静态资源处理

##### 2.4.1 tomcat的default servlet

静态资源（图片、html等）是由tomcat处理

```xml
<servlet>
        <servlet-name>default</servlet-name>
        <servlet-class>org.apache.catalina.servlets.DefaultServlet</servlet-class>
        <init-param>
            <param-name>debug</param-name>
            <param-value>0</param-value>
        </init-param>
        <init-param>
            <param-name>listings</param-name>
            <param-value>false</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
```

所有web应用程序的默认servlet，作用：1）提供静态资源的处理 2）它处理所有未映射到其他请求的请求处理

##### 2.4.2 中央调度器设置“/”

```xml
<url-pattern>/</url-pattern>
```

导致中央调度器成为了默认的default servlet。需要处理静态资源和其他未映射的请求。默认中央调度器没有处理静态资源的控制器对象，所以静态资源404。

如果项目中，中央调度器设置了“/”，动态资源能访问，静态资源不能访问，需要处理静态i资源的访问工作。

##### 2.4.3 第一种方式处理静态资源

在Springmvc配置文件中加入mvc:default-servlet-handler标签，springmvc框架会在项目运行时，加入DefaultServletHttpRequestHandler对象，让这个对象处理静态资源的访问。

```xml
<mvc:annotation-driven/>
<mvc:default-servlet-handler/>
```

原理：创建DefaultServletHttpRequestHandler对象处理静态资源。DefaultServletHttpRequestHandler对象把接收的静态资源的地址转发给tomcat的default

缺点：依赖于tomcat服务器提供的能力

default-servlet-handler和@RequestMapping使用有冲突，加上<mvc:annotation-driven/>

##### 2.4.4 第二种方式处理静态资源

在Springmvc配置文件中加入mvc:resources标签，框架会创建DefaultServletHttpRequestHandler控制器对象，使用这个对象处理静态资源的访问，不依赖tomcat服务器。

```xml
<mvc:resources mapping="" location=""/>

<mvc:resources mapping="/images/**" location="/images/"/>
<mvc:resources mapping="/html/**" location="/html/"/>
<mvc:resources mapping="/static/**" location="/static/"/>
```

- mapping：访问静态资源的url地址，可以使用通配符（**）。

​							**：表示任意的目录、目录+资源名称

- location：静态资源在项目中的位置，不要使用/WEB-INF目录

### 3. SSM整合开发

#### 3.1 SSM整合思路

- SSM整合的实现方式可分为两种：基于xml配置方式、基于注解方式
- SSM整合是使用三个框架的优势功能，三个框架对应的三层架构。
  - SpringMVC 视图层 + Spring 业务层 + Mybatis 持久层
- SSM整合需要把对象交给容器管理，让容器去创建项目中要使用的JAVA对象。现在有两个容器。
  - Spring容器：管理service和dao等对象，是业务层对象的容器
  - SpringMVC容器：管理控制器对象，是视图层对象的容器
- SSM整合就是把对象交给容器管理。两个容器共存。各自负责管理不同的对象。把对象声明到配置文件中，让两个容器创建对象。
  - Spring创建service、dao；SpringMVC创建controller

#### 3.2 容器的创建

Spring容器创建：在web.xml声明了监听器ContextLoaderListener，这个功能框架已写好。功能是创建spring的容器对象WebApplicationContext。在创建WebApplicationContext对象时，读取Spring的配置文件，遇到bean标签或者注解，就能创建service、dao等对象，放到容器中。

SpringMVC容器：在web.xml声明了中央调度器DispatchServlet。在这个servlet的init方法中，创建了容器对象WebApplicationContext，在创建WebApplicationContext对象时，读取springmvc配置文件，遇到@Controller注解，创建控制器对象，放到容器中。

```java
//内存中，创建对象
WebApplicationContext spring = new WebApplicationContext(); //spring --map(service,dao)
WebApplicationContext springmvc = new WebApplicationContext(); // springmvc -- map:(controller)
```

SpringMVC容器和Spring容器的关系：设计上MVC容器对象是Spring容器的子容器。

Spring是父容器，SpringMVC是子容器

<img src="C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220514232815086.png" alt="image-20220514232815086" style="zoom:33%;" />

#### 3.3 SSM整合开发的步骤

1.使用的student2表(id，name，age)
2.创建maven web项目
3.修改pom.xml加入依赖:spring，springmvc，mybatis，mybatis-springmysql驱动，druid，jackso
4.写webxml:声明容器对象
		1)声明spring的监听器ContextLoaderListener:创建spring的容器对象，创建service，dao对象
		2)声明springmvc的中央调度器DispatherServlet:创建springmvc容器对象，创建controller对象
		3)声明字符集的过滤器CharacterEncodingFilter，解决post请求乱码的问题
5.创建程序中的包，dao，service，controller，entity
6.写spring，springmvc，mybatis配置文件
7.写java代码，实体类，dao接口和mapper文件，service类，controller类。使用注解声明对象和赋值
8.创建视图文件，各种jsp



### 4. SpringMVC核心技术

#### 4.1 请求重定向和转发

- 当控制器对请求处理完毕后，向其他资源进行跳转时，有两种跳转方式：请求转发和重定向。根据要跳转的资源类型又分为 ：跳转到页面、跳转到其他控制器
- 对于请求转发的页面，可以是WEB-INF中页面；重定向的页面不能是WEB-INF中页面，因为重定向相当于用户再次发出一次请求，而用户是不能直接访问WEB-INF中资源

<img src="C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220515134330964.png" alt="image-20220515134330964" style="zoom: 67%;" />

- SpringMVC 框架把原来 Servlet 中的请求转发和重定向操作进行了封装。现在可以使用简单的方式实现转发和重定向。 
  - forward:表示转发，实现request.getRequestDispatcher("xx.jsp").forward() 
  - redirect:表示重定向，实现 response.sendRedirect("xxx.jsp") 

##### 4.1.1 请求转发

```java
/**
     * 控制器方法返回是ModelAndView实现转发forward
     * 语法：mv.setViewName("forward:视图完整路径")
     * forward特点：不和视图解析器一同工作，就当项目中没有视图解析器
     
     */
    @RequestMapping(value = "/doforward.do")
    public ModelAndView doForward(String name,Integer age) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("myname", name);
        mv.addObject("myage", age);
        //显示使用forward指定转发操作
        mv.setViewName("forward:/WEB-INF/view/show.jsp");
        return mv;
    }
```

##### 4.1.2 请求重定向

```java
/**
     * 控制器方法返回是ModelAndView实现重定向
     * 语法：mv.setViewName("redirect:视图完整路径")
     * forward特点：不和视图解析器一同工作，就当项目中没有视图解析器
     * 
     * 框架提供的重定向功能：
     	1. 框架可以实现两次请求之间的数据传递，把第一个请求中的model里面简单类型的数据转为字符串，并附加到目标页面的后面，做get参数传递
     	   可以在目标页面中获取参数值的使用
     	2. 在目标页面中，可以是使用${param.参数名}获取参数值  
    

     */
    @RequestMapping(value = "/doredirect.do")
    public ModelAndView doRedirect(String name,Integer age) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("myname", name);
        mv.addObject("myage", age);
        //显示使用forward指定转发操作
        // 重定向不能访问受保护的WEB-INF下面的资源
        //mv.setViewName("redirect:/WEB-INF/view/other.jsp");
        mv.setViewName("redirect:/other.jsp");
        return mv;
    }
```

#### 4.2 异常处理

- 框架使用的是集中的异常处理。把各个Controller中抛出的异常集中到一个地方处理。处理异常的叫做异常处理器
- 框架中使用两个注解完成异常的集中处理，这样每个controller不用单独处理异常

- SpringMVC框架处理异常的常用方式：
  - @ExceptionHandler：放在方法的上面，表示此方法可以处理某个类别的异常。当异常发生时，执行这个方法
  - @ControllerAdvice：表示当前类时异常的处理类，给controller增加功能的。位置：在类的上面。相当于AOP中的@Aspect。可以看做是控制器增强，就是给Controller类增强异常（切面）的处理功能。

```java
//不用ExceptionHandler
@RequestMapping(value = "/doredirect.do")
    public ModelAndView doRedirect(String name,Integer age) {
        try{
            ModelAndView mv = new ModelAndView();
            mv.addObject("myname", name);
            mv.addObject("myage", age);
            mv.setViewName("redirect:/other.jsp");                     
        }catch(Exception e){
            e.printStackTrace();            
        }catch(xxx ex){
                       
        }
        
         return mv;       
}
```

- 步骤：

1.新建web 应用

2.加入web依赖  spring-webmvc依赖（springmvc框架依赖）， servlet依赖。

3.声明springmvc核心对象DispatcherServlet.
  1)DispatcherServlet是一个Servlet对象。
  2)DispatcherServlet叫做前端控制器（front controller）
  3)DispatcherServlet作用：
     1.在servlet的init（）方法中，创建springmvc中的容器对象。
       WebApplicationContext ctx = new ClassPathXmlApplicationContext("appliationContext.xml")

​	 2.作为servlet，接收请求。

4.创建一个jsp，发起请求， 有参数name ，age

```
5.创建异常类MyUserException,
  子类 NameException , AgeException

6.创建一个普通的类，作为控制器使用（代替之前的servlet）
  1）在类的上面加入@Controller注解
  2）在类中定义方法. 根据请求参数name ，age的值抛出NameException ,AgeException
```

7.创建作为结果的jsp页面

```
8.创建一个普通类，做为异常的处理类。
  1）在类的上面加入@ControllerAdvice
  2) 在类中定义方法， 每个方法处理对应的异常。方法的上面加入@ExceptionHandler注解
```

9.创建处理异常的jsp页面。

10.创建springmvc的配置文件（spring的配置文件一样）
  1）声明组件扫描器，指定@Controller注解所在的包名
  2）声明视图解析器对象

  3）声明组件扫描器，找到@ControllerAdivce注解包名
  4）声明注解驱动

```xml
  <!--异常处理    -->
<context:component-scan base-package="com.handle"/>
<mvc:annotation-driven/>
```

#### 4.3 拦截器

拦截器是SpringMVC框架中的一种对象，需要实现接口HandlerInterceptor。用于拦截用户的请求，拦截到controller的请求。

作用：拦截用户的请求，可以预先对请求处理。根据处理结果，决定是否执行controller，也可以把多个controller中共用的功能定义到拦截器。

特点：

1. 拦截器可以分为系统拦截器和自定义拦截器
1. 一个项目可以有多个拦截器（0个或多个自定义拦截器）
1. 拦截器侧重拦截用户的请求
1. 拦截器实在请求处理之前先执行的

项目中拦截器的实现步骤：

1. 创建类实现拦截器接口HandlerInterceptor，实现接口中的方法（3个）
1. 在SpringMVC配置文件中，声明拦截器对象，并指定拦截的url地址

```java
public interface HandlerInterceptor {
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
```

#### 4.3.1 HandlerInterceptor 中的方法

##### 4.3.1.1 拦截器的第一个方法preHandle

```java
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
}
```

- preHandle：预先处理请求的方法
  - 参数：
    - Object handler：被拦截的控制器对象（MyController）
  - 返回值：
    - boolean类型
  
      - true：表示请求时正确，可以被controller处理的
  
        - 当preHandle返回true，执行结果：
  
          ```html
          ======MyInterceptor拦截器的preHandle=====
          执行了MyController的doSome方法
          ======MyInterceptor拦截器的postHandle=====
          ======MyInterceptor拦截器的afterCompletion=====
          请求的顺序：用户some.do -> preHandle ->doSome -> postHandle -> afterComplietion
          ```
  
      - false：表示请求不能被处理，控制方法不会执行，请求到此截止
- 特点：

  - 预处理方法它的执行时间：在控制器方法之前先执行的
  - 可以对请求处理，可以做登录检查，权限的判断、统计数据等等
  - 决定请求是否执行

##### 4.3.1.2 拦截器的第二个方法postHandle

```java
public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       
    }
```

```java
//对请求做二次处理
public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("======MyInterceptor拦截器的postHandle=====");
        //对请求做二次处理
        if(modelAndView !=null){
            //修改数据
            modelAndView.addObject("mydate",new Date());
            //修改视图
            modelAndView.setViewName("other");
        }

    }
```

- postHandle：后处理方法
  - 参数：
    - ModelAndView modelAndView  控制器方法的返回值（请求的执行结果）
- 特点：
  - 在控制器方法后执行
  - 能获取到控制器方法的执行结果。可以修改原来的执行结果；可以修改数据，也可以修改视图
  - 可以对请求做二次处理

##### 4.3.1.3 拦截器的第三个方法afterCompletion

```java
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       
    }
```

- afterCompletion：最后执行的方法
  - 参数：
    - Object handler 被拦截的控制器对象（MyController）
    - Exception ex：异常对象
- 特点：
  - 在请求处理完成后执行的。请求处理完成的标志是视图处理完成，对视图执行forward操作后
  - 可以做程序最后要做的工作，例如释放内存、清理临时变量。
  - 方法执行的条件：1）当前的拦截器preHandle()方法必须执行；2）preHandle()方法方法必须返回true

```java
//做请求的收尾工作
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    System.out.println("======MyInterceptor拦截器的afterCompletion=====");
    //获取数据
    HttpSession session  = request.getSession();
    Object attr = session.getAttribute("attr");
    System.out.println("attr:"+attr);
    //删除数据
    session.removeAttribute("attr");
    
    attr = session.getAttribute("attr");
    System.out.println("attr:"+attr);
}
```

#### 4.3.2 声明拦截器

```java
<!--声明拦截器    -->
    <mvc:interceptors>
    <!-- 声明第一个拦截器       -->
        <mvc:interceptor>
            <!-- 指定拦截器的拦截地址
                path：拦截的url,使用**通配符
                      例如： path="/user/**
                      http://localhost:8000/user/listUser.do
                       -->
            <mvc:mapping path="/**"/>
            <!--指定使用的拦截器            -->
            <bean class="com.handler.MyInterceptor"/> 
        </mvc:interceptor>
    </mvc:interceptors>
```

#### 4.3.3 多拦截器的执行顺序

- 对于有多个拦截器存在时，如果preHandle方法中返回的都为true，那么拦截器方法就会按：preHnadle顺序执行--》调用目标方法--》postHandle按反序执行--》渲染视图--》afterHandle按反序执行。
- 当有多个拦截器时，形成拦截器链。拦截器链的执行顺序，与其注册顺序一致。需要再次强调一点的是，当某一个拦截器的 preHandle()方法返回 true并被执行到时，会向一个专门的方法栈中放入该拦截器的 afterCompletion()方法。 
- 只要有一个 preHandle()方法返回 false，则上部的执行链将被断开，其后续的处理器方法与 postHandle()方法将无法执行。但，无论执行链执行情况怎样，只要方法栈中有方法，即执行链中只要有 preHandle()方法返回 true，就会执行方法栈中的 afterCompletion()方法。最终都会给出响应。
- 当任意一个拦截器的preHandler()方法的返回值为false时，拦截器的controller方法均不会被执行。

<img src="C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220518182400630.png" alt="image-20220518182400630" style="zoom: 50%;" />

#### 4.3.4 拦截器 vs. 过滤器

1）拦截器是SpringMVC框架中的对象，过滤器是servlet中的对象

2）拦截器对象时框架容器创建的，过滤器对象时tomcat创建的对象

3）拦截器是侧重对请求做验证处理，可以截断请求。过滤器是侧重对request，response对象的属性，参数设置值。例如request.setCharacterEncoding("utf-8")。

4）拦截器的执行时间有三个：控制器方法之前，之后，请求完成后。过滤器执行时间在请求之前。

5）拦截器是拦截对controller，动态资源的请求的。过滤器可以过滤所有动态、静态请求

6）拦截器和过滤器一起执行时，过滤器-->中央调度器（servlet）-->拦截器-->控制器方法







