package com.spring.springblog.controllers;

import com.spring.springblog.models.Post;
import com.spring.springblog.models.User;
import com.spring.springblog.repositories.PostRepository;
import com.spring.springblog.repositories.UserRepository;
import com.spring.springblog.services.EmailService;
import com.spring.springblog.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    private final UserService userService;
    private final EmailService emailService;
    //Note: If a property is final, it has to be injected through the constructor

    //constructor
    public PostController(PostRepository postsDao, UserRepository usersDao, UserService userService, EmailService emailService) {
        this.postsDao = postsDao;
        this.usersDao = usersDao;
        this.userService = userService;
        this.emailService = emailService;
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
        model.addAttribute("title", "Create Post");
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@Valid @ModelAttribute Post post, Errors validation, Model model){
        if(validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("post", post);
            model.addAttribute("title", "Error Creating Post");
            return "posts/create";
        }
        User user = userService.loggedInUser();
        post.setUser(user);
        Post savedPost = postsDao.save(post);

        //send an email once post is saved
        String subject = "New Post Created!";
        String body = "Dear " + savedPost.getUser().getUsername() + ",\n\nYou have successfully created a post. Woo-hoo! The ID for your new post titled \"" + savedPost.getTitle() + "\" is " + savedPost.getId() + ".\n\nHappy posting!";

        emailService.prepareAndSend(savedPost,subject, body);

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
    public String updatePost(@PathVariable long id, @Valid @ModelAttribute("post") Post singlePost, Errors validation, Model model) {
        if(validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("post", singlePost);
            model.addAttribute("title", "Error Updating Post");
            return "posts/edit";
        }

        User user = userService.loggedInUser();
        singlePost.setUser(user);
        postsDao.save(singlePost);

        return "redirect:/posts";
    }
}