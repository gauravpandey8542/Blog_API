package com.example.blog.Controller;

import com.example.blog.DTO.CommentDTO;
import com.example.blog.Entity.Comment;
import com.example.blog.Service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    CommentController(CommentService commentService){
        this.commentService=commentService;
    }
    @PostMapping("/user/{userid}/post/{postid}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long userid, @PathVariable long postid, @RequestBody Comment comment){
        return ResponseEntity.status(201).body(commentService.createComment(userid,postid,comment));
    }
    @GetMapping("/post/{postid}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable long postid){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postid));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long id){
        return ResponseEntity.ok(commentService.getCommentById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable long id,@RequestBody Comment comment){
        return ResponseEntity.ok(commentService.updateComment(id,comment));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
 }
