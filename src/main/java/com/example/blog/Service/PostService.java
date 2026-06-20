package com.example.blog.Service;

import com.example.blog.DTO.PostDTO;
import com.example.blog.Entity.Post;
import com.example.blog.Entity.User;
import com.example.blog.Repository.PostRepository;
import com.example.blog.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private PostDTO ConversionDTO(Post post){
        PostDTO postDTO=new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUserId(post.getUser().getId());
        return postDTO;
    }
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository=postRepository;
        this.userRepository = userRepository;
    }

    public PostDTO createPost(Long id,Post post){
        User user= userRepository.findById(id).orElseThrow(()-> new RuntimeException("No user with "+id+" found"));
        post.setUser(user);
        postRepository.save(post);
        return ConversionDTO(post);
    }
    public List<PostDTO> getAllPosts(){
        List<Post> posts=postRepository.findAll();
        List<PostDTO> postDTO=new ArrayList<>();
        for(Post post:posts){
            postDTO.add(ConversionDTO(post));
        }
        return postDTO;
    }
    public PostDTO getPostById(Long id){
        Post post=postRepository.findById(id).orElseThrow(()-> new RuntimeException("No post with "+id+" found"));
        return ConversionDTO(post);
    }
    public List<PostDTO> getPostsByUser(Long userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No user with " + userId + " found"));
        List<Post> posts=postRepository.findByUserId(userId);
        List<PostDTO> postDTOS=new ArrayList<>();
        for(Post post:posts){
            postDTOS.add(ConversionDTO(post));
        }
        return postDTOS;
    }
    public PostDTO updatePost(long id,Post updatedpost){
        Post old_post=postRepository.findById(id).orElseThrow(()-> new RuntimeException("No post with "+id+" found"));
        old_post.setTitle(updatedpost.getTitle());
        old_post.setContent(updatedpost.getContent());
        postRepository.save(old_post);
        return ConversionDTO(old_post);
    }
    public void deletePost(Long post_id){
        postRepository.delete(postRepository.findById(post_id).orElseThrow(()-> new RuntimeException("No post with "+post_id+" found")));
    }
}
