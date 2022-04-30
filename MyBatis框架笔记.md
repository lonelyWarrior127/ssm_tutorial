## 前言

#### 1. 三层架构

MVC：web开发中，使用MVC架构模式。M：数据，V：视图，C：控制器

​		C：控制器，接收请求，调用Service对象，显示请求的处理结果。当前使用servlet作为控制器

​		V：视图，现在使用jsp，html,css js。显示请求的处理结果，把M数据显示出来

​		M：数据，来自数据库Mysql，来自文件，网络

MVC作用：

   1) 实现解耦合
   2) 让MVC各负其责
   3) 使得系统扩展更好，更容易维护

三层架构：

· 界面层（视图层）：接收用户的请求，调用service，显示请求的处理结果，包含jsp，html，servlet等。和用户直接打交道

· 业务逻辑层：处理业务逻辑，使用算法处理数据。把数据返回给界面层。对应是service包，和包中的很多的XXXService类。

· 持久层（数据访问层）：访问数据层，或者读取文件，访问网络，获取数据。对应的事dao。dao包中有很多的XXXDao等等

#### 2. 三层架构请求的处理流程

用户发起请求 → 界面层 → 业务逻辑层  → 持久层 → 数据库

####  3. 三层架构模式和框架

每一层对应着一个框架

1）界面层 ---SpringMVC框架

2）业务逻辑层 --- Spring框架

3） 持久层 --- MyBatis框架

#### 4. JDBC编程

```java
public void findStudent(){
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs =null;
    try{
        //注册mysql驱动
        Class.forName("com.mysql.jdbc.Driver");
        //连接数据的基本信息， url username password
        String url = "jdbc:mysql://localhost:3306/db";
        String username = "root";
        String password = "123456";
        //创建连接对象
        conn = DriverManger.getConnection(url,username,password);
        //保存查询结果
        List<Student> stuList = new ArrayList<>();
        //创建Statement，用来执行sql语句
        stmt = conn.createStatement();
        //执行查询，创建记录集
        rs = stmt.executeQuery("select * from student");
        while(rs.next()){
            Student stu = new Student();
            stu.setId(rs.getInt("id"));
            stu.setName(rs.get.getString("name"));
            stu.serAge(rs.getInt("age"));
            //从数据库取出数据转为Student对象，封装到List集合
            stuList.add(stu);
        }       
    }catch(Exception e){
        e.printStackTrace();
    }
    finally{
        try{
            //关闭资源
            if(rs != null){
                rs.close();
            }
            if(stmt！=null){
                stmt.close();
            }
            if(conn !=null){
                conn.close();
            }
        }catch(Execption e){
            e.printStackTrace();
        }
    }
}
```

 jdbc缺点： 创建很多对象；注册驱动；执行SQL语句；把ResultSet转为Student，List集合；关闭资源；SQL语句和业务逻辑代码混在一起

## MyBatis框架

是一个**持久层框架**，底层仍然是JDBC，是对JDBC的高级封装，做数据库的相关操作。

解决问题：减轻使用JDBC的复杂性，不用编写重复的创建Connection,Statement；不用编写关闭资源代码。直接使用Java对象，表示结果数据。让开发者专注SQL的处理，其他工作由MyBatis代劳。

https://mybatis.org/mybatis-3/zh/getting-started.html

MyBatis可以完成：

1. 注册数据库驱动，例如Class.forName("com.mysql.jdbc.Driver")
2. 创建JDBC中必须使用的Connection.Statement,ResultSet对象
3. 从XML中获取SQL，并执行SQL语句，把ResultSet结果转换Java对象
4. 关闭资源
5. 实现SQL语句（放在XML中）和Java代码的解耦合


####　1. 搭建MyBatis开发环境

##### 1.1 步骤：

1. 创建mysql数据库和表

   Stu(id,name,email,age)

2. 创建maven工程；

3. 修改pom.xml

   1)加入Mybatis依赖，mysql驱动，测试代码JUnit

   2）在<build>加入资源插件

4. 编写Student实体类。定义属性，属性名与列名保持一致

5. 编写Dao接口StudentDao。定义操作数据库的方法。

6. 创建XML文件（Mapper映射文件）。写SQL语句。

   ​	MyBatis框架推荐是把SQL语句和Java代码分开

   ​    mapper文件：定义和Dao接口在同一目录，一个表一个mapper文件。

7. 创建MyBatis主配置文件（XML文件）：有1个，放在resource目录下。

      1）定义创建连接实例的数据源对象（DataSource对象）

      2）指定其他mapper文件的位置

8. 创建测试内容

      使用main方法，测试MyBatis访问数据库；

      ​	 -- 创建MyBatis核心类SqlSessionFactory

      也可以使用Junit访问数据库

      ```java
      public void testSelectStudentById() throws IOException {
          //调用MyBatis某个对象的方法，执行mapper文件的SQL语句
          //Mybatis核心类， SqlSessionFactory
      
          // 1. 定义mybatis主配置文件的位置，从类路径开始的相对路径
          String config = "mybatis.xml";
          // 2. 读取主配置文件，使用mybatis框架中的Resource类
          InputStream inputStream = Resources.getResourceAsStream(config);
          // 3. 创建SqlSessionFactory对象，使用SqlSessionFactoryBuilder类的build方法
          SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
          //4. 获取SqlSession对象
          SqlSession session = sqlSessionFactory.openSession();
          //5. 指定要执行SQL语句的id
          // sql的id = namespace + "." + <select>|update|insert|delete标签的id属性值
          String sqlId = "dao.StudentDao"+"."+"selectStudentById"; //【变化】
          //6. 通过SqlSession的方法，执行SQL语句
          Student  student = session.selectOne(sqlId); // 【变化】
          System.out.println(student);
          //7. 关闭SqlSession对象
          session.close();
      ```

      sql的id = namespace + "." + <select>|update|insert|delete标签的id属性值

      sqlID的作用：找到XXXDao.xml文件中对应sql语句的namespace和id

9. 配置日志功能

​	https://mybatis.org/mybatis-3/zh/logging.html	

```xml
<configuration>
 <settings>
    ...
    <setting name="logImpl" value="LOG4J"/>
    ...
  </settings>
</configuration>
```
##### 1.2 使用占位符

​	#{studentId}:占位符 表示从JAVA程序中传入过来的数据

```sql
select *  from student where id  =#{studentId}
```

如果传入mybatis是一个JAVA对象，使用#{属性名}获取此属性的值属性值放到#{}占位符的位置
mybatis执行此属性对应的getXXX(),例如 #{id},执行getId();

```sql
insert into student values(#{id},#{name},#{email},#{age})
```

##### 1.3 概念

1. 自动提交事务：当SQL语句执行完毕后，提交事务。数据库更新操作直接保存到数据库
2. 手动提交事务：在需要提交事务的位置，执行方法，提交事务或者回滚事务。

#### 2. Mybatis的一些重要对象

##### 2.1 Resources

mybatis框架总的对象，一个作用读取主配置信息。

```java
InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
```

##### 2.2 SqlSessionFactoryBuilder

负责创建SqlSessionFactory对象

```java
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```

##### 2.3 SqlSessionFactory

接口，重量级：创建此对象需要使用更多的资源和时间。在项目中有一个就可以了。

作用：SqlSession的工厂，就是创建SqlSession对象。

```java
public interface SqlSessionFactory {
  SqlSession openSession();
  SqlSession openSession(boolean autoCommit);
  SqlSession openSession(Connection connection);
  SqlSession openSession(TransactionIsolationLevel level);
  SqlSession openSession(ExecutorType execType);
  SqlSession openSession(ExecutorType execType, boolean autoCommit);
  SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);
  SqlSession openSession(ExecutorType execType, Connection connection);
  Configuration getConfiguration();
}
```

openSession()：获取一个默认的SqlSession对象，默认是需要手动提交事务的。

openSession(boolean autoCommit)：参数表示是否自动提交事务。

DefaultSqlSessionFactory实现类:

```java
public class DefaultSqlSessionFactory implements SqlSessionFactory{}
```

##### 2.4 SqlSession

是通过SqlSessionFactory获取，SqlSession本身是接口。

实现类DefaultSqlSession：

```java
public class DefaultSqlSession implements SqlSession{}
```

SqlSession 作用：提供了大量执行sql语句的方法：

```
selectOne：执行sql语句，最多得到一行记录，多余1行则错误。
selectList：执行sql语句，返回多行数据
selectMap：执行sql语句，得到一个Map结果
insert：插入语句
update：更新语句
delete:删除语句
commit：提交事务
rollback：回滚事务
```

​	注意：SqlSession对象不是线程安全的，保证SqlSession对象变量是方法的局部变量，其使用的步骤如下：

​		1）在方法的内部，执行sql语句之前，先获取SqlSession对象；

​		2）调用SqlSession的方法，执行sql语句；

​		3）关闭SqlSession对象，执行SqlSession.close()。

##### 2.5 创建mapper文件的模板

1）创建模板，mapper文件模板和mybatis主配置文件模板

创建模板的步骤：

![image-20220422232612170](C:\Users\pyao\AppData\Roaming\Typora\typora-user-images\image-20220422232612170.png)

##### 2.6 创建工具类MyBatisUtil

```java
public class MyBatisUtil {
    private static  SqlSessionFactory factory = null;
    static {
        String config = "mybatis.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(config);
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // 创建方法，获取SqlSession对象
    public static SqlSession getSqlSession(){
        SqlSession session = null;
        if(factory !=null){
            session = factory.openSession();
        }
        return  session;
    }
}
```

使用：

```java
SqlSession session = MyBatisUtil.getSqlSession();
```

##### 2.7 使用传统的dao执行sql

```java
public interface StudentDao {
    Student selectStudentById(Integer id);
    List<Student> selectStudents();
    int  insertStudent(Student student);
}
```

```java
public class StudentDaoImpl implements StudentDao {
    public StudentDaoImpl() {
        super();
    }

    @Override
    public Student selectStudentById(Integer id) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        String sqlId = "com.dao.StudentDao.selectStudentById";
        Student student = sqlSession.selectOne(sqlId, 1);
        sqlSession.close();
        return student;
    }

    @Override
    public List<Student> selectStudents() {
        SqlSession session = MyBatisUtil.getSqlSession();
        String sqlId = "com.dao.StudentDao" + "." + "selectStudents";
        List<Student> students = session.selectList(sqlId);
        session.close();
        return students;
    }

    @Override
    public int insertStudent(Student student) {
        SqlSession session = MyBatisUtil.getSqlSession();
        String sqlId = "com.dao.StudentDao" + "." + "insertStudent";
        int rows = session.insert(sqlId, student);
        session.commit();
        session.close();
        return rows;
    }
}
```

##### 2.8 dao代理技术

**MyBatis提供代理**：MyBatis创建dao接口的实现类对象，完成对SQL语句的执行。MyBatis创建一个对象代替你的dao实现类功能。

```java
分析：
String sqlId = "com.dao.StudentDao.selectStudentById";
Student student = sqlSession.selectOne(sqlId, 1);

测试方法中，调用dao的方法:
Student student = dao.selectStudentById(2);
1)dao：通过反射能得到全限定类型名称
    dao是StudentDao类型的，全限定名称：com.dao.StudentDao
2） selectStudentById：dao中的方法名称，方法名称：mapper文件中标签的id
    通过dao.selectStudentById()能得到sqlid的信息"com.dao.StudentDao.selectStudentById"
3）确定调用SqlSession的哪个方法
    1. 根据dao接口的方法返回值，如果返回是一个对象，例如Student,调用SqlSession.selectOne()
    如果返回是一个List，调用SqlSession.selectList()
    2. 根据mapper文件中的标签，如果标签是<insert>，调用SqlSession.insert()方法
MyBatis框架，发现使用dao的方法调用能确定执行sql语句的必要信息，所以MyBatis能简化dao对象的实现
    由MyBatis框架在程序执行期间，根据dao接口，创建一个内存中的接口的实现类对线。
    MyBatis把这个技术叫做dao的动态代理
```

dao代理技术：由MyBatis创建StudentDao接口的实现类Proxy(StudentDaoImpl)，使用框架创建的StudentDaoImpl代替程序员自己手工实现的StudentDaoImpl类的功能，因此不用程序员写dao接口的实现类。

**使用MyBatis代理要求**：

~ mapper文件中的namespace必须是dao接口的全限定名称

 ~ mapper文件中标签的id是dao接口中的方法名称

**MyBatis代理实现方式：**

使用SqlSession对象的方法getMapper(dao.class)

例如：现在有StudentDao接口。

```java
SqlSession session = MyBatisUtil.getSqlSession();
StudentDao dao = session.getMapper(StudentDao.class);
Student studnt = dao.selectStudentById(1);
// 上面代码中
StudentDao dao = session.getMapper(StudentDao.class); 
等同于
StudentDao dao = new StudentDaoImpl();    
```

   #### 3. 理解参数

理解参数：通过Java程序把数据传入到mapper.xml文件中的sql语句。参数主要是指dao接口方法的形参。

##### 3.1 parameterType

parameterType：表示参数的类型，指定dao方法的形参数据类型。这个形参的数据类型是给MyBatis使用。

​								这个属性的值可以使用JAVA类型的全限定名称或者mybatis定义的别名

MyBatis在给SQL语句的参数赋值时使用。 PreparedStatement.setXXX(位置，值)

mybatis通过反射机制可以获取dao接口方法参数类型，parameterType可以不写

```xml
<mapper namespace="com.dao.StudentDao">

    <!--
        parameterType:指定dao接口形参类型，这个属性的值可以使用JAVA类型的全限定名称或者mybatis定义的别名
        
        mybatis的实行sql语句：select * from student where id = ?
         ？是占位符，使用jdbc中的PreparedStatement执行这样的sql语句
         PreparedStatement pst = conn.PreparedStatement("select * from student where id = ?");
         给？位置赋值:  
         参数是整型：pst.setInt(1,4);
         参数是String pst.setString(1,"4");
			
		 第一个用法：java类型全限定的类型名称   parameterType="java.lang.Integer"
         第二个用法：mybatis定义的java类型的别名，看文档 parameterType="int"
    -->
    <select id="selectStudentById"  parameterType="java.lang.Integer" resultType="com.domain.Student">
        select *
        from student
        where id = #{studentId}
    </select>
</mapper>
```

##### 3.2 传递参数的方式

1. **dao接口方式有一个简单类型的参数**

   简单类型：Java基本类型和String

   占位符 #{任意字符} 和方法的参数名无关

   ```java
   Student selectStudentById(Integer id);
   ```

   ```xml
   <select id="selectStudentById"  parameterType="java.lang.Integer" resultType="com.domain.Student">
       select * from student where id = #{studentId}
   </select>
   ```

2. **dao接口方式有多个简单类型的参数**

   @Param：命名参数，在方法的形参前面使用，定义参数名。这个名称可以用在mapper文件中。这个注解是MyBatis框架提供。

   ​					使用位置：在形参定义的前面。

   ​					属性：value自定义的参数名称。

   当使用@Param命名后，在mapper中，使用#{命名的参数} 。

   例如@Param(“myname")  -> #{myname}

   ```java
   List<Student> selectByNameOrAge(@Param(value = "myname") String name, @Param(value = "myage") Integer age);
   ```

   ```xml
   <select id="selectByNameOrAge" resultType="com.domain.Student">
       select id,name email,age from student where name = #{myname} or age = #{myage}
   </select>
   ```

3. **dao接口方式有多个参数--使用对象传参**

   方法的形参是一个Java对象，这个Java对象表示多个参数。使用对象的属性值作为参数的使用

   这个Java对象有属性，每个属性有set和get方法。这个对象可以是自定义对象，不一定非要是实体类对象。

   **简单方法**： #{属性名} ，MyBatis调用此属性的getXXX()方法获取属性值

   **复杂方法：**#{属性名，javaType = java类型的全限定名称，jdbcType = mybatis中定义列的数据类型}

4. **多个参数--按位置**

   参数位置：dao接口中方法的形参列表，从左往右，参数的位置是0,1,2....

   语法格式： #{arg位置}，第一个参数是#{arg0}，第二个是#{arg1}

5. **多个参数--使用Map传参**

   在mapper文件中，获取map的值，是通过key获取的。

   语法：#{key}

##### 3.3 占位符

1. 占位符#

   语法：#{字符}

   告诉mybatis使用实际的参数值替代；并使用PrePareStatement对象执行SQL语句，#{...}代替SQL语句的“?”

   ```xml
   <select id="selectStudentById"  parameterType="java.lang.Integer" resultType="com.domain.Student">
           select * from student where id = #{studentId}
   </select>
   ```

   ```java
   //mybatis创建PrePareStatement对象，执行SQL语句
   String sql = "select * from student where id = ?";
   PrePareStatement pst = conn.prepareStatement(sql);
   pst.setInt(1,1001); //传递参数
   ResultSets  rs = pst.executeQuery();//执行SQL语句
   ```

   #{}的特点：

   1）使用PrePareStatement对象，执行SQL语句高效

   2）使用PrePareStatement对象，能避免SQL注入，SQL语句执行更安全

   3）#{}长用作**列值**使用，位于等号的右边，#{}位置的值和数据类型有关

2. 占位符$

   语法：${字符}

   MyBatis执行${}占位符的SQL语句

```xml
<select id="selectStudentById"  parameterType="java.lang.Integer" resultType="com.domain.Student">
        select * from student where id = ${studentId}
</select>

${}表示字符串连接，把sql语句的其他内同和${}内容使用字符串(+)连接的方式连在一起
String sql = "select * from student where id = "+"1001";
```

```java
//mybatis创建Statement对象，执行SQL语句
Statement stmt = conn.createStatement(sql);
ResultSet rs = stmt.executeQuery();
```

${}的特点：

 1）使用Statement对象，执行SQL语句效率低

2）${}占位符的值，使用字符串连接方式，有SQL注入的方向，有代码安全问题

3）${}数据是原样使用的，不会区分数据类型。

4）${}的常用作**表名**或者**列名**，在能保证数据安全的情况下使用

使用例子: 按列排序

```xml
<select id="selectOrderBy" resultType="com.domain.Student">
    select  * from student order  by ${orderName} desc
</select>
```

```java
List<Student> selectOrderBy(String orderName);
```

##### 3.4 封装MyBatis输出结果

封装输出结果：MyBatis执行SQL语句，得到ResultSet，转为Java对象。

1. **resultType属性**：在执行select时使用，作为<select>标签的属性出现的。

   ​								类属性名和列必须同名。

   ​								表现结果类型，mysql执行sql语句，得到Java对象的类型，它的值有两种：

   ​							1）Java类型的全限定名称。 2）使用别名，见3.5
   
   ```xml
   Student selectStudentById(Integer id);
   <select id="selectOrderBy" resultType="com.domain.Student">
       select  * from student order  by ${orderName} desc
   </select>
   resultType：现在使用Java类型的全限定名称。表示的意思是mybatis执行sql,把ResultSet中的数据转为Java对象（可以是自定义对象，不一定是实体类对象）。
   MyBatis会做以下操作：
       1. 调用com.domain.Student的无参数构造函数方法，创建对象。
          Student  student = new Student(); //使用反射创建对象
   	2. 同名的列赋值给同名的属性（注意：类属性名和列必须同名）
           student.setId(rs.getInt("id"));
   		.....
       3. 得到Java对象，如果dao接口返回值是List集合，MyBatis把student对象放入List集合。
   所以执行 Studnet mystudent = dao.selectById(1);得到数据库中id=1这行数据，这行数据的列值，赋给mystudent对象的属性。
   ```

​				3）resultType：表示一个map结构

​											执行SQL语句得到一个Map结构数据，MyBatis执行SQL，把ResultSet转为map

 											sql执行结果，列名作为map的key，列值作为value

​											sql执行得到是一行记录，转为map结构是正确的

​											dao接口返回是一个map，sql语句最多能获取一行记录，多余一行报错。

2. **resultMap**：结果映射，自定义列名和JAVA属性的对应关系。常用在列名和属性名不同的情况。

   ​     				   用法： 1）先定义resultMap标签，指定列名和属性名称对应关系
   
    									2）在select标签使用resultMap属性，指定上面定义的resultMap的id值
   
   在mapper文件中设置和使用：
   
   ```xml
   <!--使用resultMap定义列和属性的关系-->
       <!--定义resultMap
           id :给resultMap的映射关系起个名称，唯一值
           type:java类型的全限定名称
   -->
       <resultMap id="customMap" type="com.vo.CustomObject">
           <!--定义列名和属性名的对应-->
           <!--主键类型使用id标签-->pr
           <id column="id" property="cid"/>
           <!--非主键类型使用result标签-->
           <result column="name" property="cname"/>
           <!--列名和属性名相同不用定义-->
       </resultMap>
       
       <select id="selectById2" resultMap="customMap">
           select * from student
       </select>
   ```
   
   resultMap和resultType不能同时使用，二选一。

##### 3.5自定义别名

MyBatis提供的对Java类型定义简短，方便记忆。

自定义别名的步骤：

1）在MyBatis主配置文件，使用typeAliase标签声明别名

2）在mapper文件中，resultType = "别名"

(1) resultType表示的是自定义对象

```xml
<typeAliases>
    <!--第一种语法格式
        type:java类型的全限定名称（自定义类型）
        alias:自定义别名
		优点：别名可以自定义
        缺点：每个类型必须单独定义
    -->
    <typeAlias type="com.domain.Student" alias="Stu"/>  
    
    <!--第二种语法格式
 		name:包名，MyBatis会把这个包中所有类名作为别名，不用区分大小写
		优点：使用方便，一次给多个类定义别名
		缺点：别名不能自定义，必须是类名，还要避免不同包之间的类重名。
	-->
	<package name="com.domain"/>
</typeAliases>
```

(2) resultType表示的是简单类型

```java
long countStudent();
```

```xml
<select id="countStudent" resultType="java.lang.Long">
    select count(*) from student
</select>
```

##### 3.6 Java对象属性名和列名不同的处理方式

1. 使用和resultMap：使用自定义列名和属性名称对应关系
2. 使用resultType：使用列别名，让别名和Java对象属性名称一样

##### 3.7 模糊查询like

1. 在Java程序中，把like的内容组装好。把这个内容传入sql语句

   ```java
   List<Student> selectLikeOne(@Param("name") String name);
   ```

   ```xml
   <select id="selectLikeOne" resultType="com.domain.Student">
       select * from student where name like #{name}
   </select>
   ```

   ```java
   List<Student> students = dao.selectLikeOne("%辉%");
   ```

2. 在SQL语句，组织like内容

   SQL语句like格式：where name like "%“空格#{name}空格”%"

```xml
<select id="selectLikeOne" resultType="com.domain.Student">
    select * from student where name like "%" #{name} "%"
</select>
```

```java
List<Student> students = dao.selectLikeOne("辉");
```

#### 4. 动态SQL

同一个dao方法，根据不同的条件可以表示不同的sql语句，主要是where部分有变化。

使用MyBatis提供的标签，实现动态SQL的能力，例如if,where,foreach,sql。

使用动态sql的时候，dao方法的形参使用<u>Java对象</u>

##### 4.1 if

语法：

```xml
<if test="dao方法形参对象的属性值来作为条件,boolean判断结果">
	sql 语句
</if>

在mapper文件中
<select id="selectStudent" resultType="com.domain.Student">
    select * from student
    <if test="boolean判断结果">
	sql 语句1
	</if>
    <if test="boolean判断结果">
	sql 语句2
	</if>
</select>
```

使用场景：多条件查询

#### 4.2 where

使用if标签时，容易引起sql语句语法错误，使用where标签解决if产生的语法错误

使用时，where 里面是一个或多个if标签，当有一个if标签判断条件为true,where标签会转为where关键字附加到sql语句的后面。如果if没有一个条件为true，忽略where和里面的if。

where标签删除和它最近的or或者and

语法：

```xml
<where>
    <if test ="条件1"> sql语句1 </if>
    <if test ="条件2"> sql语句2 </if>
</where>
```

#### 4.3 foreach

手工实现循环：

```sql
select * from student where id in (1001,1002,1003)
```

```java
List<Integer> idList = new ArrayList<>();
idList.add(1001);
idList.add(1002);
idList.add(1003);
StringBuffer sql = new StringBuffer("");
sql.append("select * from student where id in ");
sql.append("(");
//使用循环，把List数据追加到sql字符串中
for(int i =0;i<idList.size();i++){
    Integer item.idList.get(i);
    sql.append(item);
    sql.append(",");
}
 sql.append(")");
```

使用foreach可以循环数据，list集合，一般使用在in语句中。

语法：

```xml
<foreach collection="集合类型" open="开始的字符" close="结束的字符"
         item = "集合中的成员" separator="集合成员之间的分隔符">
    #{item的值}
</foreach>
标签属性：
collection：表示循环的对象是数组还是list集合。如果dao接口方法形参是数组，collection="array"  如果dao接口方法形参是List，collection="list"
open：循环开始时的字符。sql.append("(");
close：循环结束时的字符。 sql.append(" )");
item：集合成员，自定义成员 Integer item.idList.get(i);
separator：集合成员之间的分隔符。sql.append(",");
 #{item的值}：获取集合成员的值
```

应用：

**case1：foreach简单类型的List**

```xml
<select id="selectForEachOne" resultType="com.domain.Student">
    select  * from student where id in
    <foreach collection="list" open="(" close=")" separator="," item="myid">
        #{myid}
    </foreach>
</select>
```

```java
List<Student> selectForEachOne(List<Integer> idList);
```

```xml
<select id="selectForEachOne" resultType="com.domain.Student">
    select  * from student 
    <if test="list !=null and list.size>0" >
    where id in
    <foreach collection="list" open="(" close=")" separator="," item="myid">
        #{myid}
    </foreach>
    </if>
</select>
```

**case2：foreach对象类型的List**

```java
List<Student> selectForEachTwo(List<Student> stuList);
```

```xml
<select id="selectForEachTwo" resultType="com.domain.Student">
    select * from student
    <if test="list !=null and list.size>0">
        where id in 
        <foreach collection="list" open="(" close=")" separator="," item="stu">
            #{stu.id}
        </foreach>
    </if>
</select>
```

#### 4.5 代码片段

sql标签标识一段sql代码，可以是表名，几个字段，where条件都可以，可以在其他地方复用sql标签的内容

使用方式

```xml
1) 在mapper文件中定义sql代码的片段 <sql id="唯一字符串"> 部分sql语句</sql>
2）在其他位置，使用include标签引用某个代码片段
```

```xml
<sql id='selectStudent'>
    select * from studet
</sql>

<sql id='studentFieldList'>
    id,name,email
</sql>

<select id="selectForEachTwo" resultType="com.domain.Student">
    <include refid = "selectStudent"/>
</select>

<select id="selectForEachTwo" resultType="com.domain.Student">
    select  <include refid = "studentFieldList"/> from student
</select>
```

#### 5. MyBatis配置文件

MyBatis配置文件有两大类：

1. MyBatis的主配置文件，提供MyBatis全局设置，包含内容、日志、数据源、mapper文件位置
2. mapper文件，写sql语句，一个表一个mapper文件。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>


    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!-- 配置数据源：创建Connection对象。-->
            <dataSource type="POOLED">
                <!--driver 驱动内容-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <!--连接数据库的url-->
                <property name="url"
                          value="jdbc:mysql://localhost:3306/studb?useUnicode=true&amp;characterEncoding=utf-8"/>
                <!--用户名和密码-->
                <property name="username" value="root"/>
                <property name="password" value="099720"/>
            </dataSource>
        </environment>
    </environments>

    <!--指定其他mapper文件的位置：
        目的是找到其他文件的SQL语句
        -->
    <mappers>
      <mapper resource="com/dao/StudentDao.xml"/>
    </mappers>
</configuration>
```

#### 5.1 settings部分

https://mybatis.org/mybatis-3/zh/configuration.html#settings

settings是MyBatis的全局设置，影响整个MyBatis的运行。这个设置一般使用默认值即可。

举例：

```xml
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

####　5.2别名

类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置，意在降低冗余的全限定类名书写。例如：

```xml
<typeAliases>
  <typeAlias alias="Author" type="domain.blog.Author"/>
  <typeAlias alias="Blog" type="domain.blog.Blog"/>
  <typeAlias alias="Comment" type="domain.blog.Comment"/>
  <typeAlias alias="Post" type="domain.blog.Post"/>
  <typeAlias alias="Section" type="domain.blog.Section"/>
  <typeAlias alias="Tag" type="domain.blog.Tag"/>
</typeAliases>
```

当这样配置时，`Blog` 可以用在任何使用 `domain.blog.Blog` 的地方。

也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如：

```xml
<typeAliases>
  <package name="domain.blog"/>
</typeAliases>
```

每一个在包 `domain.blog` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 `domain.blog.Author` 的别名为 `author`；若有注解，则别名为其注解值。见下面的例子：

```xml
@Alias("author")
public class Author {
    ...
}
```

#### 5.3配置环境

environments：环境标签，在它里面可以配置多个environment

​					属性：default：必须是某个environment的id值一致。表示MyBatis默认的连接数据库

environment：表示一个数据库的连接信息

​					属性：id自定义的环境标识，唯一值。

transactionManager：事务管理器

​					属性：type：事务管理器的类型。属性值：JDBC：使用Connection对象，由MyBatis自己完成事务的处理

​                                                                                             MANAGED：管理，表示把事务的处理交给容器来实现，由其他软件完成事务的提交和回滚

dataSource：数据源，创建的Connection对象，连接数据库。

​						属性：type：数据源的类型。属性值：POOLED：MyBatis会在内吞中创建PoolDataSource类，管理多个Connection对象，使用连接池来管理。

​																						UNPOOLED:不使用连接池，MyBatis创建一个UnPoolDataSource类，每次执行SQL语句先创建Connection对																												象，再执行SQL语句，最后关闭Connection对象

​																						JNDI：Java的命名和目录服务。

```xml
<environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!-- 配置数据源：创建Connection对象。-->
            <dataSource type="POOLED">
                <!--driver 驱动内容-->
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <!--连接数据库的url-->
                <property name="url"
                          value="jdbc:mysql://localhost:3306/studb?useUnicode=true&amp;characterEncoding=utf-8"/>
                <!--用户名和密码-->
                <property name="username" value="root"/>
                <property name="password" value="099720"/>
            </dataSource>
</environments>
```

#### 5.4使用数据库属性配置文件

需要把数据库的配置信息放到一个单独文件中，独立管理。这个文件扩展名是properties。在这个文件中，使用自定义的key=value的格式表示数据

使用步骤：

 1. 在resources目录中，创建xxxx.properties文件

 2. 在文件中，使用key=value格式定义数据

    ```xml
    例如：jdbc.url = jdbc:mysql://localhost:3306/studb?useUnicode=true&amp;characterEncoding=utf-8
    ```

3. 在MyBatis主配置文件，使用<property>标签引用外部的属性配置文件

4. 在使用值的位置，使用${key}获取key对应的value（等号右侧的值）

   ```xml
   <property name="driver" value="${jdbc.driver}"/>
   ```

#### 5.5 mapper标签

使用mapper指定其他mapper文件的位置

mapper标签使用格式有2种常用方式

```xml
<!--resource mapper文件的路径
	优点：文件清晰，加载的文件明确，文件的位置比较灵活
    缺点：文件多，代码量会比较大，管理难度大
-->
<mappers>
      <mapper resource="com/dao/StudentDao.xml"/>
</mappers>
```

```xml
<!--
	name:包名，mapper文件所在的包名
	特点：把这个包中的所有mapper文件，一次加载
	使用要求：
			1. mapper文件和dao接口在同一目录
			2. mapper文件和dao接口名称完全一样
-->
<package name="com.dao"/>
```

#### 6. 分页

https://github.com/pagehelper/Mybatis-PageHelper

PageHelper做数据分页。在你的select语句后面加入分页的sql内容，如果使用的mysql数据库，它就是在select * from student 后面加入limit语句

使用步骤：

1. 加入依赖pagehelper依赖

   ```xml
   <dependency>
       <groupId>com.github.pagehelper</groupId>
       <artifactId>pagehelper</artifactId>
       <version>5.1.10</version>
   </dependency>
   ```

2. 在MyBatis主配置文件，加入plugin配置

在<environments>之前加入

```xml
properties?, settings?, typeAliases?, typeHandlers?, objectFactory?, objectWrapperFactory?, reflectorFactory?, plugins?, environments?, databaseIdProvider?, mappers?
```

```xml
<plugins>
    <plugin interceptor="com.github.pagehelper.PageInterceptor">
        <!-- config params as the following -->
        <property name="param1" value="value1"/>
	</plugin>
</plugins>
```

3. 在select语句之前，调用PageHelper.startPage(页码，每页大小)

```xml
<select id="selectAllStudents" resultType="com.domain.Student">
    select * from student order by id
</select>
```

```javv
List<Student> selectAllStudents( );
```

```java
public void testPageHelper(){
        SqlSession session = MyBatisUtil.getSqlSession();
        StudentDao dao = session.getMapper(StudentDao.class);
        //调用PageHelper的方法
        PageHelper.startPage(1,3);
        List<Student> students = dao.selectAllStudents();
        for(Student student : students){
            System.out.println(student);
        }
        session.close();
    }
```

