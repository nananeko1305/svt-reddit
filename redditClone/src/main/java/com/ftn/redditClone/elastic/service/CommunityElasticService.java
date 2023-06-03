package com.ftn.redditClone.elastic.service;

import com.ftn.redditClone.elastic.dto.*;
import com.ftn.redditClone.elastic.lucene.handlers.DocumentHandler;
import com.ftn.redditClone.elastic.lucene.handlers.PDFHandler;
import com.ftn.redditClone.elastic.mapper.CommunityElasticMapper;
import com.ftn.redditClone.elastic.mapper.PostElasticMapper;
import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.elastic.model.PostElastic;
import com.ftn.redditClone.elastic.repository.CommunityElasticRepository;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunityElasticService {

    //putanja do fajla
    @Value("${files.path}")
    private String filesPath;

    private final CommunityElasticRepository communityElasticRepository;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    public CommunityElasticService(CommunityElasticRepository communityElasticRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.communityElasticRepository = communityElasticRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    public CommunityElastic findById(int id){
        return communityElasticRepository.findById(id).get();
    }

    public void index(CommunityElastic community) {
        communityElasticRepository.save(community);
    }

    private SearchHits<CommunityElastic> searchBoolQuery(QueryBuilder boolQueryBuilder) {

        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();

        return elasticsearchRestTemplate.search(query, CommunityElastic.class, IndexCoordinates.of("communities"));
    }

    public List<CommunityElasticDTO> findAllByMultipleValues(MultipleValuesDTO multipleValuesDTO) {

        int br = 0;

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

// Pretraživanje po nazivu zajednice
        if (multipleValuesDTO.getName() != null && !multipleValuesDTO.getName().isEmpty()) {
            QueryBuilder nameQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(multipleValuesDTO.getSearchType(), new SimpleQueryEs("name", multipleValuesDTO.getName()));
            boolQueryBuilder.should(nameQuery);
            br++;
        }

// Pretraživanje po opisu zajednice
        if (multipleValuesDTO.getDescription() != null && !multipleValuesDTO.getDescription().isEmpty()) {
            QueryBuilder descriptionQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(multipleValuesDTO.getSearchType(), new SimpleQueryEs("description", multipleValuesDTO.getDescription()));
            boolQueryBuilder.should(descriptionQuery);
            br++;
        }

        if (multipleValuesDTO.getPdfDescription() != null && !multipleValuesDTO.getPdfDescription().isEmpty()) {
            QueryBuilder descriptionQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(multipleValuesDTO.getSearchType(), new SimpleQueryEs("pdfDescription", multipleValuesDTO.getPdfDescription()));
            boolQueryBuilder.should(descriptionQuery);
            br++;
        }

// Pretraživanje po opsegu broja objava
        if (multipleValuesDTO.getMinPosts() != null || multipleValuesDTO.getMaxPosts() != null) {
            RangeQueryBuilder postsRangeQuery = QueryBuilders.rangeQuery("numberOfPosts");
            if (multipleValuesDTO.getMinPosts() != null) {
                postsRangeQuery.gte(multipleValuesDTO.getMinPosts());
            }
            if (multipleValuesDTO.getMaxPosts() != null) {
                postsRangeQuery.lte(multipleValuesDTO.getMaxPosts());
            }
            boolQueryBuilder.should(postsRangeQuery);
            br++;
        }

// Pretraživanje po opsegu prosečne karme zajednice
        if (multipleValuesDTO.getMinKarma() != null || multipleValuesDTO.getMaxKarma() != null) {
            RangeQueryBuilder karmaRangeQuery = QueryBuilders.rangeQuery("averageKarma");
            if (multipleValuesDTO.getMinKarma() != null) {
                karmaRangeQuery.gte(multipleValuesDTO.getMinKarma());
            }
            if (multipleValuesDTO.getMaxKarma() != null) {
                karmaRangeQuery.lte(multipleValuesDTO.getMaxKarma());
            }
            boolQueryBuilder.should(karmaRangeQuery);
            br++;
        }

// Kombinacija prethodnih parametara pretrage
        if (!multipleValuesDTO.getSearchAccuracy().isEmpty() && multipleValuesDTO.getSearchAccuracy().equalsIgnoreCase("OR")) {
            boolQueryBuilder.minimumShouldMatch(1); // Jedan od must uslova mora biti zadovoljen
        } else {
            boolQueryBuilder.minimumShouldMatch(br); // Svi must uslovi moraju biti zadovoljeni
        }

        return CommunityElasticMapper.mapDtos(searchBoolQuery(boolQueryBuilder));

    }

    public List<CommunityElasticDTO> searchByRule(MultipleValuesDTO multipleValuesDTO) {

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder queryBuilder;

        if ("FUZZY".equalsIgnoreCase(multipleValuesDTO.getSearchType().toString())) {
            queryBuilder = QueryBuilders.matchQuery("rules.description", multipleValuesDTO.getRule())
                    .fuzziness(Fuzziness.AUTO);
        } else if ("PHRASE".equalsIgnoreCase(multipleValuesDTO.getSearchType().toString())) {
            queryBuilder = QueryBuilders.matchPhraseQuery("rules.description", multipleValuesDTO.getRule());
        } else {
            queryBuilder = QueryBuilders.termQuery("rules.description", multipleValuesDTO.getRule());
        }

        NativeSearchQuery searchQuery = searchQueryBuilder.withQuery(QueryBuilders.nestedQuery("rules", queryBuilder, ScoreMode.None)).build();

        SearchHits<CommunityElastic> searchHits = elasticsearchRestTemplate.search(searchQuery, CommunityElastic.class);

        return CommunityElasticMapper.mapDtos(searchHits);
    }


    public DocumentHandler getHandler(String fileName) {
        return getDocumentHandler(fileName);
    }

    public static DocumentHandler getDocumentHandler(String fileName) {
        if (fileName.endsWith(".pdf")) {
            return new PDFHandler();
        } else {
            return null;
        }
    }

    private String saveUploadedFileInFolder(MultipartFile file) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(new File(filesPath).getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }

    public String indexUploadedFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            if (multipartFile.isEmpty()) {
                return "";
            }
            String fileName = saveUploadedFileInFolder(multipartFile);
            if (fileName != null) {
                CommunityElastic communityElastic = getHandler(fileName).getIndexCommunity(new File(fileName));
                return communityElastic.getPdfDescription();
            }

        }
        return null;
    }


}


