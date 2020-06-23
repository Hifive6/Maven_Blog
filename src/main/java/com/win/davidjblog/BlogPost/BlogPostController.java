package com.win.davidjblog.BlogPost;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlogPostController {
    
    @Autowired
    private BlogPostRespository blogRepo;

    private static List<BlogPost> posts = new ArrayList<>();

    @GetMapping
    public String index(BlogPost blogPost, Model model){
        model.addAttribute("posts", posts);
        return "blogpost/index";
    }

    private BlogPost blogPost;

    @PostMapping
    public String addNewBlogPost(BlogPost blogPost, Model model){
        blogRepo.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));

        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());

        return "blogpost/result";
    }

    
}