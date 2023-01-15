package cog.caseStudy.qnaportal.qnaservice.dto;


import cog.caseStudy.qnaportal.qnaservice.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
