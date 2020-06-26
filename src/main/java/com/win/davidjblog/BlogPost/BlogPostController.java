package com.win.davidjblog.BlogPost;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogPostController {
    
    @Autowired
    private BlogPostRespository blogRepo;

    private static List<BlogPost> posts = new ArrayList<>();

    @GetMapping(value = "/")
    public String index(BlogPost blogPost, Model model){
        posts.removeAll(posts);
        for(BlogPost post : blogRepo.findAll()){
            posts.add(post);
        }
        model.addAttribute("posts", posts);
        return "blogpost/index";
    }

    private BlogPost blogPost;

    @GetMapping(value = "/blogposts/new")
    public String newBlog(BlogPost blogPost){
        return "blogpost/new";
    }


    @PostMapping(value = "/blogposts")
    public String addNewBlogPost(BlogPost blogPost, Model model){
        blogRepo.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));

        // add new blog posts as they're created to our posts list for indexing
        // posts.add(blogPost);


        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());

        return "blogpost/result";
    }

    // Similar to @PostMapping or @GetMapping, but allows for @PathVariable
    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    // Spring takes whatever value is in {id} and passes it to our method params using @PathVariable
    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model){
        // findById() returns and Optional<T> which can be null, so we have to test
        Optional<BlogPost> post = blogRepo.findById(id);
        // Test if post acutally has anything in it
        if(post.isPresent()){
            //UnWrap the post from Optional shell
            BlogPost actualPost = post.get();
            model.addAttribute("blogPost", actualPost);
        }

        return "blogpost/edit";
    }

    
}