package com.example.CodeFellowship.interfaces;

import com.example.CodeFellowship.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post , Long> {
}
