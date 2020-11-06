package com.example.RedditClone.demo.Spring.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RedditClone.demo.Spring.model.SubReddit;

@Repository
public interface SubredditRepository extends JpaRepository<SubReddit, Long> {

	Optional<SubReddit> findByName(String subredditName);
}
