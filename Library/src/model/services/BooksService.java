package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.BooksDao;
import model.entities.Books;

public class BooksService {
	
	private BooksDao dao = DaoFactory.createBooksDao();
	
	public List<Books> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Books obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	public void remove (Books obj) {
		dao.deleteById(obj.getId());
	}
} 