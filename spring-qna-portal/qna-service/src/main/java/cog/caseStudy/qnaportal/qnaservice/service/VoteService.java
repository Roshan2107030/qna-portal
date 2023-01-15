package cog.caseStudy.qnaportal.qnaservice.service;

import cog.caseStudy.qnaportal.qnaservice.dto.VoteDto;
import cog.caseStudy.qnaportal.qnaservice.exceptions.PostNotFoundException;
import cog.caseStudy.qnaportal.qnaservice.exceptions.SpringQnAException;
import cog.caseStudy.qnaportal.qnaservice.model.Post;
import cog.caseStudy.qnaportal.qnaservice.model.Vote;
import cog.caseStudy.qnaportal.qnaservice.repository.PostRepository;
import cog.caseStudy.qnaportal.qnaservice.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static cog.caseStudy.qnaportal.qnaservice.model.VoteType.UPVOTE;


@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringQnAException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
