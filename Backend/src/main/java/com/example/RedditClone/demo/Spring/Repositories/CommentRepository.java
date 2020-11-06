package com.example.RedditClone.demo.Spring.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedditClone.demo.Spring.model.Comment;
import com.example.RedditClone.demo.Spring.model.Post;
import com.example.RedditClone.demo.Spring.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
