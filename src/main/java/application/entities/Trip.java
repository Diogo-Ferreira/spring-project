package application.entities;

import javax.persistence.*;
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


    private Set<User> users;


    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "user_trip", joinColumns = @JoinColumn(name = "trip_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Trip(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Trip() {}

    public Trip(String name, String location, Date startDate, Date endDate) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
