package com.ftn.redditClone.elastic.repository;

import com.ftn.redditClone.elastic.dto.PostElasticDTO;
import com.ftn.redditClone.elastic.model.PostElastic;
import com.ftn.redditClone.model.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface PostElasticRepository extends ElasticsearchRepository<PostElastic, Integer> {

    LinkedList<PostElastic> findAllByText(String text);

//    LinkedList<PostElastic> findAllByFlairElasticName(String flairName);

    LinkedList<PostElasticDTO> findAllByCommentElasticListText(String text);

}
