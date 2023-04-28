package com.ftn.redditClone.elastic.repository;

import com.ftn.redditClone.elastic.model.PostElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface PostElasticRepository extends ElasticsearchRepository<PostElastic, Integer> {

    LinkedList<PostElastic> findAllByText(String text);

}
