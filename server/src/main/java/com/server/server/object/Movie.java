package com.server.server.object;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data               // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
@Document("movies")
public class Movie {
    @Id
    private String _id;
    private int id;
    private String title;
    private String imageUrl;
    private double imdbRating;
    private String intro;
    private List<String> starring;
    private List<String> genres;
    private int duration;
    private double rentPrice;
    private double purchasePrice;
    private boolean is_featured;
    private String type;

    public boolean getis_featured() {
        return is_featured;
    }

    public void setis_featured(boolean is_featured) {
        this.is_featured = is_featured;
    }
}