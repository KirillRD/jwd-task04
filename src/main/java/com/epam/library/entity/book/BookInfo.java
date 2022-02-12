package com.epam.library.entity.book;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity of entered book data
 */
public class BookInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -3303511427894732965L;

    private int id;
    private String name;
    private List<String> authorsID = new ArrayList<>();
    private String publisherID;
    private String typeID;
    private List<String> genresID = new ArrayList<>();
    private String publicationYear;
    private String pages;
    private String part;
    private String isbn;
    private String issn;
    private String annotation;
    private String price;
    private String imageURL;

    public BookInfo() {}

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

    public List<String> getAuthorsID() {
        return authorsID;
    }

    public void setAuthorsID(List<String> authorsID) {
        this.authorsID = authorsID;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public List<String> getGenresID() {
        return genresID;
    }

    public void setGenresID(List<String> genresID) {
        this.genresID = genresID;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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
        BookInfo bookInfo = (BookInfo) o;
        return id == bookInfo.id && Objects.equals(name, bookInfo.name) && Objects.equals(authorsID, bookInfo.authorsID) && Objects.equals(publisherID, bookInfo.publisherID) && Objects.equals(typeID, bookInfo.typeID) && Objects.equals(genresID, bookInfo.genresID) && Objects.equals(publicationYear, bookInfo.publicationYear) && Objects.equals(pages, bookInfo.pages) && Objects.equals(part, bookInfo.part) && Objects.equals(isbn, bookInfo.isbn) && Objects.equals(issn, bookInfo.issn) && Objects.equals(annotation, bookInfo.annotation) && Objects.equals(price, bookInfo.price) && Objects.equals(imageURL, bookInfo.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorsID, publisherID, typeID, genresID, publicationYear, pages, part, isbn, issn, annotation, price, imageURL);
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorsID=" + authorsID +
                ", publisherID='" + publisherID + '\'' +
                ", typeID='" + typeID + '\'' +
                ", genresID=" + genresID +
                ", publicationYear='" + publicationYear + '\'' +
                ", pages='" + pages + '\'' +
                ", part='" + part + '\'' +
                ", isbn='" + isbn + '\'' +
                ", issn='" + issn + '\'' +
                ", annotation='" + annotation + '\'' +
                ", price='" + price + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
