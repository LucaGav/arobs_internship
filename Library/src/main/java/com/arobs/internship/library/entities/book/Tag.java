package com.arobs.internship.library.entities.book;

import com.arobs.internship.library.entities.book.Book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @Column(name = "tagID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagID;

    @NotNull
    @Column
    private String tagDescription;

    @ManyToMany(mappedBy = "tags")
    private Set<Book> books = new HashSet<>();

    public Tag() {
    }

    public Tag(int tagID, String tagDescription) {
        this.tagID = tagID;
        this.tagDescription = tagDescription;
    }

    public Tag(@NotNull String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public int getTagID() {
        return tagID;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }
}
