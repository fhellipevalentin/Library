package model.dao;

import model.dao.Impl.BooksDaoJDBC;

public class DaoFactory {
	
	public static BooksDao createBooksDao() {
		return new BooksDaoJDBC();
	}
}
