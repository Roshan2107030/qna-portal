package cog.caseStudy.qnaportal.qnaservice.mapper;


import cog.caseStudy.qnaportal.qnaservice.dto.SubQnADto;
import cog.caseStudy.qnaportal.qnaservice.model.Post;
import cog.caseStudy.qnaportal.qnaservice.model.SubQnA;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubQnAMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subQnA.getPosts()))")
    SubQnADto mapSubQnAToDto(SubQnA subQnA);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubQnA mapDtoToSubQnA(SubQnADto subQnADto);
}
