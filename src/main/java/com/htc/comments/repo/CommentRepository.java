package com.htc.comments.repo;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.htc.comments.entity.Comment;





@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
