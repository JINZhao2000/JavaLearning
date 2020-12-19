package Cours3.XMLSetter;

public class Person {
	private String pname;
	private Integer age;
	private Adresse home;
	private Adresse company;

	public String getPname () {
		return pname;
	}

	public void setPname (String pname) {
		this.pname = pname;
	}

	public Integer getAge () {
		return age;
	}

	public void setAge (Integer age) {
		this.age = age;
	}

	public Adresse getHome () {
		return home;
	}

	public void setHome (Adresse home) {
		this.home = home;
	}

	public Adresse getCompany () {
		return company;
	}

	public void setCompany (Adresse company) {
		this.company = company;
	}

	@Override
	public String toString () {
		return "Person{" +
				"pname='" + pname + '\'' +
				", age=" + age +
				", home=" + home +
				", company=" + company +
				'}';
	}
}
