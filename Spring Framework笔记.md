## Spring框架

### 1. Spring概述

https://spring.io/

Java轻量级的开源框架，使用Java语言开发，可以在j2se、j2ee项目中都可以使用。

Spring核心技术：Ioc AoP

Spring又叫做容器，主要装Java对象。可以<u>让Spring创建Java对象并给属性赋值</u>

Spring作用：实现解耦合，解决Java对象之间的耦合，解决模块之间的耦合

优点：轻量，针对接口编程、解耦合，AOP编程的支持，方便集成各种优秀框架

Spring体系结构：

![Spring的体系结构](http://c.biancheng.net/uploads/allimg/190606/5-1Z606104H1294.gif)

Tomcat也是容器，管理的是servlet，listener，filter等对象。创建HelloServlet类，写web.xml

spring的工作方式：

​		![image-20220502125807010](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220502125807010.png)

### 2. IoC控制反转

#### 2.1 概念

IoC,Inversion of Control：控制反转，一个理论，一个指导思想，指导开发人员如何使用对象，管理对象。把对象的创建、属性赋值，对象的声明周期都交给代码之外的容器管理。

1. **IoC分为控制和反转**

​	控制：对象创建，属性赋值，对象声明周期管理

​	反转：把开发人员管理对象的权限转移给代码之外的容器实现。由容器完成对象的管理。

​	正转：开发人员在代码中，使用new构造方法创建对象。开发人员掌握了对象的创建，属性赋值，对象从开始到销毁的全部过程。开发人员对对象全部控制。

通过容器，可以使用容器中的对象（容器已经创建了对象，对象属性赋值了，对象也组装好了）。

Spring就是一个容器，可以管理对象，创建对象，给属性赋值。

2. **IoC的技术实现**

   DI（依赖注入）：Dependency Injection，缩写是DI，是IoC的一种技术实现。程序只需要提供使用的对象的名称就可以了，对象如何创建，如何从容器中查找，获取都由容器内部自己实现。

   依赖：例如Class A类使用了Class B的属性或者方法，叫做Class A依赖Class B

3. **Spring框架使用DI实现IoC**

   通过Spring框架，只需要提供要使用的对象名称就可以了，从容器中获取名称对应的对象。

   Spring底层使用的事反射机制，通过反射创建对象，给属性赋值。

#### 2.2 Spring的配置文件

```xml
使用sprinng:spring作为容器来管理对象，开发人员从spring中获取要使用的对象
实现步骤：
1. 新鲜maven项目
2. 加入依赖，修改pom.xml
    spring-context:spring依赖
    junit:单元测试
3. 开发人员定义类：接口和实现类
   类也可没有接口
   接口和实现类定义：和没有spring一样
4. 创建spring的配置文件。作用：声明对象。
    把对象交给spring创建和管理。
    使用标签<bean>表示对象声明，一个bean表示一个JAVA对象
5. 使用容器中的对象。
    创建一个表示spring容器的对象 ApplicationContext
    从容器中，根据名称获取对象，使用getBean(“对象名称”)
```

```xml
<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>5.3.18</version>
</dependency>
```

```xml
<!--spring配置文件-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!--声明对象
        id :自定义对象名称，唯一,可以没有，spring可以提供默认的
        class:类的全限定名称，spring通过反射机制创建对象，不能是接口
        spring根据id,class创建对象，把对象放入到spring的一个map对象
        map.put(id,对象)
        -->
        <bean id="someService"  class="com.service.Impl.SomeServiceImpl"/>

</beans>
```

spring标准的配置文件：

1. 根标签beans

2. beans后面的事约束文件的说明
3. bean里面是bean声明
4. 什么是bean：spring管理的Java对象叫做bean

```java
//创建spring容器对象
public class AppMain {
    public static void main(String []args) {
//        SomeService service = new SomeServiceImpl();
//        service.doSome();
        // 1. 指定spring配置文件，从类路径(Class Path)之下开始的路径
        String config = "beans.xml";
        // 2. 创建容器对象 ApplicationContext表示spring容器对象
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        // 3. 从容器获取指定名称的对象，使用getBean("id")
        SomeService someService = (SomeService) ctx.getBean("someService");
        // 4. 调用对象的方法
        someService.doSome();
    }
}
```

Spring默认使用的无参构造方法，创建对象。如果定义了有参构造方法，应再定义无参构造方法。

Spring是在什么时候创建对象：创建Spring容器对象的时候，会读取配置文件，创建文件中声明Java对象。其优点使获取对象的速度快，因为对象已经创建好了。其缺点是比较占内存。

Spring容器创建对象，一次创建几个：在创建容器对象时，会把配置文件中的所有对象都创建出来（spring的默认规则）

#### 2.3 Spring容器创建对象的特点

1. 容器对象ApplicationContext：接口

   通过ApplicationContext对象，获取要使用的其他Java对象，执行getBean("<bean>的id")

2. Spring默认调用类的无参数构造方法，创建对象
3. Spring读取配置文件，一次创建好所有的Java对象，都放到map中。

##### 2.3.1获取容器中对象的信息

```java
String config = "beans.xml";
ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
//获取容器中定义对象的数量
int nums = ctx.getBeanDefinitionCount();
System.out.println("容器中定义对象的数量：" + nums);
//获取容器中定义的对象名称
String names[] = ctx.getBeanDefinitionNames();
for(String name:names){
    System.out.println("容器中对象的名称： "+name);
}
```

##### 2.3.2 创建非自定义类对象

```xml
<!--创建非自定义对象  -->
<bean id="mydate" class="java.util.Date"/>
```

##### 2.3.3 没有实现接口的类创建对象

```xml
<bean id="otherService" class="com.service.otherService"/>
```

#### 2.4 DI：给属性赋值

Spring调用类的五参数构造方法，创建对象。对象创建后给属性赋值。

给属性赋值可以使用：xml配置文件中的标签和属性；使用注解

DI的分类：1. set注入 ；2.构造注入

##### 2.4.1 基于XML的依赖注入DI

在XML配置文件中使用标签和属性，完成对象的创建，属性赋值。

1）set注入

概念：spring调用类中的set方法，在set方法中可以完成属性赋值。（推荐使用）

 ```xml
  <!--声明bean
         DI:给属性赋值
         简单类型：Java中的基本数据类型和String
           1. set注入，spring调用类的set方法，通过set方法完成属性赋值
           简单类型的set注入：
           语法：<bean id = "xxx" class = "yyy">
                 <property> name ="属性名1" value="简单类型的属性值"/>
                  <property> name ="属性名2" value="简单类型的属性值"/>
 
           </bean>
     -->
     <bean id="myStudent" class="com.ba01.Student">
         <property name="name" value="卓卓"/>
         <property name="age" value="18"/>
 
     </bean>
 ```

给属性赋值看的是set方法，和属性名无关。

给非自定义类属性赋值

```xml
<!--声明日期类-->
<bean id = "mydate" class="java.util.Date">
    <property name="time" value="2344"   <!--setTime(...)-->
</bean>
```

声明一个引用类型

```xml
<!--      
2. set注入
            引用类型
            语法：
               <bean id="xxx" class = "yyy">
                    <>property name="属性名" ref = "bean的id"/>
                </bean>
-->

    <bean id="myStudent" class="com.ba01.Student">
        <property name="name" value="卓卓"/>
        <property name="age" value="18"/>
        <property name="school" ref="myschool"/> <!--setSchool(myschool)-->

    </bean>

    <bean id ="myshcoll" class="com.ba01.School">
        <property name="name" value="beijing"/>
        <property name="address" value="haidianqu"/>
    </bean>
```

2）构造注入

```xml
<!--声明bean
       构造注入： Spring调用类的有参数构造方法，创建对象同时给属性赋值
       语法：
        <bean id="xxx" class ="yyy">
            <constructor-arg> ：表示一个构造方法的形参
            标签有属性：name:构造方法形参名，
                      index：构造方法的参数位置，构造方法参数从左往右位置是0,1,2。。。。
                      value:简单类型的形参值
                      ref：引用类型的形参值
        </bean>

    -->

    <!--构造注入-->
    <bean id="myStudent" class="com.ba01.Student">
        <constructor-arg name="myname" value="huihui"/>
        <constructor-arg name="myage" value="22"/>
        <constructor-arg name="mySchool" ref="mySchool"/>
    </bean>
```

3）引用类型的自动注入

概念：Spring可以根据某些规则给引用类型完成赋值。只对引用类型有效。规则有byName,byType。

​    a. byName（按名称注入）：Java类中引用类型属性名称和Spring容器中bean的id名称一样，且数据类型也是一样的。这样的bean能够赋值给引用类型

```xml
语法：
<bean id="xxx" class="yyy" autowire = "byType">
    简单类型属性赋值
</bean>  
```

```xml
	<bean id="myStudent" class="com.ba01.Student" autowire="byName">
        <property name="name" value="HUIHUI"/>
        <property name="age" value="10"/>

     </bean>

    <bean id="school" class="com.ba01.School">
        <property name="name" value="CQU"/>
        <property name="address" value="CQ"/>
    </bean>
```

b. byType（按类型注入）：Java类中引用类型的数据类型和Spring容器中bean的class值是同源的，这样的bean赋值给引用类型。

​	同源关系：1）Java中引用类型的数据类型和bean的class值是一样的 ；2）Java中引用类型的数据类型(父)和bean的class（子）值是父子类关系；Java中引用类型的数据类型和bean的class值是接口和实现类关系。

```xml
语法：
<bean id="xxx" class="yyy" autowire = "byType">
    简单类型属性赋值
</bean>  
```

```xml
	<bean id="myStudent" class="com.ba01.Student" autowire="byType">
        <property name="name" value="HUIHUI"/>
        <property name="age" value="10"/>

     </bean>

    <bean id="school" class="com.ba01.School">
        <property name="name" value="CQU"/>
        <property name="address" value="CQ"/>
    </bean>
```

5）项目中使用多个Spring配置文件

分多个配置文件的方式：1）按功能模块分，一个模块一个配置文件。2）按类的功能分，数据库操作相关的类在一个文件，service类在一个配置文件，配置redis、事务等等的一个配置文件。

Spring管理多个配置文件：常用的事包含关系的配置文件。项目中有一个总的文件，里面是有import标签包含其他的多个配置文件。

语法：

```xml
总的文件（xml）
<import resource="classpath:其他的文件路径1"/>
<import resource="classpath:其他的文件路径2"/>

关键字"classpath"：表示类路径，也就是类文件（class文件）所在目录。spring到类路径中加载文件
				什么时候使用classpath:在一个文件中要使用其他的文件，需要使用classpath
```

##### 2.4.2 基于注解的依赖注入DI

使用Spring提供的注解，完成Java对象的创建，属性赋值。

注解使用的核心步骤：

 1. 在源代码加入注解

    ```java
    @Component(value = "myStudent") :表示创建对象，对象放到容器中。作用是<bean>
        	属性：value 表示对象的名称，也就是bean的id属性值。若没有提供自定义对象名称，使用框架的默认对象名称（类名首字母小写）
        	使用位置：在类的上面，表示创建此类的对象
        	等价于 <bean id = "myStudent" class = "com.Student"/>
    ```

    

	1. 在spring配置文件，加入组件扫描器标签

```xml
 <!--声明组件扫描器：使用注解必须加入这个语句-->
        <context:component-scan base-package="com.ba01"/>
component-scan :组件扫描器，组件是Java对象
			属性：base-package 注解在你的项目中的包名
框架会扫描这个包和子包中的所有类，找类中的所有注解。遇到注解后，按照注解表示的功能，去创建对象，给属性赋值
```

**创建对象的4个注解**：

1. @Component：普通Java对象。在不属于后3个角色的时候，使用此注解创建对象最合适
2. @Repository：dao对象。放在dao接口的实现类上面，表示创建<u>dao对象</u>，<u>持久层对象</u>，能访问数据库
3. @Service：<u>service对象</u>。放在业务层接口的实现类上面，表示创建<u>业务层对象</u>，业务层对象具有事务能力
4. @Controller：控制器对象。放在控制器类上面，表示创建<u>控制器对</u>象。属于表示层对象视图层对象）。控制器对象能接受请求，把请求的处理结果显示给用户。

以上4个注解都能创建对象，但是后3个语句具有角色的说明，表示对象是分层的，对象时属于不同层的，具有额外的功能。

**扫描多个包的3中方式：**

1. 使用多的组件扫描器

   ```xml
   <context:component-scan base-package="com.ba01"/>
   <context:component-scan base-package="com.ba02"/>
   <context:component-scan base-package="com.ba03"/>
   ```

2. 使用分隔符（;或,）指定多个包

   ```xml
   <context:component-scan base-package="com.ba01;com.ba02;com.ba03"/>
   ```

3. 指定父包

   ```xml
   <context:component-scan base-package="com"/>
   ```

**简单属性赋值**：

```java
@Value: 简单类型属性赋值
    属性：value简单类型属性值
    位置：1）在属性定义的上面，无需set方法，推荐使用
    	 2）在set方法上面
    @Value(value="辉辉")
    private String name;
	@Value(value="12")
	int age;
```

**使用外部属性配置文件：**

```xml
<!--读取外部属性文件
	property-placeholder：读取properties这样的文件
-->
<context:property-placeholder location="classpath:/myconf:properties"/>                            
```

```java
@Value("${myname}")
private String name;
@Value("${myage}")
int age;
```

**引用类型赋值：**

1. @Autowired：spring框架提供给引用类型赋值，使用自动注入原理。支持byName，byType（默认）

   使用位置：1）属性定义的上面，无需set方法；2）在set方法的上面

   属性：required：boolean类型的属性，默认是true

   ​			true：spring在启动的时候，创建容器对象的时候，会检查引用类型是否赋值成果，如果赋值失败终止程序执行，并报错。

   ​			false：引用类型失败，程序正常执行，不报错，引用类型的值是null。

   byType：@Autowired

​		byName：@Autowired + @Qualifier(value="myshcool")

2. @Resource：来自JDK中，给引用类型赋值，支持byName（默认）、byType。

   ​						Spring支持这个注解的使用。

   ​						位置：1）在属性定义的上面，无需set方法；2）在set方法上面。

   ​						先使用byName赋值，如果赋值失败，再使用byType。

   ​						使用JDK1.8带有此注解，高于1.8没有此注解，需要加入一个依赖Javax Annotation API

   ​						只使用byName赋值规则：使用@Resource注解属性name. @Resource(name="对象名称")

### 3. 面向切面编程AOP（3-40）

#### 3.0 不适用AOP，增加功能导致的问题

```java
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("业务方法doSome,计算商品的购买金额");
    }
    @Override
    public void doOther() {
        System.out.println("业务方法doOther,消减库存");
    }
}
```

在源代码中，业务service方法中增加的功能。

 	1. 源代码可能改动的比较多；
 	2. 重复代码比较多；
 	3. 代码难于维护

```java
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        //在业务方法开始时，记录时间
        System.out.println("日志功能：记录方法的执行时间"+new Date());
        System.out.println("业务方法doSome,计算商品的购买金额");
        //在业务方法之后，提交事务
        System.out.println("事务功能：提交事务处理");
    }
    @Override
    public void doOther() {
        //在业务方法开始时，记录时间
        System.out.println("日志功能：记录方法的执行时间"+new Date());
        System.out.println("业务方法doOther,消减库存");
        //在业务方法之后，提交事务
        System.out.println("事务功能：提交事务处理");
    }
}
```

```java
//调用ServiceProxy类方法时候，调用真正的目标方法。
// 调用目标的方法时候，增加一些功能
//ServiceProxy叫做代理，代理对目标的操作
// 创建代理，可以完成对目标方法的调用，增减功能，保持目标方法内容不变
public class ServiceProxy implements SomeService {
	
    //真正的目标
    SomeService target = new SomeServiceImpl();
    @Override
    public void doSome() {
        System.out.println("日志功能：记录调用方法的时"+ new Date());
        target.doSome();
        System.out.println("事务功能：提交事务");

    }
    @Override
    public void doOther() {
        System.out.println("日志功能：记录调用方法的时"+ new Date());
        target.doOther();
        System.out.println("事务功能:提交事务");
    }
}
```

#### 3.1 AOP概念

AOP（Aspect Orient Programming）面向切面编程

Aspect：表示切面，给业务方法增加的功能，叫做切面。切面一般都是一个非业务功能，而且切面功能一般都是可以复用的，例如日志功能、事务功能、权限检查、参数检查、统计信息等等。

<u>如何理解AOP</u>：以切面为核心设计开发你的应用

​	1）设计项目时，找出切面功能；

​	2）安排切面的执行时间和执行的位置；

#### 3.2 AOP作用

1. 让切面功能复用
2. 让开发人员专注业务逻辑，提高开发效率
3. 实现业务功能和非业务功能的解耦合
4. 给存在的业务方法，增加功能，不用修改原来的代码

#### 3.3 AOP中的术语

1. Aspect：切面，给业务方法增加的功能
2. JoinPoint：连接点，连接切面的业务方法，在这个业务方法执行时，会同时执行切面的功能
3. Pointcut：切入点，是一个或多个连接点集合。表示这些方法执行时，都能增加切面的功能。表示切面执行的位置。
4. Target：目标对象，给哪个对象增加切面功能，这个对象就是目标对象。
5. Advice：通知（增强），表示切面的执行时间。在目标方法之前执行切面，还是目标方法之后执行切面。

AOP中重要的三个要素：Aspect（等价于事件）    Pointcut（等价于地点）    Advice（等价于时间） 在Advice的时间，在Pointcut的位置，执行Aspect。

AOP是一个动态思想，在程序运行期间，创建代理（ServiceProxy），使用代理执行方法时，增加切面的功能。这个代理对象是存在内存中。

#### 3.4 什么时候使用AOP

给某些方法增加相同的一些功能。源代码不能改时；给业务方法增加非业务功能时。

#### 3.5 AOP技术思想的实现

使用框架实现AOP。实现AOP的框架很多，如：

1. Spring框架实现AOP思想的部分功能，但实现AOP的操作比较繁琐、笨重。
2. Aspectj：独立框架，专门是做AOP的，属于Eclipse

#### 3.6 使用AspectJ框架实现AOP

##### 3.6.1 通知

AspectJ表示切面执行时间、用的通知（Advice）。这个通知可以使用注解表示。

5个注解，表示切面的5个执行时间，这些注解叫做通知注解。

- @Before：前置通知
- @AfterReturning：后置通知
- @Around：环绕通知
- @AfterThrowing：异常通知
- @After:最终通知

##### 3.6.2 Pointcut位置

Pointcut用来表示切面执行的位置，使用AspectJ中切入表达式

切入点表达式语法：execution(方法的定义)

```java
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)
execution(访问权限 方法返回值 方法声明(参数) 异常类型)    
```

- 其中带 `?`号的 `modifiers-pattern?`，`declaring-type-pattern?`，`throws-pattern?`是可选项
- `ret-type-pattern`,`name-pattern`, `parameters-pattern`是必选项
- `modifier-pattern?` 修饰符匹配，如public 表示匹配公有方法，`*`表示任意修饰符【访问权限类型】
- `ret-type-pattern` 返回值匹配，`*` 表示任何返回值，全路径的类名等【返回值类型】
- `declaring-type-pattern?` 类路径匹配【包名类名】
- `name-pattern` 方法名匹配，`*` 代表所有，`xx*`代表以xx开头的所有方法【方法名(参数类型和参数个数)】
- `(param-pattern)` 参数匹配，指定方法参数(声明的类型)，`(..)`代表所有参数，`(*,String)`代表第一个参数为任何值,第二个为String类型，`(..,String)`代表最后一个参数是String类型
- `throws-pattern?` 异常类型匹配【抛出异常类型】

```java
例子：
exexution(public void com.yaop.proxy.ServiceProxy.doSome(String,Integer) )
```

##### 3.6.3 @Before前置通知

```java
public interface SomeService {
    void doSome(String name, Integer age);
}
```

```java
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome(String name, Integer age) {
        System.out.println("业务方法doSome()，创建商品的订单");

    }
}
```

```xml
 <!--声明目标对象-->
    <bean id="someService"  class="com.yaop.service.impl.SomeServiceImpl"/>

    <!--声明切面类对象-->
    <bean id="myAspect" class="handle.MyAspect"/>

    <!--声明自动代理生成器，目的是创建目标对象的代理

        调用aspectJ框架中的功能，寻找spring容器中的所有目标对象
        把每个目标对象加入切面类中的功能，生成代理
        这个代理对象是修改的内存中的目标对象，这个目标对象就是代理对象（ServiceProxy）

    -->
    <aop:aspectj-autoproxy/>
```

```java
@Aspect
public class MyAspect {
    //定义方法：表示切面的具体功能
    /*
    * 前置通知方法的定义
    *  1)方法是spring
    *  2)方法是void
    *  3）方法名称自定义
    *  4）方法可以有参数，如果有是JoinPoint,也可以没有
    * */

    /**
     * @Before ：前置通知
     *          属性：value 切入点表达式，表示切面的执行位置。在这个方法执行时会同时执行切面的功能
     *          位置：在方法的上面
     *          特点： 1）执行时间：在目标方法之前执行
     *                2）不会影响目标方法的执行
     *                3）不会修改目标方法的执行结果
     * **/
    @Before(value = "execution(public void com.yaop.service.impl.SomeServiceImpl.doSome(String,Integer))")
    public void myBefore(){
        //切面代码
        System.out.println("前置通知：切面的功能，在目标方法之前先执行: " + new Date());
    }

    /**
     * 切面类中的通知方法，可以有参数
     * JoinPoint必须是它
     * JoinPoint：表示正在执行的业务方法，想到雨反射中的Method
     *          使用要求：必须是参数列表的第一个
     *          作用：获取方法执行时的信息，例如方法的名称和参数集合
     * **/

    @Before(value = "execution(public void com.yaop.service.impl.SomeServiceImpl.doSome(String,Integer))")
    public void myBefore2(JoinPoint jp){
        // 获取方法的定义
        System.out.println("前置通知中，获取目标方法的定义："+ jp.getSignature());
        System.out.println("前置通知中，获取方法的名称：" + jp.getSignature().getName());

        //切面代码
        System.out.println("前置通知：切面的功能，在目标方法之前先执行: " + new Date());
    }
}
```

```java
//应用aspectJ框架实现AOP时，框架代生成如下代码
public class ServiceProxy implements SomeService {

    //真正的目标
    MyAspect aspect = new MyAspect();
    SomeService target = new SomeServiceImpl();
    @Override
    public void doSome(String name, Integer age) {
        aspect.myBefore();
        target.doSome("hui",10);

    }
}
```

```java
public class MyTest {

    @Test
    public void  test01(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        SomeService someService = (SomeService) ctx.getBean("someService");
        //someService==com.sun.proxy.$Proxy7
        //加入代理的处理： 1）目标方法执行时，有切面功能 2）someService对象是改变后的代理对象com.sun.proxy.$Proxy7
        System.out.println("someService==" + someService.getClass().getName());
        //代理对象，调用方法，才有切面的功能增强
        someService.doSome("huihui",10);
    }

    @Test
    public void test02(){
        ServiceProxy service = new ServiceProxy();
        service.doSome("huizhuo",28);
    }

    @Test
    public void  test03(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        SomeService someService = (SomeService) ctx.getBean("someService");
        someService.doSome("huihui",10);
    }
}
```

##### 3.6.4 后置通知@AfterReturning

##### @AfterReturning：在目标方法之后执行

```java
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome(String name, Integer age) {
        System.out.println("业务方法doSome()，创建商品的订单");
    }

    @Override
    public String doOther(String name, Integer age) {
        System.out.println("执行业务方法doOther,处理库存");
        return "abcd";
    }
}
```

```java
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MyAspect {
    // 定义方法，表示 切面的具体功能
    /**后置通知方法的定义
     * 1) public
     * 2) void
     * 3) 方法名称自定义
     * 4）方法可以有参数，推荐使用Object类型
     * **/

    /**
     * @AfterReturnning：后置通知 属性：value 切入点表达式
     * returning 自定义的变量，表示目标方法的返回值
     * 自定义变量名称必须和通知方法的形参名一样
     * 位置：在方法的上面
     * 特点：
     * 1. 在目标方法之后，执行的
     * 2. 能获取到目标方法的执行结果
     * 3. 慧影响目标方法的执行
     * 方法的参数：
     * Object res: 表示目标方法的返回值，使用res接收doOther的调用结果。
     * Object res = doOther();
     * 后置通知的执行顺序
     * Object res = SomeServiceImpl.doOther(..);
     * myAfterReturning(res);
     * JoinPoint必须是第一个参数
     * 思考：
     * 1. doOther方法返回是String,Integer,Long等基本类型，在后置通知中，修改返回值，是不会影响目标方法的最后调用结果的
     * 2. doOther返回的结果是对象类型，例如Student，在后台通知方法中，修改这个Student对象的属性值会不会影响最后调用结果？
     **/
    @AfterReturning(value = "execution(* *..SomeServiceImpl.do*(..))", returning = "res")
    public void myAfterReturnning(JoinPoint jp, Object res) {
        if (res != null) {
            res = "Hello AspectJ";
        }
        System.out.println("后置通知，在目标方法之后执行，能拿到执行结果：" + res);
        // Object res有什么用
        if ("abcd".equals(res)) {
            System.out.println("根据返回值的不同，做不同的增强功能");
        } else if ("add".equals(res)) {
            System.out.println("doOther做了添加数据库，我做了备份数据");

        }

    }
}
```

```java
public class MyTest {

    @Test
    public  void  test01(){
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        SomeService someService = (SomeService) ctx.getBean("someService");
        String ret = someService.doOther("hui",10);
        System.out.println("ret:"+ret);
    }
}
```

##### 3.6.5 @Around环绕通知

@Around(value="切入点表达式")

使用环绕通知时，就是调用切面类中的通知方法.

前置通知+后置通知！=环绕通知

```java
@Aspect
public class MyAspect {


    //定义方法，表示切面的具体功能
    /*环绕通知方法的定义
        1)方法是public
        2)方法必须优返回值，推荐使用Object类型
        3)方法名称自定义
        4)方法必须有ProceedingJoinPoint参数
    * */

    /**
     * @Around：环绕通知 属性：value 切入点表达式
     * 位置：在方法定义的上面
     * <p>
     * 返回值： Object，表示调用目标方法希望得到的执行结果（不一定是目标方法自己的返回值）
     * 参数：ProceedingJoinPoint，相当于反射中的Method，作用：执行目标方法是等于Method.invoke()
     * 特点：
     * 1. 在目标方法的迁和后都能增强功能
     * 2. 控制目标是否执行
     * 3. 修改目标方法的执行结果。
     */
    @Around(value = "execution(* *..SomeServiceImpl.doFirst(..))")
    public Object myAround(ProceedingJoinPoint pjp) throws Throwable {
        //获取方法执行时的参数值
        String name = ""
        Object args[] = pjp.getArgs();
        if (args != null && args.length > 0) {
            Object arg = args[0];
            if (arg != null) {
                name = (String) arg;
            }

        }
        Object methodReturn = null;
        System.out.println("执行环绕通知，在目标方法之前输出日志事件==" + new Date());
        //执行目标方法，ProceedingJoinPoint,表示doFirst
        if ("huihui".equals(name)) {
            methodReturn = pjp.proceed();//method.invoke() 表示执行doFirst方法本身

        }
        if (methodReturn != null) {
            methodReturn = "环绕通知中，修改目标方法原来的执行结果";
        }
        System.out.println("环绕通知，在目标方法之后，增加了事务提交功能" + new Date());
        //return "Hello Around，不是目标方法的执行结果";

        return methodReturn;

    }
}
```

##### 3.6.6 @AfterThrowing异常通知

语法：@AfterThrowing(value ="切入点表达式"，throwing="自定义变量")

```java
@Aspect
public class MyAspect {
    /*
    异常通知方法的定义
      1. public 2. void 3. 方法名称自定义 4. 有参数是Execption
     */

    /**
     * @AfterThrowing：异常通知 属性 value 切入点表达式
     * throwing 自定义变量，表示目标方法抛出的异常
     * 变量名必须和通知方法的形参名一样
     * 位置：方法上面
     * 特点：
     * 1. 在目标方法抛出异常后执行的，没有异常不执行
     * 2. 能够获取目标方案的异常信息
     * 3. 不是异常处理程序，可以得到异常发生的通知，可以发送邮件、短信通知开发人员
     * 看做目标方法的监控程序
     * 异常通知的执行
     * <p>
     * try{
     * SomeServiceImpl.doSecond(...)
     * }catch(Exection e){
     * myAfterThrowing(Exception ex)
     * }
     */
    @AfterThrowing(value = "execution(* *..SomeServiceImpl.doFirst(..))", throwing = "ex")
    public void myAfterThrowing(Exception ex) {
        System.out.println("异常通知，在目标方法抛出异常时执行，异常原因是：" + ex.getMessage());
        /*
            异常发生可以做：
                1.记录异常的时间，位置等信息
                2.发送邮件、短信通知开发人员
         */
    }
}
```

##### 3.6.7 @After最终通知

语法：@After(value = "切入点表达式" )

```java
@Aspect
public class MyAspect {
    //异常通知方法的定义
    // 1. public 2. void 3. 方法名称自定义 4. 没有形参
    /*
    @After：最终通知
            属性：value 切入点表达式
            位置：在方法的上面
    特点：
        1. 在目标方法之后执行
        2. 总是会被执行,抛出异常后也会执行
        3. 可以用来做程序的收尾工作，例如清除临时数据、变量，清理内存
     try{
        SomeServiceImpl.doSecond(...)
     }catch(Exection e){
            myAfterThrowing(Exception ex)
     }finally{
        myAfter()
     }

     */
    @After(value = "execution(* *..SomeServiceImpl.doThird(..))")
    public void myAfter(){
        System.out.println("最终通知，总是会被执行的");
    }
}
```

##### 3.6.8 @Pointcut定义切入点

```java
 /*
        @Pointcut：定义和管理切入点，不是通知注解
                属性：value 切入点表达式
                位置：在自定义方法的上面，这个方法看做是切入点表达式的别名
                     其他的通知注解中，可以使用方法名称，就表示使用这个切入点表达式了                  
                
     */
    @Pointcut(value = "execution(* *..SomeServiceImpl.doThird(..))")
    private void mypt(){
        //无需代码
    }

    @After(value = "mypt()")
    public void myAfter(){
        System.out.println("最终通知，总是会被执行的");
    }
```

#### 3.7 AOP总结

​	AOP是一种动态的技术思想，目的是实现业务功能和非业务功能的解耦合。业务功能是独立的模块，其他功能也是独立的模块。例如事务功能、日志等。让这些事务、日志功能可以被复用。

​	当目标方法需要一些功能时，可以在不修改，不能修改源代码的情况下，使用AOP技术在程序执行期间，生成代理对象，通过代理执行业务方法，同时增加功能。

### 4. Spring集成MyBatis框架

#### 4.1 集成思路

Spring能集成很多框架。通过集成功能，让开发人员使用其他框架更方便。

集成使用的是Spring的IOC核心技术。

#### 4.2 要使用MyBatis，如何使用

使用MyBatis，需要创建MyBatis框架中的某些对象，使用这些对象就能使用MyBatis提供的功能。

分析：MyBatis执行SQL语句，需要使用哪些对象。

  1. 需要dao接口的代理对象，例如StudentDao，需要一个它的代理对象

     使用SqlSession.getMapper(StudentDao.class)，得到dao代理对象

	2. 需要SqlSessionFactory，创建SqlSessionFactory对象，才能使用openSession()得到SqlSession对象
	2. 数据源DataSource对象，使用一个更强大、功能更多的连接池对象代替MyBatis的PooledDataSource

![image-20220506173732225](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220506173732225.png)

#### 4.3 集成的步骤

```xml
spring集成mybatis

实现步骤：
    1.使用mysql库，使用学生表 student2(id int 主键列，自动增长，
                                    name varchar(80),
                                    age int)
    2. 创建maven项目
    3. 加入依赖
        spring依赖，mybatis依赖，mysql驱动，junit依赖
        mybatis-spring依赖（mybatis网站上提供，用来spring项目中，创建mybatis对象）
        spring有关事务的依赖

        mybatis和spring整合的时候，事务是自动提交的

    4.  创建实体类Student
    5.  创建Dao接口和mapper文件写sql
    6.  写mybatis主配置文件
    7.  创建service接口和其他实现类
    8.  创建spring的配置文件
        1) 声明数据源DataSource，使用阿里的Druid连接池
        2) 声明SqlSessionFactoryBean类，在这个类内部创建是SqlSessionFactory对象
        3) 声明MapperScannerConfigurer类，在内部创建dao代理对象，
           创建的对象都在spring容器中
        4) 声明service对象，把3)中的dao赋值给service属性
    9.  测试dao访问数据库
```

https://github.com/yao07/ssm_tutorial/tree/main/AOP-homework

### 5. Spring事务

#### 5.1 事务的概念

事务是一些sql序列的集合，多条SQL语句作为一个整体来执行。要么执行都成功，要么执行都失败。

```xml
mysql执行事务
beginTransaction 事务开启
insert into student() values....
select * from student where id=1001
update school set name=xxx where id = 1005
endTransaction 事务结束
```

什么情况下需要使用事务？一个操作需要2条或2条以上的SQL语句一起完成，操作才能成功。

#### 5.2 在程序中事务在哪里说明

事务加在业务类的方法上面（public方法上面），表示业务方法执行时，需要事务的支持。

```java
public class AccountService{
    private AccountDao dao;
    private MoneyDao dao2;
    //在service（业务类）的public方法上面，需要说明事务
	public void trans(String a, String b, Integer money){
        dao.updateA();
        dao.updateB();
        dao2.insertA();
    }
}

public class AccountDao{
    public void updateA(){   }
    public void updateB(){   }
}

public class MoneyDao{
    public void insertA(){}
    public void insertB(){}
}
```

#### 5.3 事务管理器接口

##### 5.3.1 不同的数据库访问技术，处理事务是不同的

1）使用jdbc访问数据库，事务处理

```java
public void updateAccount(){
    Connection conn = ..;
    conn.setAutoCommit(false);
    stat.insert();
    stat.update();
    stat.commit();
    con.setAutoCommit(true);
}
```

2)MyBatis执行数据库，处理事务

```java
public void updateAccount(){
    try{
        SqlSession session = SqlSession.openSession(false);
        session.insert();
        session.update();
        session.commit();
    }catch(Exception e){
        session.rollback();
    }  
}
```

##### 5.3.2 Spring统一管理事务，把不同的数据库访问技术的事务处理统一起来

使用Spring的事务管理器，管理不同数据库访问技术的事务处理。开发人员需要掌握Spring的事务处理一个方案就可以实现不同数据库访问技术的事务管理。

管理事务面向的事Spring，由Spring来管理事务，做事务提交和事务回滚。

##### 5.3.3 Spring事务管理器

Spring框架使用事务管理器对象，管理所有事务。

事务管理器接口：PlatformTransactionManager。

​							作用：定义了事务的操作，主要是commit()、rollback()

```java
public interface PlatformTransactionManager extends TransactionManager {

    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;

    void commit(TransactionStatus status) throws TransactionException;

    void rollback(TransactionStatus status) throws TransactionException;
}
```

事务管理器有很多的实现类：一种数据库的访问技术有一个实现类，由实现类具体完成事务的提交和回滚。因此JDBC或MyBatis访问数据库有自己的事务管理器实现类：DataSourceTranactionManager；hibernate框架事务管理器实现类：HibernateTranactionManager。

相关文档：https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html

API文档：https://docs.spring.io/spring-framework/docs/current/javadoc-api/

##### 5.3.4 事务管理器工作方式

![image-20220507142054508](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220507142054508.png)

##### 5.3.5 事务的提交或回滚时机

当业务方法正常执行且没有异常时，事务提交。当业务方法抛出了运行时异常，事务是回滚的。

异常的分类：

- Error：严重错误。回滚事务
- Exception：异常类，可以出来的异常情况
  - 运行时异常：RuntimeException和它的子类都是运行时异常，在程序执行过程中抛出的异常。常见的运行时异常有：NullPointerException\NumberFormatException\IndexOutOfBoundsException\ArithmeticException               【回滚】
  - 受查异常：编写Java代码时，必须出来的异常。例如：IOException\SQLException\FileNotFoundException       【默认提交事务】

【方法中抛出运行时异常事务回滚，其他情况（正常执行方法、受查异常）提交事务】

##### 5.3.6 事务使用的AOP的环绕通知

环绕通知：可以在目标方法的前和后都能增强功能，不需要修改业务代码。

```java
//spring给业务方法在执行时，增加上事务的切面功能
@Around("execption(* 所有的业务类中的方法)")
public Object myAround(ProceedingJoinPoint pjp){
    try{
        PlatformTransactionManager.beginTransaction();使用spring的事务管理器开启事务
        pjp.proceed();//执行目标方法
        PlatformTransactionManager.commit()//业务方法正常执行，提交事务
        
    }catch(Exception e){
        PlatformTransactionManager.rollback();//业务方法非正常执行，回滚事务            
    }        
}
```

#### 5.4 事务定义接口

TransactionDefinition接口。定义了三类常量，定义了有关事务控制的属性。

事务的属性：1)隔离级别 2）传播行为 3）事务的超时

给业务方法说明事务的属性，和ACID不一样。

##### 5.4.1 隔离级别

隔离级别：控制事务之间影响的程度。5个值，只有4个隔离级别。

| `static int` | ISOLATION_DEFAULT          | 采用DB默认的事务隔离级别，MySql默认为REPEATABLE_READ;Oracle默认为READ_COMMITTED |
| ------------ | -------------------------- | ------------------------------------------------------------ |
| `static int` | ISOLATION_READ_COMMITTED   | 读已提交，解决脏读，存在不可重复读与幻读                     |
| `static int` | ISOLATION_READ_UNCOMMITTED | 读未提交，未解决任何并发问题                                 |
| `static int` | ISOLATION_REPEATABLE_READ  | 可重复读，解决脏读、不可重复读、存在幻读                     |
| `static int` | ISOLATION_SERIALIZABLE     | 串行化，不存在并发问题                                       |

##### 5.4.2 事务的超时

超时时间，以秒为单位。整数值。默认-1.

超时时间：表示一个业务方法最长的执行时间，若没有到达时间没有执行完毕，Spring回滚事务

##### 5.4.3 事务的传播行为

传播行为：业务方法在调用时，事务在方法之间的传递和使用。有7个值。

使用传播行为可以标识方法有无事务。

| `static int` | `PROPAGATION_MANDATORY`Support a current transaction; throw an exception if no current transaction exists. |
| ------------ | ------------------------------------------------------------ |
| `static int` | `PROPAGATION_NESTED`Execute within a nested transaction if a current transaction exists, behave like [`PROPAGATION_REQUIRED`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/TransactionDefinition.html#PROPAGATION_REQUIRED) otherwise. |
| `static int` | `PROPAGATION_NEVER`Do not support a current transaction; throw an exception if a current transaction exists. |
| `static int` | `PROPAGATION_NOT_SUPPORTED`Do not support a current transaction; rather always execute non-transactionally. |
| `static int` | `PROPAGATION_REQUIRED`Support a current transaction; create a new one if none exists. |
| `static int` | `PROPAGATION_REQUIRES_NEW`Create a new transaction, suspending the current transaction if one exists. |
| `static int` | `PROPAGATION_SUPPORTS`Support a current transaction; execute non-transactionally if none exists. |

**1）required**

Spring的默认传播行为，方法在调用时，若存在事务就使用当前事务。若没有事务，则新建事务方法在新事务中执行。

![image-20220507170748522](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220507170748522.png)

**2）support**

支持，方法有事务可以正常执行，没有事务也可以正常执行。

![image-20220507170759355](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220507170759355.png)

**3）require_new**

方法需要一个新事物。如果在调用方法时，存在一个事务，则原来的事务暂停，直到新事务执行完毕。如果方法调用时没有事务，则新建一个事务，在新事物执行代码。

![image-20220507170807348](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220507170807348.png)

#### 5.5 Spring框架使用自己的注解@Transaction控制事务

@Transaction注解：使用注解的属性控制事务（隔离级别、传播行为、超时）

属性：

- propagation：事务的传播行为，使用的是Propagation类的枚举值，例如Propagation.REQUIRED

  ```java
  public enum Propagation {
      REQUIRED(0),
      SUPPORTS(1),
      MANDATORY(2),
      REQUIRES_NEW(3),
      NOT_SUPPORTED(4),
      NEVER(5),
      NESTED(6);
  
      private final int value;
  
      private Propagation(int value) {
          this.value = value;
      }
  
      public int value() {
          return this.value;
      }
  }
  ```

- isolation：隔离级别。使用Isolation类的枚举值，表示隔离级别

```java
public enum Isolation {
    DEFAULT(-1),
    READ_UNCOMMITTED(1),
    READ_COMMITTED(2),
    REPEATABLE_READ(4),
    SERIALIZABLE(8);

    private final int value;

    private Isolation(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
```

- readOnly：数据库操作是否是制度。boolean类型，默认false
- timeout：事务的超时时间，默认-1，整数值，单位是秒。例如timeout=20
- rollbackFor：表示回滚的异常类列列表，他的值是一个数组，每个值是异常类型的class
- rollbackForClassName：表示回滚的异常类列表，它的值是异常类名称，是String类型的值
- noRollbackFor：不需要回滚的异常类列表，是class类型
- noRollbackForClassName：不需要回滚的异常类列表，是String类型的值

注解位置：1）在业务方法的上面，是在public方法的上面；2）在类的上面，类中的所有public方法都需要事务控制

##### 5.5.1 事务控制的步骤

1. 修改spring配置文件

   ```xml
    <!--    声明事务的控制-->
       <!--    声明事务管理器-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <!-- 指定数据源DataSource       <-->
           <property name="dataSource" ref="myDataSource"/>
       </bean>
       <!--    开启事务注解驱动：告诉框架框架使用注解管理事务
               transaction-manager：指定事务管理器的id
       -->
       <tx:annotation-driven transaction-manager="transactionManager" />
   ```

2.加注解进行事务控制

```java
public class BuyGoodsServiceImpl implements BuyGoodsService {
    private GoodsDao goodsDao;
    private SaleDao saleDao;
    //@Transactional放在public方法的上面，表示方法有事务功能
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false, timeout = 20,
            rollbackFor = {NullPointerException.class, NotEnoughException.class})
    @Override
    public void buy(Integer goodId, Integer num) {
        System.out.println("====buy方法的开始");

        //生成销售记录
        Sale sale = new Sale();
        sale.setGid(goodId);
        sale.setNum(num);
        saleDao.insertSale(sale); //默认情况，事务自动提交

//        查询商品
        Goods goods = goodsDao.selectById(goodId);


        if (goods == null) {
            throw new NullPointerException(goodId + ",商品不存在");
        } else if (goods.getAmount() < num) {
            throw new NotEnoughException(goodId + "，库存不足");
        }
        //更新库存
        Goods buyGoods = new Goods();
        buyGoods.setId(goodId);
        buyGoods.setAmount(num);
        goodsDao.updateGoods(buyGoods);
    }

```

rollbackFor说明：

```java
第一种设置方式
@Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false, timeout = 20,
            rollbackFor = {NullPointerException.class, NotEnoughException.class})
第二种设置方式
@Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.DEFAULT,
            readOnly = false, timeout = 20)

第三种设置方式
@Transactional //属性使用默认值  使用默认值REQUIRED，发生运行时异常回滚
```

1）框架首先检查方法抛出的异常是不是在rollbackFor的数组中，如果在一定回滚

2）如果方法抛出的异常不在rollbackFor数组中，框架会继续检查抛出的异常它是不是RuntimeException，如果是一定回滚

例如，抛出SQLException，IOException异常向让事务回滚，那么需要加入rollbackFor数组中

**@Transactional使用特点**：1.Spring框架自己提供的事务控制 2. 适合中小型项目 3.使用方便，效率高

#### 5.6 使用AspectJ框架在Spring的配置文件中声明事务控制

使用aspectj的AOP声明事务控制叫做<u>声明式事务</u>。

使用步骤：

 1. 在pom.xml加入spring-aspectj依赖

 2. 在spring的配置文件声明事务的内容

    1）声明事务管理器

    2）声明业务方法需要的事务属性

    3）声明切入点表达式
    
    4) 关联切入点表达式和事务通知

```xml
   <!--声明式事务：不用写代码    -->
        <!--1. 声明事务管理器    -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="myDataSource"/>
    </bean>
    <!--声明业务方法的事务属性（隔离级别，传递行为，超时）
        id:给业务方法配置事务段代码七个名称：唯一值
        transaction-manager:事务管理器的id
    -->
    <tx:advice id="serviceAdvice" transaction-manager="transactionManager">
        <!--给具体的业务方法增加事务的说明    -->
        <tx:attributes>
    <!--给具体的业务方法，说明它需要的事务属性
        name :业务方法名称。配置name值：1.业务方法的名称 2. 带有部分通配符的方法名称 3. 使用*
        rollback-for:指定回滚的异常类列表，使用异常类全限定名称
    -->
            <tx:method name="buy" propagation="REQUIRED" isolation="DEFAULT" read-only="false" timeout="20"
                       rollback-for="org.example.exception.NotEnoughException，java.lang.NullPointerException"/>
            <!-- 在业务方法有命名规则后，可以对一些方法使用事务           -->
            <tx:method name="add*" propagation="REQUIRED" isolation="DEFAULT" timeout="20"/>
            <!-- 以上方法以外的           -->
            <tx:method name="*" propagation="SUPPORTS" read-only="true"/>
        </tx:attributes>

    </tx:advice>
    <!-- 声明切入点表达式，表示哪些包中的类，类中的方法参与事务   -->
    <aop:config>
    <!--声明切入点表达式
    expression：切入点表达式，表示哪些类和类中的方法要参与事务
        id：切入点表达式的名称，唯一值
    -->
        <aop:pointcut id="servicePointcut" expression="execution(* *..service..*.*(..))"/>
        <!-- 关联切入点表达式和事务通知       -->
        <aop:advisor advice-ref="serviceAdvice" pointcut-ref="servicePointcut"/>
    </aop:config>
```

### 6. Spring与Web

####  6.1 创建步骤

1. 创建web项目：webapp模板
2. 添加依赖和dao接口
3. sql语句
4. 创建Service
5. 创建Servlet

#### 6.2 需要一个什么样的容器对象

##### 6.2.1 在servlet类中使用容器对象的问题

1. 创建容器对象次数多
2. 在多个servlet中，分别创建容器对象

```java
//XXXSevlet.java
public class AddStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strName = request.getParameter("name");
        String strAge = request.getParameter("age");
        //调用service
        //创建容器，获取service,一个请求创建次数多，比较浪费，没有必要
        String config = "applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(config);
        System.out.println("servlet中创建的对象容器===" + ctx);
        StudentService service= (StudentService) ctx.getBean("studentService");
        Student  student = new Student();
        student.setStuname(strName);
        student.setStuage(Integer.valueOf(strAge));
        service.addStudent(student);
        //给用户现实请求的结果
        request.getRequestDispatcher("/show.jsp").forward(request,response);

    }
}
```

想法：

1. 容器对象只有一个，创建一次就可以了
2. 容器对象应该在整个项目中共享使用，多个servlet都能使用同一个容器对象

改进：使用监听器ServletContextListener（两个方法 初始时执行的，销毁时执行的）创建容器对象

在监听器中创建好的容器对象应该放在web应用中的ServletContextListener的作用域中

#### 6.3 使用Spring的监听器ContextLoaderListener

ContextLoaderListener监听器对象，Spring提供，使用监听器的作用：

	1. 创建容器对象，一次
	1. 把容器对象放入到ServletContext作用域

使用步骤：

 	1. pom.xml加入依赖 spring-web

```xml
<dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>5.3.18</version>
</dependency>
```

2. web.xml声明监听器（的存在）

```xml
<context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring-beans.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
```

3. 使用

```java
public class AddStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strName = request.getParameter("name");
        String strAge = request.getParameter("age");
		 //使用了监听器已将创建好了容器对象，从ServletContext作用域中获取容器对象
        /*WebApplicationContext ctx = null;
        String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        //ServletContext sc =  getServletContext(); // ServletContext,servlet中的方法
        ServletContext sc=  request.getServletContext(); //HttpServletRequest对象的饿方法
        Object attr = sc.getAttribute(key);
        if(attr !=null ){
            ctx = (WebApplicationContext) attr;

        }
        */
        
        
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
```

```java
//监听器的源代码
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {
    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context) {
        super(context);
    }
	//监听器的初始方法
    public void contextInitialized(ServletContextEvent event) {
        this.initWebApplicationContext(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
        this.closeWebApplicationContext(event.getServletContext());
        ContextCleanupListener.cleanupAttributes(event.getServletContext());
    }
}
```

```java

private WebApplicationContext context;//容器对象    
public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        if (servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE) != null) {
            throw new IllegalStateException("Cannot initialize context because there is already a root application context present - check whether you have multiple ContextLoader* definitions in your web.xml!");
        } else {
            servletContext.log("Initializing Spring root WebApplicationContext");
            Log logger = LogFactory.getLog(ContextLoader.class);
            if (logger.isInfoEnabled()) {
                logger.info("Root WebApplicationContext: initialization started");
            }

            long startTime = System.currentTimeMillis();

            try {
                
                if (this.context == null) {
                    //创建Spring的容器对象
                    this.context = this.createWebApplicationContext(servletContext);
                }

                if (this.context instanceof ConfigurableWebApplicationContext) {
                    ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext)this.context;
                    if (!cwac.isActive()) {
                        if (cwac.getParent() == null) {
                            ApplicationContext parent = this.loadParentContext(servletContext);
                            cwac.setParent(parent);
                        }

                        this.configureAndRefreshWebApplicationContext(cwac, servletContext);
                    }
                }
				//把容器对象放入ServletContext作用域
                // key :WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
                // value: 容器对象
                servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
                ClassLoader ccl = Thread.currentThread().getContextClassLoader();
                if (ccl == ContextLoader.class.getClassLoader()) {
                    currentContext = this.context;
                } else if (ccl != null) {
                    currentContextPerThread.put(ccl, this.context);
                }

                if (logger.isInfoEnabled()) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    logger.info("Root WebApplicationContext initialized in " + elapsedTime + " ms");
                }

                return this.context;
            } catch (Error | RuntimeException var8) {
                logger.error("Context initialization failed", var8);
                servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, var8);
                throw var8;
            }
        }
    }
```

```java
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";
    String SCOPE_REQUEST = "request";
    String SCOPE_SESSION = "session";
    String SCOPE_APPLICATION = "application";
    String SERVLET_CONTEXT_BEAN_NAME = "servletContext";
    String CONTEXT_PARAMETERS_BEAN_NAME = "contextParameters";
    String CONTEXT_ATTRIBUTES_BEAN_NAME = "contextAttributes";

    @Nullable
    ServletContext getServletContext();
}
//WebApplicationContext是web项目中使用的容器对象
```







