package models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Role extends models.Entity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    //------------------------------------------------------------

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

//    public void addUser(User user) {
//        if (!getUsers().contains(user)) {
//            getUsers().add(user);
//        }
//        if (!user.getRoles().contains(this)) {
//            user.getRoles().add(this);
//        }
//    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }



    public Role() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
