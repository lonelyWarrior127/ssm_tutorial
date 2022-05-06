使用aop做方法的参数检查

要求：
1.当addNumber方法参数，不为null的时候
2.当addNumber方法参数大于0时，才执行addNumber()计算三个数的和
3.如果任意一个参数是null或者小于0，则调用addNumber方法返回结果是-1
4. 使用aop做addNumber方法的参数检查