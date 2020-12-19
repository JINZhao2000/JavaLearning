package Cours4.Component2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("studentAction")
public class StudentAction {

	@Autowired
	public StudentService studentService;

	public void execute () {
		studentService.addStudent();
	}
}
