package Cours3.XMLSpEL;

public class User {
	private String userName="Default";
	private Double PI;

	public String getUserName () {
		return userName;
	}

	public void setUserName (String userName) {
		this.userName = userName;
	}

	public Double getPI () {
		return PI;
	}

	public void setPI (Double PI) {
		this.PI = PI;
	}

	@Override
	public String toString () {
		return "User{" +
				"userName='" + userName + '\'' +
				", PI=" + PI +
				'}';
	}
}
