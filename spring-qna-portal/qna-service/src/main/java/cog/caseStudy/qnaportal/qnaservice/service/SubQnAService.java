package cog.caseStudy.qnaportal.qnaservice.service;

import cog.caseStudy.qnaportal.qnaservice.dto.SubQnADto;
import cog.caseStudy.qnaportal.qnaservice.exceptions.SpringQnAException;
import cog.caseStudy.qnaportal.qnaservice.mapper.SubQnAMapper;
import cog.caseStudy.qnaportal.qnaservice.model.SubQnA;
import cog.caseStudy.qnaportal.qnaservice.repository.SubQnARepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubQnAService {

    private final SubQnARepository subQnARepository ;
    private final SubQnAMapper subQnAMapper;

    @Transactional
    public SubQnADto save(SubQnADto subQnADto) {
        SubQnA save = subQnARepository.save(subQnAMapper.mapDtoToSubQnA(subQnADto));
        subQnADto.setId(save.getId());
        return subQnADto;
    }

    @Transactional(readOnly = true)
    public List<SubQnADto> getAll() {
        return subQnARepository.findAll()
                .stream()
                .map(subQnAMapper::mapSubQnAToDto)
                .collect(toList());
    }

    public SubQnADto getSubQnA(Long id) {
        SubQnA subQnA = subQnARepository.findById(id)
                .orElseThrow(() -> new SpringQnAException("No subQnA found with ID - " + id));
        return subQnAMapper.mapSubQnAToDto(subQnA);
    }
}
