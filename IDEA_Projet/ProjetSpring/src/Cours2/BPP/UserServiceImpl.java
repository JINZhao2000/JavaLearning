package Cours2.BPP;

public class UserServiceImpl implements UserService {

	@Override
	public void addUser () {
		System.out.println("add user");
	}

	public void myInit(){
		System.out.println("Init5");
	}

	public void myDestroy(){
		System.out.println("Destroy5");
		//close() before be destroyed
		//singleton
	}
}
