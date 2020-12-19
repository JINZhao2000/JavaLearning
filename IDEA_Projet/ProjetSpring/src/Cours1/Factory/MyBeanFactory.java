package Cours1.Factory;

public class MyBeanFactory {
	public UserService3 createService(){
		return new UserServiceImpl3();
	}
}
