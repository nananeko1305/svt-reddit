package com.ftn.redditClone.elastic.service;

import com.ftn.redditClone.elastic.dto.MultipleValuesPostDTO;
import com.ftn.redditClone.elastic.dto.PostElasticDTO;
import com.ftn.redditClone.elastic.dto.SimpleQueryEs;
import com.ftn.redditClone.elastic.lucene.handlers.DocumentHandler;
import com.ftn.redditClone.elastic.lucene.handlers.PDFHandler;
import com.ftn.redditClone.elastic.mapper.CommunityElasticMapper;
import com.ftn.redditClone.elastic.mapper.PostElasticMapper;
import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.elastic.model.PostElastic;
import com.ftn.redditClone.elastic.repository.PostElasticRepository;
import com.ftn.redditClone.model.entity.Post;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.modelmapper.ModelMapper;
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
public class PostElasticService {

    @Value("${files.path}")
    private String filesPath;

    private PostElasticRepository postElasticRepository;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

   public PostElasticService(PostElasticRepository postElasticRepository, ElasticsearchRestTemplate elasticsearchRestTemplate) {
       this.postElasticRepository = postElasticRepository;
       this.elasticsearchRestTemplate = elasticsearchRestTemplate;
   }

   public void savePost(Post post, String pdfSTR) {
       PostElastic postElastic = new PostElastic(post);
       postElastic.setPdfDescription(pdfSTR);
       postElasticRepository.save(postElastic);
   }

    public void savePostElastic(PostElastic postElastic) {
        postElasticRepository.save(postElastic);
    }

   public PostElastic getOnePost(int id){
       return postElasticRepository.findById(Integer.valueOf(id)).get();
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
                PostElastic postElastic = getHandler(fileName).getIndexPost(new File(fileName));
                return postElastic.getPdfDescription();
            }

        }
        return null;
    }

    public List<PostElasticDTO> findAllByMultipleValues(MultipleValuesPostDTO multipleValuesPostDTO) {

        int br = 0;

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if( multipleValuesPostDTO.getText() != null){
            if (!multipleValuesPostDTO.getText().isEmpty()) {
                QueryBuilder descriptionQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(multipleValuesPostDTO.getSearchType(), new SimpleQueryEs("text", multipleValuesPostDTO.getText()));
                boolQueryBuilder.should(descriptionQuery);
                br++;
            }
        }

        if(multipleValuesPostDTO.getTitle() != null){
            if (!multipleValuesPostDTO.getTitle().isEmpty()) {
                QueryBuilder nameQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(multipleValuesPostDTO.getSearchType(), new SimpleQueryEs("title", multipleValuesPostDTO.getTitle()));
                boolQueryBuilder.should(nameQuery);
                br++;
            }
        }

        //postPDF
        if(multipleValuesPostDTO.getPdfDescription() != null){
            if (!multipleValuesPostDTO.getPdfDescription().isEmpty()) {
                QueryBuilder descriptionQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(multipleValuesPostDTO.getSearchType(), new SimpleQueryEs("pdfDescription", multipleValuesPostDTO.getPdfDescription()));
                boolQueryBuilder.should(descriptionQuery);
                br++;
            }
        }


        if (multipleValuesPostDTO.getMinComments() != null || multipleValuesPostDTO.getMaxComments() != null) {
            RangeQueryBuilder postsRangeQuery = QueryBuilders.rangeQuery("numberOfComments");
            if (multipleValuesPostDTO.getMinComments() != null) {
                postsRangeQuery.gte(multipleValuesPostDTO.getMinComments());
            }
            if (multipleValuesPostDTO.getMaxComments() != null) {
                postsRangeQuery.lte(multipleValuesPostDTO.getMaxComments());
            }
            boolQueryBuilder.should(postsRangeQuery);
            br++;
        }

        if (multipleValuesPostDTO.getMinKarma() != null || multipleValuesPostDTO.getMaxKarma() != null) {
            RangeQueryBuilder karmaRangeQuery = QueryBuilders.rangeQuery("karma");
            if (multipleValuesPostDTO.getMinKarma() != null) {
                karmaRangeQuery.gte(multipleValuesPostDTO.getMinKarma());
            }
            if (multipleValuesPostDTO.getMaxKarma() != null) {
                karmaRangeQuery.lte(multipleValuesPostDTO.getMaxKarma());
            }
            boolQueryBuilder.should(karmaRangeQuery);
            br++;
        }

        if (!multipleValuesPostDTO.getSearchAccuracy().isEmpty() && multipleValuesPostDTO.getSearchAccuracy().equalsIgnoreCase("OR")) {
            boolQueryBuilder.minimumShouldMatch(1); // Jedan od must uslova mora biti zadovoljen
        } else {
            boolQueryBuilder.minimumShouldMatch(br); // Svi must uslovi moraju biti zadovoljeni
        }

        return PostElasticMapper.mapDtos(searchBoolQuery(boolQueryBuilder));

    }

    public List<PostElasticDTO> searchByFlair(MultipleValuesPostDTO multipleValuesPostDTO) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        QueryBuilder nameQuery = SearchQueryGenerator.createMatchQueryBuilderTerm(multipleValuesPostDTO.getSearchType(), new SimpleQueryEs("flairElastic.name", multipleValuesPostDTO.getFlair()));
        boolQueryBuilder.should(nameQuery);
        searchBoolQuery(boolQueryBuilder);
        return PostElasticMapper.mapDtos(searchBoolQuery(boolQueryBuilder));
    }

    public List<PostElasticDTO> searchByCommentText(MultipleValuesPostDTO multipleValuesPostDTO) {

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder queryBuilder;

        if ("FUZZY".equalsIgnoreCase(multipleValuesPostDTO.getSearchType().toString())) {
            queryBuilder = QueryBuilders.matchQuery("commentElasticList.text", multipleValuesPostDTO.getCommentWordFind())
                    .fuzziness(Fuzziness.AUTO);
        } else if ("PHRASE".equalsIgnoreCase(multipleValuesPostDTO.getSearchType().toString())) {
            queryBuilder = QueryBuilders.matchPhraseQuery("commentElasticList.text", multipleValuesPostDTO.getCommentWordFind());
        } else {
            queryBuilder = QueryBuilders.termQuery("commentElasticList.text", multipleValuesPostDTO.getCommentWordFind());
        }

        NativeSearchQuery searchQuery = searchQueryBuilder.withQuery(queryBuilder).build();

        SearchHits<PostElastic> searchHits = elasticsearchRestTemplate.search(searchQuery, PostElastic.class);

        return PostElasticMapper.mapDtos(searchHits);
    }


    private SearchHits<PostElastic> searchBoolQuery(QueryBuilder boolQueryBuilder) {

        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();

        return elasticsearchRestTemplate.search(query, PostElastic.class, IndexCoordinates.of("posts"));
    }


}
