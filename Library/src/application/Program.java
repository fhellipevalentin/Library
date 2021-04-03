package application;

import model.dao.BooksDao;
import model.dao.DaoFactory;
import model.entities.Books;

public class Program {

	public static void main(String[] args) {
		
		BooksDao booksDao = DaoFactory.createBooksDao();
		
		Books books = booksDao.findById(3);
		System.out.println(books);
	}

}
