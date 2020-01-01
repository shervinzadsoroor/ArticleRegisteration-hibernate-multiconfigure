package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToMany(mappedBy = "tags")
    private List<Article> articles = new ArrayList<>();

    public List<Article> getRoles() {
        return articles;
    }

    public void setRoles(List<Article> roles) {
        this.articles = roles;
    }

    //constructors-----------------------------------------------------

    public Tag() {
    }

    public Tag(String title) {
        this.title = title;
    }
    //-----------------------------------------------------------------

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
        return "Tag{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
