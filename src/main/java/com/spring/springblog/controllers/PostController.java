package com.spring.springblog.controllers;

import com.spring.springblog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    //TODO: Create a PostController class. This controller should return strings
    // for the following routes that describe what will ultimately be there.
    @GetMapping("/posts")
    public String postsIndex(Model model){
        Post post1 = new Post("First Post", "This is my first post", 1);
        Post post2 = new Post("Second Post", "This is my 2nd post", 2);
        Post post3 = new Post("Third Post", "This is my 3rd post", 3);

        List<Post> postList = new ArrayList<>();
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);

        model.addAttribute("title", "All Posts");
        model.addAttribute("posts", postList);

        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postView(Model model){
        Post post = new Post("First Post", "This is my first post", 1);
        model.addAttribute("title", "Single Posts");
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String postForm(){
        return "Create a post here!";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(){
        return "Creating a new post...";
    }
}