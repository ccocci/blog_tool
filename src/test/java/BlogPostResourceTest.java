import com.carlacoccia.entity.BlogPost;
import com.carlacoccia.repository.BlogPostRepository;
import com.carlacoccia.rest.BlogPostResource;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Carla on 28.04.17.
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.carlacoccia.Application.class)
public class BlogPostResourceTest {

    @Autowired
    private BlogPostResource blogPostResource;
    @Autowired
    private BlogPostRepository blogPostRepository;


    @Rule
    public ExpectedException thrown=ExpectedException.none();

    @Test
    public void createPostTest(){

        Long id=blogPostResource.createPost(new BlogPostResource.CreateBlogPostObject("testTitle","28/04/17","This is a test."));
        BlogPost blogPost=blogPostRepository.findOne(id);
        Assert.assertEquals("testTitle",blogPost.getTitle());
        Assert.assertEquals(new Date(2017-1900,4-1,28),blogPost.getDate());
        Assert.assertEquals("This is a test.",blogPost.getText());

        Long id2=blogPostResource.createPost(new BlogPostResource.CreateBlogPostObject("testTitle","28-04-17","This is a test."));
        BlogPost blogPost2=blogPostRepository.findOne(id);
        Assert.assertEquals("testTitle",blogPost2.getTitle());
        Assert.assertEquals(new Date(2017-1900,4-1,28),blogPost2.getDate());
        Assert.assertEquals("This is a test.",blogPost2.getText());

        Long id3=blogPostResource.createPost(new BlogPostResource.CreateBlogPostObject("testTitle","28.04.17","This is a test."));
        BlogPost blogPost3=blogPostRepository.findOne(id);
        Assert.assertEquals("testTitle",blogPost3.getTitle());
        Assert.assertEquals(new Date(2017-1900,4-1,28),blogPost3.getDate());
        Assert.assertEquals("This is a test.",blogPost3.getText());


        // Assert Month must exist
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Matchers.containsString("valid"));
        Long response = blogPostResource.createPost(new BlogPostResource.CreateBlogPostObject("testTitle","2/18/17","This is a test that should catch an exception."));


        // Assert game Day must exist
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Matchers.containsString("valid"));
        response = blogPostResource.createPost(new BlogPostResource.CreateBlogPostObject("testTitle","53/2/17","This is a test that should catch an exception."));

        // Assert special caracters are caught
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(Matchers.containsString("valid"));
        response = blogPostResource.createPost(new BlogPostResource.CreateBlogPostObject("testTitle","-3/*/17","This is a test that should catch an exception."));

    }
}
