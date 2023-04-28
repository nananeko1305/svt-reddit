package com.ftn.redditClone.elastic.repository;

import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.model.entity.Rule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityElasticRepository extends ElasticsearchRepository<CommunityElastic, Integer> {

    List<CommunityElastic> findAllByName(String name);

    List<CommunityElastic> findAllByDescription(String desc);

    List<CommunityElastic> findAllByRules(List<Rule> rules);


}
