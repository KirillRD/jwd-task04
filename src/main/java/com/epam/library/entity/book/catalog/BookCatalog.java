package com.epam.library.entity.book.catalog;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity of book for catalog
 */
public class BookCatalog implements Serializable {
    @Serial
    private static final long serialVersionUID = -2927555746128611818L;

    private int id;
    private String name;
    private List<String> authors = new ArrayList<>();
    private String publisher;
    private String city;
    private String type;
    private List<String> genres = new ArrayList<>();
    private int publicationYear;
    private int pages;
    private int part;
    private String isbn;
    private String issn;
    private String annotation;
    private double price;
    private String imageURL;
    private boolean bookIsUsed;
    private List<InstanceCatalog> freeInstanceCatalogList = new ArrayList<>();
    private List<HallInstanceCatalog> hallFreeInstanceCatalogList = new ArrayList<>();
    private double rating;
    private int countRatings;
    private int countComments;

    public BookCatalog() {}

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

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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

    public boolean isBookIsUsed() {
        return bookIsUsed;
    }

    public void setBookIsUsed(boolean bookIsUsed) {
        this.bookIsUsed = bookIsUsed;
    }

    public List<InstanceCatalog> getFreeInstanceCatalogList() {
        return freeInstanceCatalogList;
    }

    public void setFreeInstanceCatalogList(List<InstanceCatalog> freeInstanceCatalogList) {
        this.freeInstanceCatalogList = freeInstanceCatalogList;
    }

    public List<HallInstanceCatalog> getHallFreeInstanceCatalogList() {
        return hallFreeInstanceCatalogList;
    }

    public void setHallFreeInstanceCatalogList(List<HallInstanceCatalog> hallFreeInstanceCatalogList) {
        this.hallFreeInstanceCatalogList = hallFreeInstanceCatalogList;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCountRatings() {
        return countRatings;
    }

    public void setCountRatings(int countRatings) {
        this.countRatings = countRatings;
    }

    public int getCountComments() {
        return countComments;
    }

    public void setCountComments(int countComments) {
        this.countComments = countComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCatalog that = (BookCatalog) o;
        return id == that.id && publicationYear == that.publicationYear && pages == that.pages && part == that.part && Double.compare(that.price, price) == 0 && bookIsUsed == that.bookIsUsed && Double.compare(that.rating, rating) == 0 && countRatings == that.countRatings && countComments == that.countComments && Objects.equals(name, that.name) && Objects.equals(authors, that.authors) && Objects.equals(publisher, that.publisher) && Objects.equals(city, that.city) && Objects.equals(type, that.type) && Objects.equals(genres, that.genres) && Objects.equals(isbn, that.isbn) && Objects.equals(issn, that.issn) && Objects.equals(annotation, that.annotation) && Objects.equals(imageURL, that.imageURL) && Objects.equals(freeInstanceCatalogList, that.freeInstanceCatalogList) && Objects.equals(hallFreeInstanceCatalogList, that.hallFreeInstanceCatalogList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authors, publisher, city, type, genres, publicationYear, pages, part, isbn, issn, annotation, price, imageURL, bookIsUsed, freeInstanceCatalogList, hallFreeInstanceCatalogList, rating, countRatings, countComments);
    }

    @Override
    public String toString() {
        return "BookCatalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", publisher='" + publisher + '\'' +
                ", city='" + city + '\'' +
                ", type='" + type + '\'' +
                ", genres=" + genres +
                ", publicationYear=" + publicationYear +
                ", pages=" + pages +
                ", part=" + part +
                ", isbn='" + isbn + '\'' +
                ", issn='" + issn + '\'' +
                ", annotation='" + annotation + '\'' +
                ", price=" + price +
                ", imageURL='" + imageURL + '\'' +
                ", bookIsUsed=" + bookIsUsed +
                ", freeInstanceCatalogList=" + freeInstanceCatalogList +
                ", hallFreeInstanceCatalogList=" + hallFreeInstanceCatalogList +
                ", rating=" + rating +
                ", countRatings=" + countRatings +
                ", countComments=" + countComments +
                '}';
    }
}
