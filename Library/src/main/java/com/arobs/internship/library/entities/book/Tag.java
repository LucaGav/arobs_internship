package com.arobs.internship.library.entities.book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"tagName"})}
)
public class Tag {
    @Id
    @Column(name = "tagID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagID;

    @NotNull
    @Column(name = "tagName")
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Book> books = new HashSet<>();

    public Tag() {
    }

    public Tag(int tagID, String tagName) {
        this.tagID = tagID;
        this.tagName = tagName;
    }

    public Tag(@NotNull String tagName) {
        this.tagName = tagName;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public int getTagID() {
        return tagID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
