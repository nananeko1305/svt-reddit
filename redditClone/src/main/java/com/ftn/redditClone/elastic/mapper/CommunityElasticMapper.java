package com.ftn.redditClone.elastic.mapper;

import com.ftn.redditClone.elastic.dto.CommunityElasticDTO;
import com.ftn.redditClone.elastic.model.CommunityElastic;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public class CommunityElasticMapper {

    public static CommunityElasticDTO mapResponseDto(CommunityElastic communityElastic) {
        return CommunityElasticDTO.builder()
                .id(communityElastic.getId())
                .name(communityElastic.getName())
                .description(communityElastic.getDescription())
                .numberOfPosts(communityElastic.getNumberOfPosts())
                .averageKarma(communityElastic.getAverageKarma())
                .pdfDescription(communityElastic.getPdfDescription())
                .build();
    }

    public static List<CommunityElasticDTO> mapDtos(SearchHits<CommunityElastic> searchHits) {
        return searchHits
                .map(elasticCommunity -> mapResponseDto(elasticCommunity.getContent()))
                .toList();
    }

}
