package com.spring.springblog.controllers;

import com.spring.springblog.models.Post;
import com.spring.springblog.models.User;
import com.spring.springblog.repositories.PostRepository;
import com.spring.springblog.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    //properties
    //TODO: Use dependency injection to use an instance of the new Posts interface(PostRepository).
    // Dependency injection -> where we create a Repository instance and
    //  initialize it in the controller class constructor.
    private final PostRepository postDao;

    //TODO: Create a UserRepository interface (in repositories) and inject it
    // into the PostController.
    private final UserRepository userDao;

    //constructor
    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    //methods
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "/home";
    }

    @GetMapping("/posts")
    public String postsIndex(Model model) {
        model.addAttribute("title", "Blog Posts");
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postView(@PathVariable long id, Model model){
        Post singlePost = postDao.getOne(id);

        model.addAttribute("post", singlePost);
        model.addAttribute("title", singlePost.getTitle());

        return "posts/show";
    }

    //TODO: When a post is created, and before it is saved to the database, assign
    // a user to it. For now, it does not matter which user is assigned, so long
    // as some user is assigned. In the next lesson we will make this
    // functionality more robust.
    @GetMapping("/posts/create")
    @ResponseBody
    public String postForm(Model model){
        Post newPost = new Post();
        model.addAttribute("title", "Creating Post");
        model.addAttribute("post", newPost);
        return "posts/create";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(@RequestParam String title, @RequestParam String body){
        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setBody(body);

        //Will throw if no users are in the database
        User user = userDao.findAll().get(0);
        newPost.setUser(user);

        postDao.save(newPost);

        return "redirect:/posts/" + newPost.getId();
    }

    //TODO: Implement the edit and delete functionality using forms to submit
    // these requests using @PostMapping annotations.
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        postDao.deleteById(id);
        return "redirect:/posts";
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

        model.addAttribute("title", "Update Post");
        model.addAttribute("post", singlePost);

        return "redirect:/posts";
    }
}