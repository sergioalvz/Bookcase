package com.bookcase.model;

public class Book {
	private String author;
	private String title;
	private double rating;
	private BookStatus status;
	private String isbn;
	private String observations;

	public Book() {

	}

	public Book(String author, String title, double rating, BookStatus status) {
		super();
		this.author = author;
		this.title = title;
		this.rating = rating;
		this.status = status;
	}

	public String getAuthor() {
		return author;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getObservations() {
		return observations;
	}

	public double getRating() {
		return rating;
	}

	public BookStatus getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
