package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Role_Tag",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    List<Tag> tags = new ArrayList<>();

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
