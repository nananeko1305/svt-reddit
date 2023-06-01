package com.ftn.redditClone.elastic.mapper;

import com.ftn.redditClone.elastic.dto.CommunityElasticDTO;
import com.ftn.redditClone.elastic.dto.PostElasticDTO;
import com.ftn.redditClone.elastic.model.PostElastic;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public class PostElasticMapper {

    public static PostElasticDTO mapResponseDto(PostElastic postElastic) {
        return PostElasticDTO.builder()
                .id(postElastic.getId())
                .title(postElastic.getTitle())
                .text(postElastic.getText())
                .karma(postElastic.getKarma())
                .numberOfComments(postElastic.getNumberOfComments())
                .community(new CommunityElasticDTO(postElastic.getCommunityElastic()))
                .flair(postElastic.getFlairElastic())
                .pdfDescription(postElastic.getPdfDescription())
                .commentElasticList(postElastic.getCommentElasticList())
                .build();
    }

    public static List<PostElasticDTO> mapDtos(SearchHits<PostElastic> searchHits) {
        return searchHits
                .map(elasticCommunity -> mapResponseDto(elasticCommunity.getContent()))
                .toList();
    }
}
