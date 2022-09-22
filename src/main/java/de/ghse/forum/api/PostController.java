package de.ghse.forum.api;

import de.ghse.forum.model.Post;
import de.ghse.forum.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequestMapping("api/v1")
@RestController
public class PostController {

    private final PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping(path = "/post")
    public void addPost(@Valid @NonNull @RequestBody Post post){
        postService.addPost(post);
    }

    @GetMapping(path = "/post")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping(path = "/post/{id}")
    public Post getPostById(@PathVariable("id") UUID id){
        return postService.getPostById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource"));
    }

    @DeleteMapping(path = "/post/{id}")
    public void deletePost(@PathVariable("id") UUID id){
        postService.deletePost(id);
    }

    @PutMapping(path = "/post/{id}")
    public void updatePost(@PathVariable("id") UUID id,@Valid @NonNull @RequestBody Post post){
        postService.updatePost(id, post);
    }
    @GetMapping(path = "/author/{author}/all")
    public List<Post> findPostsByAuthor(@PathVariable("author") String author) { return postService.findPostsByAuthor(author); }

    @GetMapping (path = "/title/{title}")
    public List<Post> findPostsByTitle(@PathVariable("title") String title) { return postService.findPostsByTitle(title); }
}
