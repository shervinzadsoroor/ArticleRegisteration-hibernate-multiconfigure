package models;

import javax.persistence.*;

@Entity
//@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 50, unique = true)
    private String title;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    // constructors ==========================================

    public Category() {
    }

    public Category(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //end of constructors ====================================

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
