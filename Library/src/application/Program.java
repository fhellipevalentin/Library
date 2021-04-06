package application;

import java.util.Date;
import java.util.List;

import model.dao.BooksDao;
import model.dao.DaoFactory;
import model.entities.Books;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		
		BooksDao booksDao = DaoFactory.createBooksDao();
		System.out.println("==== TEST 1: books findById ====");
		Books books = booksDao.findById(4);
		System.out.println(books);

		System.out.println("==== TEST 2: books findByDepartment ====");
		Department department = new Department(2, null);
		List<Books> list = booksDao.findByDepartment(department);
		for (Books obj : list) {
			System.out.println(obj);
		}
		System.out.println("==== TEST 3: books findAll ====");
		list = booksDao.findAll();
		for (Books obj : list) {
			System.out.println(obj);
		}
		System.out.println("==== TEST 4: books insert ====");
		Books newBook = new Books(null, "Harry Potter and The Secret Chamber", "Fantasy", "J.K.Rowling", 24.90, new Date(), new Date(), department);
		booksDao.insert(newBook);
		System.out.println("Inserted! New id = " + newBook.getId());
	}

}
