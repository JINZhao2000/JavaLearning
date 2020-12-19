package Cours1.DI;

public class BookServiceImpl implements BookService{
	private BookDao bookDao;

	public void setBookDao (BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void addBook() {
		this.bookDao.addBook();
	}

}
