package cog.caseStudy.qnaportal.qnaservice.repository;

import cog.caseStudy.qnaportal.qnaservice.model.SubQnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubQnARepository extends JpaRepository<SubQnA, Long> {

    Optional<SubQnA> findByName(String subQnAName);
}
