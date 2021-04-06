package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Books implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String genre;
	private String author;
	private Double marketPrice;
	private Date releaseDate;
	private Date donateDate;
	
	private Department department;
	
	public Books() {
		
	}
	public Books(Integer id, String name, String genre, String author, Double marketPrice, Date releaseDate,
			Date donateDate, Department department) {
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.author = author;
		this.marketPrice = marketPrice;
		this.releaseDate = releaseDate;
		this.donateDate = donateDate;
		this.department = department;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getDonateDate() {
		return donateDate;
	}
	public void setDonateDate(Date donateDate) {
		this.donateDate = donateDate;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Books other = (Books) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Books [id=" + id + ", name=" + name + ", genre=" + genre + ", author=" + author + ", marketPrice="
				+ marketPrice + ", releaseDate=" + releaseDate + ", donateDate=" + donateDate + ", department="
				+ department + "]";
	}	
}
