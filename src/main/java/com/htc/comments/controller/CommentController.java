package com.htc.comments.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.htc.comments.CustomException.ResourceNotFoundException;
import com.htc.comments.entity.Comment;
import com.htc.comments.repo.CommentRepository;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	CommentRepository commentRepository;
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);


	@PostMapping("/comments")
	public Comment createComment(@RequestBody Comment comment) {
		if (comment.getCreatedAt() == null)
			comment.setCreatedAt(LocalDateTime.now());
		return commentRepository.save(comment);
	}

	@GetMapping("/comments/{id}")
	public Comment getCommentById(@PathVariable(value = "id") Long commentId) {
		logger.info("get comment by id : " + commentId);
		return commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
	}

	@PutMapping("/comments/{id}")
	public Comment updateComment(@PathVariable(value = "id") Long commentId, @RequestBody Comment commentDetails) {

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		comment.setPostName(commentDetails.getPostName());
		comment.setComment(commentDetails.getComment());
		comment.setUpdatedAt(LocalDateTime.now());

		Comment updatedComment = commentRepository.save(comment);
		return updatedComment;
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "id") Long commentId) {
		logger.info("deletecomment {}", commentId);
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		commentRepository.delete(comment);

		return ResponseEntity.ok().build();
	}

}
