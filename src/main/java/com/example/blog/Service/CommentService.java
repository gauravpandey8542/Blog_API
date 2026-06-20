package com.example.blog.Service;

import com.example.blog.DTO.CommentDTO;
import com.example.blog.Entity.Comment;
import com.example.blog.Entity.Post;
import com.example.blog.Entity.User;
import com.example.blog.Repository.CommentRepository;
import com.example.blog.Repository.PostRepository;
import com.example.blog.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

public class CommentService {
    private CommentDTO DTOConversion(Comment comment){
        CommentDTO dto=new CommentDTO();
        dto.setId(comment.getId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setContent(comment.getContent());
        dto.setPostId(comment.getPost().getId());
        dto.setUserId(comment.getUser().getId());
        return dto;
    }
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    CommentService(UserRepository userRepository,PostRepository postRepository,CommentRepository commentRepository){
        this.postRepository=postRepository;
        this.userRepository=userRepository;
        this.commentRepository=commentRepository;
    }
    public CommentDTO createComment(long userId, long postId, Comment comment){
        User user= userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No user with id "+userId+" found"));
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("No post with id "+postId+" found"));
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return DTOConversion(comment);
    }
    public List<CommentDTO> getCommentsByPostId(Long id){
        postRepository.findById(id).orElseThrow(()-> new RuntimeException("No post with id "+id+" found"));
        List<Comment> comments=commentRepository.findByPostId(id);
        List<CommentDTO> dtos=new ArrayList<>();
        for(Comment comment:comments){
            dtos.add(DTOConversion(comment));
        }
        return dtos;
    }
    public CommentDTO getCommentById(Long id){
        Comment comment= commentRepository.findById(id).orElseThrow(()-> new RuntimeException("No comment with id "+id+" found"));
        return DTOConversion(comment);
    }
    public CommentDTO updateComment(Long id,Comment updatedComment){
        Comment oldComment=commentRepository.findById(id).orElseThrow(()-> new RuntimeException("No comment with id "+id+" found"));
        oldComment.setContent(updatedComment.getContent());
        commentRepository.save(oldComment);
        return DTOConversion(oldComment);
    }
    public void deleteComment(long id){
        commentRepository.delete(commentRepository.findById(id).orElseThrow(()-> new RuntimeException("No comment with id "+id+" found")));
    }
}
