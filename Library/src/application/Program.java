package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.BooksDao;
import model.dao.DaoFactory;
import model.entities.Books;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		BooksDao booksDao = DaoFactory.createBooksDao();
		System.out.println("==== TEST 1: books findById ====");
		Books book = booksDao.findById(4);
		System.out.println(book);

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
		/*System.out.println("==== TEST 4: books insert ====");
		Books newBook = new Books(null, "Das Kapital", "fiction", "Karl Marx", 0.01, new Date(), new Date(), department);
		booksDao.insert(newBook);
		System.out.println("Inserted! New id = " + newBook.getId());
		
		/*System.out.println("==== TEST 4: books update ====");
		book = booksDao.findById(16);
		book.setGenre("History");
		booksDao.update(book);
		System.out.println("Update Completed!");*/
		
		System.out.println("==== TEST 6: book delete ====");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		booksDao.deleteById(id);
		System.out.println("Delete completed!");
		sc.close();
		
	}

}
