package Cours4.Component2;

import org.springframework.stereotype.Repository;

@Repository("studentDao")
public class StudentDaoImpl implements StudentDao {
	@Override
	public void save () {
		System.out.println("save");
	}
}
