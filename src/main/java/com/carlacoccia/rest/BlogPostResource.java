package com.carlacoccia.rest;

import com.carlacoccia.entity.BlogPost;
import com.carlacoccia.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Carla on 27.04.17.
 */

@RestController
@Transactional
public class BlogPostResource {

    @Autowired
    private BlogPostRepository blogPostRepository;


    Logger logger  = LoggerFactory.getLogger(BlogPostResource.class);

    static final String CONTEXT = "/posts";



    /**
     * return a specific blog post
     * @param id The id of the requested blog post.
     * @return True on success, false on failure.
     */
    @RequestMapping(value = CONTEXT+"/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GetBlogPostObject getPost(@PathVariable Long id){

    BlogPost blogpost=blogPostRepository.findOne(id);

    return  new GetBlogPostObject(blogpost);
    }


    /**
     * Returns an object with the relevant blog post data.
     * @param -
     * @return An object with information about the blog (title, date, text).
     */
    @RequestMapping(value = CONTEXT, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GetBlogPostObject> getPosts(){
        Iterable<BlogPost> blogPosts= blogPostRepository.findAll();
        List<GetBlogPostObject> posts= new ArrayList<>();
        for(BlogPost blogPost:blogPosts){
            posts.add(new GetBlogPostObject(blogPost));
        }
        return posts;
    }

     /**
     * Create a new blog post and save it in the BlogPostRepository
      *@param data containing the title for the new blog post, a data as a String and the text
      *            of the blog post.
      *@return The id of the created blog post or null if there was an error.
      */
    @RequestMapping(value = CONTEXT, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestBody CreateBlogPostObject data){
        String dateString = data.getDate();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date date=new Date();
        try {
            date = df.parse(dateString);
            } catch (ParseException e) {
            e.printStackTrace();
        }  BlogPost blogPost= new BlogPost(data.getTitle(),date , data.getText());
        blogPostRepository.save(blogPost);
        Long id= blogPost.getId();
        logger.debug(id + ": post created.");
        return id;
    }




    public static class CreateBlogPostObject{
        private String title;
        private String date;
        private String text;

        public CreateBlogPostObject(String title,String date, String text){
            this.title=title;
            this.date=date;
            this.text=text;
        }

        public CreateBlogPostObject() {
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getText() {
            return text;
        }
    }

    public static class GetBlogPostObject{
        private String title;
        private String date;
        private String text;

        public GetBlogPostObject(BlogPost blogpost){
            this.title=blogpost.getTitle();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            this.date=df.format(blogpost.getDate());
            this.text=blogpost.getText();
        }

        public GetBlogPostObject() {
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getText() {
            return text;
        }
    }




}
