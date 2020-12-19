package Cours4.AnnotationScope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service("userService")
@Scope("prototype")
public class UserServiceImpl implements UserService {

	@Override
	public void addUser () {
		System.out.println("add user");
	}

	@PostConstruct
	public void myInit(){
		System.out.println("Init");
	}

	@PreDestroy
	public void myDestroy(){
		System.out.println("Destroy");
	}
}
