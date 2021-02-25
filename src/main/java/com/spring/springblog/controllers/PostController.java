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
    private final PostRepository postsDao;

    //TODO: Create a UserRepository interface (in repositories) and inject it
    // into the PostController.
    private final UserRepository usersDao;

    //constructor
    public PostController(PostRepository postsDao, UserRepository usersDao) {
        this.postsDao = postsDao;
        this.usersDao = usersDao;
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
        model.addAttribute("posts", postsDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String postView(@PathVariable long id, Model model){
        Post singlePost = postsDao.getOne(id);

        model.addAttribute("post", singlePost);
        model.addAttribute("title", singlePost.getTitle());

        return "posts/show";
    }

    //TODO: When a post is created, and before it is saved to the database, assign
    // a user to it. For now, it does not matter which user is assigned, so long
    // as some user is assigned. In the next lesson we will make this
    // functionality more robust.

    //TODO: Change your controller method for showing the post creation form to
    // actually show the form in create.html. This method should
    // pass a new (i.e. empty) Post object to the view.
    @GetMapping("/posts/create")
    public String showPostForm(Model model){
        model.addAttribute("title", "Creating Post");
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post){
        User user = usersDao.findAll().get(0);  //will replace with service
        post.setUser(user);
        Post savePost = postsDao.save(post);

        return "redirect:/posts";
    }

    //TODO: Implement the edit and delete functionality using forms to submit
    // these requests using @PostMapping annotations.
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id, Model model) {
        postsDao.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String editPostForm(@PathVariable long id, Model model) {
        Post singlePost = postsDao.getOne(id);
        model.addAttribute("title", "Edit Post");
        model.addAttribute("post", singlePost);

        return "posts/edit";
    }

    @PostMapping("/posts/update/{id}")
    public String updatePost(@ModelAttribute("post") Post singlePost, @PathVariable long id, Model model) {
        postsDao.save(singlePost);

        model.addAttribute("title", "Update Post");
        model.addAttribute("post", singlePost);

        return "redirect:/posts";
    }
}