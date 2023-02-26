package de.ghse.forum.api;

import de.ghse.forum.api.request.CommentRequest;
import de.ghse.forum.api.response.CommentResponse;
import de.ghse.forum.model.Comment;
import de.ghse.forum.service.CommentService;
import de.ghse.forum.service.PostService;
import de.ghse.forum.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {
  private final CommentService commentService;
  private final UserService userService;
  private final PostService postService;

  Logger logger = LoggerFactory.getLogger(CommentController.class);

  @PostMapping(path = "/add/")
  public void addComment(@RequestBody CommentRequest commentRequest, Principal principal) {
    logger.info("Adding comment to post with id: " + commentRequest.getPost_id());
    try {

    commentService.addComment(
        Comment.builder()
            .content(commentRequest.getContent())
            .user(userService.findbyUsername(principal.getName()).orElseThrow())
            .post(
                postService.getPostById(UUID.fromString(commentRequest.getPost_id())).orElseThrow())
            .build());
    } catch (Exception e) {
        logger.error("Error adding comment" +e.getMessage());
    }
  }

  @GetMapping(path = "/get/{post_id}/{page}")
  public List<CommentResponse> getCommentsByPostByPage(
      @PathVariable("post_id") UUID post_id, @PathVariable("page") int page) {
    logger.info("Getting comments from post with id: " + post_id);
    return new CommentResponse().convert(commentService.findCommentByPostByPage(post_id, page));
  }
}
