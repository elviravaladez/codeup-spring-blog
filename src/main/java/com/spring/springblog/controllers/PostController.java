package com.spring.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    //TODO: Create a PostController class. This controller should return strings
    // for the following routes that describe what will ultimately be there.
    @GetMapping("/posts")
    @ResponseBody

    //posts index page
    public String postsIndexPage() {
        return "This is the posts index page!";
    }

    //view an individual post
    @GetMapping("/posts/{id}")
    @ResponseBody
    public String individualPost(@PathVariable int id) {
        return "This is the page for post number " + id;
    }

    //view the form for creating a post
    @GetMapping("/posts/create")
    @ResponseBody
    public String ViewCreatePostForm() {
        return "This is the form for creating a post.";
    }

    //create a new post
    @PostMapping(path = "/posts/create")
    @ResponseBody
    public String createPost() {
        return "This is where you create a post!";
    }
}