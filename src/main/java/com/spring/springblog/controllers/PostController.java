package com.spring.springblog.controllers;

import com.spring.springblog.models.Post;
import com.spring.springblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    //TODO: Use dependency injection to use an instance of the new Posts interface(PostRepository).
    // Dependency injection -> where we create a Repository instance and
    //  initialize it in the controller class constructor.
    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String postsIndex(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    //TODO: Create a PostController class. This controller should return strings
    // for the following routes that describe what will ultimately be there.
    @GetMapping("/posts/{id}")
    public String postView(@PathVariable long id, Model model){
        Post singlePost = postDao.getOne(id);

        model.addAttribute("post", singlePost);
        model.addAttribute("title", "Single Posts");

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

    //TODO: Implement the edit and delete functionality using forms to submit
    // these requests using @PostMapping annotations.
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        postDao.deleteById(id);
        return postsIndex(model);
    }

    @GetMapping("/posts/edit/{id}")
    public String editPostForm(@PathVariable long id, Model model) {
        Post singlePost = postDao.getOne(id);
        model.addAttribute("title", "Edit Post");
        model.addAttribute("post", singlePost);

        return "posts/edit";
    }

    @PostMapping("/posts/update/{id}")
    public String updatePost(@ModelAttribute("post") Post singlePost, @PathVariable long id, Model model) {
        postDao.save(singlePost);

        model.addAttribute("title", "Edit Post");
        model.addAttribute("post", singlePost);

        return postView(id, model);
    }
}