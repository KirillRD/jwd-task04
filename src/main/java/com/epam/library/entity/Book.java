package com.epam.library.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity of book
 */
public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = -359761412463639186L;

    private int id;
    private String name;
    private List<Integer> authorsID = new ArrayList<>();
    private int publisherID;
    private int typeID;
    private List<Integer> genresID = new ArrayList<>();
    private int publicationYear;
    private int pages;
    private int part;
    private String isbn;
    private String issn;
    private String annotation;
    private double price;
    private String imageURL;

    public Book() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAuthorsID() {
        return authorsID;
    }

    public void setAuthorsID(List<Integer> authorsID) {
        this.authorsID = authorsID;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public List<Integer> getGenresID() {
        return genresID;
    }

    public void setGenresID(List<Integer> genresID) {
        this.genresID = genresID;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && publisherID == book.publisherID && typeID == book.typeID && publicationYear == book.publicationYear && pages == book.pages && part == book.part && Double.compare(book.price, price) == 0 && Objects.equals(name, book.name) && Objects.equals(authorsID, book.authorsID) && Objects.equals(genresID, book.genresID) && Objects.equals(isbn, book.isbn) && Objects.equals(issn, book.issn) && Objects.equals(annotation, book.annotation) && Objects.equals(imageURL, book.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorsID, publisherID, typeID, genresID, publicationYear, pages, part, isbn, issn, annotation, price, imageURL);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorsID=" + authorsID +
                ", publisherID=" + publisherID +
                ", typeID=" + typeID +
                ", genresID=" + genresID +
                ", publicationYear=" + publicationYear +
                ", pages=" + pages +
                ", part=" + part +
                ", isbn='" + isbn + '\'' +
                ", issn='" + issn + '\'' +
                ", annotation='" + annotation + '\'' +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
