package application;

import java.util.List;

import model.dao.BooksDao;
import model.dao.DaoFactory;
import model.entities.Books;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		
		BooksDao booksDao = DaoFactory.createBooksDao();
		System.out.println("==== TEST 1: books findById ====");
		Books books = booksDao.findById(3);
		System.out.println(books);

		System.out.println("==== TEST 2: books findByDepartment ====");
		Department department = new Department(1, null);
		List<Books> list = booksDao.findByDepartment(department);
		for (Books obj : list) {
			System.out.println(obj);
		}
	}

}
