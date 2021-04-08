package model.dao;

import db.DB;
import model.dao.Impl.BooksDaoJDBC;
import model.dao.Impl.DepartmentDaoJDBC;

public class DaoFactory {
	
	public static BooksDao createBooksDao() {
		return new BooksDaoJDBC(DB.getConnection());
	}
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
