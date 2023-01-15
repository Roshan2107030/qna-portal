package cog.caseStudy.qnaportal.qnaservice.repository;

import cog.caseStudy.qnaportal.qnaservice.model.Post;
import cog.caseStudy.qnaportal.qnaservice.model.SubQnA;
import cog.caseStudy.qnaportal.qnaservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubQnA(SubQnA subQnA);

    List<Post> findByUser(User user);
}
