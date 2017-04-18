package application.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> roles;
    private Set<Trip> trips;

    /**
     * Which user does this user follow
     */
    private Set<User> following;


    /**
     * Users who follow this user
     */
    private Set<User> followedBy;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "user_trip", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "trip_id"))
    public Set<Trip> getTrips(){ return trips;}



    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "follow_users", joinColumns = @JoinColumn(name = "followed_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
    public Set<User> getFollowing() {
        return following;
    }


    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "follow_users", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "followed_id"))
    public Set<User> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(Set<User> followedBy) {
        this.followedBy = followedBy;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}