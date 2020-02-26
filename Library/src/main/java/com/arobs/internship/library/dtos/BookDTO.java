package com.arobs.internship.library.dtos;

import com.arobs.internship.library.entities.book.Tag;

import java.util.Date;
import java.util.Set;

public class BookDTO {

    private String title;
    private String author;
    private String description;
    private Date addedDate;
    private Set<TagDTO> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addDate) {
        this.addedDate = addDate;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }
}
