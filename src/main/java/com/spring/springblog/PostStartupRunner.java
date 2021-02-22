package com.spring.springblog;

import com.spring.springblog.models.Post;
import com.spring.springblog.models.User;
import com.spring.springblog.repositories.PostRepository;
import com.spring.springblog.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PostStartupRunner implements CommandLineRunner {

    private final UserRepository userDao;
    private final PostRepository postDao;

    public PostStartupRunner(UserRepository userDao, PostRepository postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
    }

    @Override
    public void run(String... args) throws Exception {
        // don't run if there's already any users in the database
//        if (userDao.count() != 0) {
//            return;
//        }
        User user = new User();
        user.setUsername("userTwo");
        user.setEmail("userTwo@email.com");
        user.setPassword("codeup");
        userDao.save(user);

        Post post = new Post();
        post.setTitle("Demo title");
        post.setBody("Demo body");
        post.setUser(user);
        postDao.save(post);
    }
}
