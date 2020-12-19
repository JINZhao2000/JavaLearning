package Cours2.Cycle;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser () {
		System.out.println("add user");
	}

	public void myInit(){
		System.out.println("Init4");
	}

	public void myDestroy(){
		System.out.println("Destroy4");
		//close() before be destroyed
		//singleton
	}
}
