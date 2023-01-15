package cog.caseStudy.qnaportal.qnaservice.mapper;


import cog.caseStudy.qnaportal.qnaservice.dto.CommentsDto;
import cog.caseStudy.qnaportal.qnaservice.model.Comment;
import cog.caseStudy.qnaportal.qnaservice.model.Post;
import cog.caseStudy.qnaportal.qnaservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
}