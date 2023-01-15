package cog.caseStudy.qnaportal.qnaservice.service;

import cog.caseStudy.qnaportal.qnaservice.dto.PostRequest;
import cog.caseStudy.qnaportal.qnaservice.dto.PostResponse;
import cog.caseStudy.qnaportal.qnaservice.exceptions.PostNotFoundException;
import cog.caseStudy.qnaportal.qnaservice.exceptions.SubQnANotFoundException;
import cog.caseStudy.qnaportal.qnaservice.mapper.PostMapper;
import cog.caseStudy.qnaportal.qnaservice.model.Post;
import cog.caseStudy.qnaportal.qnaservice.model.SubQnA;
import cog.caseStudy.qnaportal.qnaservice.model.User;
import cog.caseStudy.qnaportal.qnaservice.repository.PostRepository;
import cog.caseStudy.qnaportal.qnaservice.repository.SubQnARepository;

import cog.caseStudy.qnaportal.qnaservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubQnARepository subQnARepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        SubQnA subQnA = subQnARepository.findByName(postRequest.getSubQnAName())
                .orElseThrow(() -> new SubQnANotFoundException(postRequest.getSubQnAName()));
        postRepository.save(postMapper.map(postRequest, subQnA, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubQnA(Long subQnAId) {
        SubQnA subQnA = subQnARepository.findById(subQnAId)
                .orElseThrow(() -> new SubQnANotFoundException (subQnAId.toString()));
        List<Post> posts = postRepository.findAllBySubQnA(subQnA);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
