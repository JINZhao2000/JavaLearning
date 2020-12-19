package Cours1.StaticFactory;

public class MyBeanFactory {
	public static UserService2 createService(){
		return new UserServiceImpl2();
	}
}
