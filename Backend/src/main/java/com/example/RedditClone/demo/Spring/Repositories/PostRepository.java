package com.example.RedditClone.demo.Spring.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedditClone.demo.Spring.model.Post;
import com.example.RedditClone.demo.Spring.model.SubReddit;
import com.example.RedditClone.demo.Spring.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(SubReddit subreddit);
	
	List<Post> findAllByUser(User user);
}
