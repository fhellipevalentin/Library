package model.dao;

import db.DB;
import model.dao.Impl.BooksDaoJDBC;

public class DaoFactory {
	
	public static BooksDao createBooksDao() {
		return new BooksDaoJDBC(DB.getConnection());
	}
}
