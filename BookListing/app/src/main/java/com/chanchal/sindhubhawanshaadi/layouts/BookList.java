package com.chanchal.sindhubhawanshaadi.layouts;

public class BookList {

    private final String bookAuthor ;

    private final String bookTitle ;

    private final String pubDate;

    private final String Country ;

    private final double price ;

    private final String currencyCode ;

    private final String Image;

    private  String buyLink;


    public BookList(String bookAuthor, String bookTitle, String pubDate, String country, Double price, String currencyCode , String Image , String buyLink) {
        this.bookAuthor = bookAuthor;
        this.bookTitle = bookTitle;
        this.pubDate = pubDate;
        this.Country = country;
        this.price = price;
        this.currencyCode = currencyCode;
        this.Image = Image;
        this.buyLink = buyLink;
    }


    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getCountry() {
        return Country;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getImage() {
        return Image;
    }

    public String getBuyLink() {
        return buyLink;
    }

}
