package entities;

import javax.persistence.*;

@Entity
@Table(name = "Article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "brief", nullable = false)
    private String brief;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "createDate", nullable = false, length = 10)
    private String createDate;

    @Column(name = "lastUpdateDate", nullable = false, length = 10)
    private String lastUpdateDate;

    @Column(name = "publishDate", nullable = false, length = 10)
    private String publishDate;

    @Column(name = "isPublished", nullable = false)
    private boolean isPublished;

    @ManyToOne
    private User user;

    @OneToOne
    private Category category;

    //constructors ===========================================
    public Article() {
    }

    public Article(String title, String brief, String content, String createDate, String lastUpdateDate,
                   String publishDate, boolean isPublished, User user, Category category) {
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.publishDate = publishDate;
        this.isPublished = isPublished;
        this.user = user;
        this.category = category;
    }

    //========================================================


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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", content='" + content + '\'' +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", isPublished=" + isPublished +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}
