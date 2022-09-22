package de.ghse.forum.service;


import de.ghse.forum.model.Post;
import de.ghse.forum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void addPost(Post post){
        postRepository.save(post);
    }
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    public Optional<Post> getPostById(UUID id){
        return postRepository.findById(id);
    }
    public List<Post> getAllByTitleContaining(String title){ return postRepository.findAllByTitleContaining(title); }
    public void deletePost(UUID id){
        System.out.println("Delete Post with ID: " + id);
        postRepository.deleteById(id);
    }

    public void updatePost(UUID id, Post post){
        postRepository.deleteById(id);
        postRepository.save(post);
    }
}
