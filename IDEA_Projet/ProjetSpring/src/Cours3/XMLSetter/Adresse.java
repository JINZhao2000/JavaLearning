package Cours3.XMLSetter;

public class Adresse {
	private String addr;
	private String tel;

	public String getTel () {
		return tel;
	}

	public void setTel (String tel) {
		this.tel = tel;
	}

	public String getAddr () {
		return addr;
	}

	public void setAddr (String addr) {
		this.addr = addr;
	}

	@Override
	public String toString () {
		return "Adresse{" +
				"addr='" + addr + '\'' +
				", tel='" + tel + '\'' +
				'}';
	}
}
