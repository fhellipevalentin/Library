package application;

import model.dao.BooksDao;
import model.dao.DaoFactory;

public class Program {

	public static void main(String[] args) {
		
		BooksDao booksDao = DaoFactory.createBooksDao();
	}

}
