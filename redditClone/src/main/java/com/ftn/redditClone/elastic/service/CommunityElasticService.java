package com.ftn.redditClone.elastic.service;

import com.ftn.redditClone.elastic.dto.CommunityElasticAddDTO;
import com.ftn.redditClone.elastic.dto.CommunityElasticDTO;
import com.ftn.redditClone.elastic.dto.SimpleQueryEs;
import com.ftn.redditClone.elastic.lucene.handlers.DocumentHandler;
import com.ftn.redditClone.elastic.lucene.handlers.PDFHandler;
import com.ftn.redditClone.elastic.mapper.CommunityElasticMapper;
import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.elastic.repository.CommunityElasticRepository;
import com.ftn.redditClone.elastic.util.SearchType;
import com.ftn.redditClone.model.dto.MultipleValuesDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

@Service
public class CommunityElasticService {

    //putanja do fajla
    @Value("$[files.path]")
    private String filesPath;

    private final CommunityElasticRepository communityElasticRepository;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    public CommunityElasticService(CommunityElasticRepository communityElasticRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.communityElasticRepository = communityElasticRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }


    public List<CommunityElastic> findAllByDesc(String desc) {
        return communityElasticRepository.findAllByDescription(desc);
    }


    //pretraga po imenu i po tipu

    public List<CommunityElasticDTO> findAllByNameAndType(String name, SearchType searchType) {

        QueryBuilder nameQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(searchType, new SimpleQueryEs("name", name));

        BoolQueryBuilder boolQueryName = QueryBuilders.boolQuery().must(nameQuery);

        return CommunityElasticMapper.mapDtos(searchBoolQuery(boolQueryName));
    }

    //pretraga po tipu i po desc

    public List<CommunityElasticDTO> findAllByDescAndType(String desc, SearchType searchType) {

        QueryBuilder descQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(searchType, new SimpleQueryEs("description", desc));

        BoolQueryBuilder boolQueryDesc = QueryBuilders.boolQuery().must(descQuery);

        return CommunityElasticMapper.mapDtos(searchBoolQuery(boolQueryDesc));
    }

    public void index(CommunityElastic community) {
        communityElasticRepository.save(community);
    }

    private SearchHits<CommunityElastic> searchBoolQuery(QueryBuilder boolQueryBuilder) {

        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();

        return elasticsearchRestTemplate.search(query, CommunityElastic.class, IndexCoordinates.of("communities"));
    }

    public List<CommunityElasticDTO> findCommunitiesFromToPost(int from, int to) {
        String range = from + "-" + to;
        QueryBuilder numberOfPostsQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQueryEs("numberOfPosts", range));
        return CommunityElasticMapper.mapDtos(searchBoolQuery(numberOfPostsQuery));
    }

    public List<CommunityElasticDTO> findCommunitiesFromToAverageKarma(double from, double to) {
        String range = from + "-" + to;
        QueryBuilder numberOfPostsQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQueryEs("averageKarma", range));
        return CommunityElasticMapper.mapDtos(searchBoolQuery(numberOfPostsQuery));
    }

    public List<CommunityElasticDTO> findAllByNameAndDesc(MultipleValuesDTO multipleValuesDTO) {


        BoolQueryBuilder boolQuery = new BoolQueryBuilder();

        if (!multipleValuesDTO.getName().isEmpty()) {
            QueryBuilder nameQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryEs("name", multipleValuesDTO.getName()));
            boolQuery.must(nameQuery);
        }

        if (!multipleValuesDTO.getDescription().isEmpty()) {
            QueryBuilder descriptionQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryEs("description", multipleValuesDTO.getDescription()));
            boolQuery.must(descriptionQuery);
        }

        if (multipleValuesDTO.getAverageKarma() != 0.0) {
            QueryBuilder averageKarmaQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryEs("averageKarma", String.valueOf(multipleValuesDTO.getAverageKarma())));
            boolQuery.must(averageKarmaQuery);
        }

        if (!multipleValuesDTO.getNumberOfPosts().equals(0)) {
            QueryBuilder numberOfPostsQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQueryEs("numberOfPosts", String.valueOf(multipleValuesDTO.getNumberOfPosts())));
            boolQuery.must(numberOfPostsQuery);
        }

        System.out.println(boolQuery);


//        if (multipleValuesDTO.getName().equals("0") && !multipleValuesDTO.getDescription().equals("0")){
//            boolQueryNameAndDescription.must(descriptionQuery);
//        }
//            else
//            if (!multipleValuesDTO.getName().equals("0") && multipleValuesDTO.getDescription().equals("0")){
//            boolQueryNameAndDescription.must(nameQuery);
//        }
//            else
//            if (!multipleValuesDTO.getName().equals("0") && !multipleValuesDTO.getDescription().equals("0")) {
//            boolQueryNameAndDescription.must(nameQuery).must(descriptionQuery);
//        }
//            else
//            if (multipleValuesDTO.getName().equals("0") && multipleValuesDTO.getDescription().equals("0")) {
//            return null;
//        }

//        return CommunityElasticMapper.mapDtos(searchBoolQuery(boolQueryNameAndDescription));

        return null;
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

    public void indexUploadedFile(CommunityElasticAddDTO elasticCommunityDTO) throws IOException {
        if (elasticCommunityDTO.getFile() != null) {
                if (elasticCommunityDTO.getFile().isEmpty()) {
                   return;
                }

                String fileName = saveUploadedFileInFolder(elasticCommunityDTO.getFile());
                if (fileName != null) {
                    CommunityElastic communityIndexUnit = getHandler(fileName).getIndexUnit(new File(fileName));
                    communityIndexUnit.setNumberOfPosts(0);
                    communityIndexUnit.setAverageKarma(0.0);
                    index(communityIndexUnit);
                }

        } else {
            CommunityElastic communityIndexUnit = new CommunityElastic();
            communityIndexUnit.setId(elasticCommunityDTO.getId());
            communityIndexUnit.setName(elasticCommunityDTO.getName());
            communityIndexUnit.setDescription(elasticCommunityDTO.getDescription());
            communityIndexUnit.setNumberOfPosts(0);
            communityIndexUnit.setAverageKarma(0.0);
            communityIndexUnit.setPdfDescription(null);
            communityIndexUnit.setFilename(null);
            index(communityIndexUnit);
        }
    }

}


