package com.example.blog.Controller;

import com.example.blog.DTO.PostDTO;
import com.example.blog.Entity.Post;
import com.example.blog.Service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    PostController(PostService postService){
        this.postService=postService;
    }
    @PostMapping("/user/{userId}")
    public ResponseEntity<PostDTO> createPost(@PathVariable long userId, @RequestBody Post post){
        return ResponseEntity.status(201).body(postService.createPost(userId,post));
    }
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable long userId){
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }
    @GetMapping("/{id}")
        public ResponseEntity<PostDTO> getPost(@PathVariable long id){
            return ResponseEntity.ok(postService.getPostById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable long id,@RequestBody Post post){
        return ResponseEntity.ok(postService.updatePost(id,post));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
