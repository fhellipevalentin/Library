package model.dao;

import java.util.List;

import model.entities.Books;

public interface BooksDao {
	void insert(Books obj);
	void update(Books obj);
	void deleteById(Integer obj);
	Books findById(Integer id);
	List<Books> findAll();
}
