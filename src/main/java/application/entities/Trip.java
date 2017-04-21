package application.entities;

import application.entities.converters.DateConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by diogo on 3/10/17.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Trip {


    private Long id;

    private String name;
    private String location;

    private Date startDate;
    private Date endDate;
    private User traveler;
    private Set<TripImage> images;

    @Transient
    public TripImage getCover(){

        if(images.isEmpty()) {
            TripImage tripImage = new TripImage();
            tripImage.setUrl("http://www.yzgeneration.com/wp-content/themes/Gonzo-2/images/no-image-featured-image.png");
            return tripImage;
        }
        return (TripImage) images.toArray()[0];
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<TripImage> getImages() {
        return images;
    }

    public void setImages(Set<TripImage> images) {
        this.images = images;
    }

    @ManyToOne
    @JoinColumn(name = "traveler_id")
    public User getTraveler() {
        return traveler;
    }

    public void setTraveler(User traveler) {
        this.traveler = traveler;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getStartDate() {
        return startDate;
    }


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

}
