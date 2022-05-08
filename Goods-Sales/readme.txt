购买商品，模拟事务的处理
spring集成mybatis

实现步骤：
    1. 创建数据库表
    2. 创建maven项目
    3. 加入依赖
        spring依赖，mybatis依赖，mysql驱动，junit依赖
        mybatis-spring依赖（mybatis网站上提供，用来spring项目中，创建mybatis对象）
        spring有关事务的依赖
    4.  创建实体类
    5.  创建Dao接口和mapper文件写sql
    6.  写mybatis主配置文件
    7.  创建service接口和其他实现类,实现buy的业务方法
    8.  创建spring的配置文件
        1) 声明数据源DataSource，使用阿里的Druid连接池
        2) 声明SqlSessionFactoryBean类，在这个类内部创建是SqlSessionFactory对象
        3) 声明MapperScannerConfiguration类，在内部创建dao代理对象，
           创建的对象都在spring容器中
        4) 声明service对象，把3)中的dao赋值给service属性
    9.  测试