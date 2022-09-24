package de.ghse.forum.api;

import de.ghse.forum.model.Post;
import de.ghse.forum.service.PostService;
import de.ghse.forum.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequestMapping("api/v1")
@RestController
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping(path = "/post")
    public void addPost( @RequestBody  PostRequest postRequest){
        Post post = new Post(UUID.randomUUID(), postRequest.getTitle(), postRequest.getContent(), userService.findUserById(UUID.fromString(postRequest.getUser_id())).orElseThrow() , new Date().toString());
        postService.addPost(post);
    }



    @GetMapping(path = "/post")
    public List<PostResponse> getAllPosts(){
        return new PostResponse().convert(postService.getAllPosts());
    }

    @GetMapping(path = "/post/{id}")
    public PostResponse getPostById(@PathVariable("id") UUID id){
        return new PostResponse().convert(postService.getPostById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")));
    }

    @DeleteMapping(path = "/post/{id}")
    public void deletePost(@PathVariable("id") UUID id){
        postService.deletePost(id);
    }

    @PutMapping(path = "/post/{id}")
    public void updatePost(@PathVariable("id") UUID id,@Valid @NonNull @RequestBody Post post){
        postService.updatePost(id, post);
    }
    @GetMapping(path = "title/{title}")
    public List<PostResponse> getAllByTitleContaining(@PathVariable("title") String title){ return new PostResponse().convert(postService.getAllByTitleContaining(title)); }

    @RequestMapping(path = "/user/{id}/posts")
    public List<PostResponse> getAllByUser(@PathVariable("id") UUID id){
        return new PostResponse().convert(postService.getAllByUser(userService.findUserById(id).orElseThrow()));
    }

    @Data
    public static class PostRequest {
        private String title;
        private String content;
        private String user_id;
    }

    @Data
    public static class PostResponse {
        private UUID id;
        private String title;
        private String content;
        private UUID user_id;
        private String user_name;
        private String date;

        public List<PostResponse> convert(List<Post> allByUser) {
            List<PostResponse> postResponses = new ArrayList<>();
            for (Post post : allByUser) {
                PostResponse postResponse = new PostResponse();
                postResponse.setId(post.getId());
                postResponse.setTitle(post.getTitle());
                postResponse.setContent(post.getContent());
                postResponse.setUser_id(post.getUser().getId());
                postResponse.setUser_name(post.getUser().getUsername());
                postResponse.setDate(post.getDate());
                postResponses.add(postResponse);
            }
            return postResponses;
        }

        public PostResponse convert(Post post) {
            PostResponse postResponse = new PostResponse();
            postResponse.setId(post.getId());
            postResponse.setTitle(post.getTitle());
            postResponse.setContent(post.getContent());
            postResponse.setUser_id(post.getUser().getId());
            postResponse.setUser_name(post.getUser().getUsername());
            postResponse.setDate(post.getDate());
            return postResponse;
        }
    }

}
