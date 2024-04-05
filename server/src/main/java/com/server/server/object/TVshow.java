package com.server.server.object;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document("tvshows")
public class TVshow {

    @Id
    private String _id;
    private int id;
    private String title;
    private String imageUrl;
    private double imdbRating;
    private String intro;
    private List<String> starring;
    private List<String> genres;
    private int seasons;
    private double rentPrice;
    private double purchasePrice;
    private boolean is_featured;

    // No-argument constructor
    public TVshow() {
    }

    // All-argument constructor
    public TVshow(String _id, int id, String title, String imageUrl, double imdbRating,
            String intro, List<String> starring, List<String> genres,
            int seasons, double rentPrice, double purchasePrice, boolean is_featured) {
        this._id = _id;
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.imdbRating = imdbRating;
        this.intro = intro;
        this.starring = starring;
        this.genres = genres;
        this.seasons = seasons;
        this.rentPrice = rentPrice;
        this.purchasePrice = purchasePrice;
        this.is_featured = is_featured;

    }

    // Getters and Setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<String> getStarring() {
        return starring;
    }

    public void setStarring(List<String> starring) {
        this.starring = starring;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public boolean getis_featured() {
        return is_featured;
    }

    public void setis_featured(boolean is_featured) {
        this.is_featured = is_featured;
    }

    @Override
    public String toString() {
        return "TVShow{" +
                "_id='" + _id + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imdbRating=" + imdbRating +
                ", intro='" + intro + '\'' +
                ", starring=" + starring +
                ", genres=" + genres +
                ", seasons=" + seasons +
                ", rentPrice=" + rentPrice +
                ", purchasePrice=" + purchasePrice +
                ", is_featured=" + is_featured +
                '}';
    }
}
