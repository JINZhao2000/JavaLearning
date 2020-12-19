# Spring 源码

-> xml + yaml + json

-> 封装接口

-> bean definition

-> BeanFactoryPostProcessor

-> IoC 反射创建实例

--> refresh

--> getBean

---> doGetBean

----> createBean

-----> doCreateBean

------> createBeanInstance

-------> Constructor<>[]

--------> getInstantiation

---------> BeanUtils.instantiateClass

----------> return ctor.newInstance()

-> 实例化

-> 填充属性 populateBean

--> exposedObject

-> invokeAwareMethods

-> BeanPostProcessor - before

-> invokeInitMethod

-> BeanPostProcessor - after

-> 完整对象



