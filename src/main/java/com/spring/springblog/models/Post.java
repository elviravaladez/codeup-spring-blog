package com.spring.springblog.models;

public class Post {
    //TODO: Create a Post class. This class will ultimately represent a POST record
    // from our database. The class should have private properties
    // and getters and setters for a title and body.

    //properties
    private long id;
    private String title;
    private String body;

    //constructors
    public Post() {
    }

    public Post(String title, String body, long id) {
        this.title = title;
        this.body = body;
        this.id = id;
    }

    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
