package com.carlacoccia.repository;

import com.carlacoccia.entity.BlogPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Carla on 27.04.17.
 */

@Repository("blogPostRepository")
public interface BlogPostRepository extends CrudRepository<BlogPost,Long> {


}
