package cog.caseStudy.qnaportal.qnaservice.service;

;
import cog.caseStudy.qnaportal.qnaservice.dto.CommentsDto;
import cog.caseStudy.qnaportal.qnaservice.exceptions.PostNotFoundException;
import cog.caseStudy.qnaportal.qnaservice.exceptions.SpringQnAException;
import cog.caseStudy.qnaportal.qnaservice.mapper.CommentMapper;
import cog.caseStudy.qnaportal.qnaservice.model.Comment;
import cog.caseStudy.qnaportal.qnaservice.model.NotificationEmail;
import cog.caseStudy.qnaportal.qnaservice.model.Post;
import cog.caseStudy.qnaportal.qnaservice.model.User;
import cog.caseStudy.qnaportal.qnaservice.repository.CommentRepository;
import cog.caseStudy.qnaportal.qnaservice.repository.PostRepository;
import cog.caseStudy.qnaportal.qnaservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringQnAException("Comments contains unacceptable language");
        }
        return false;
    }
}

