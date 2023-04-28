package com.ftn.redditClone.elastic.service;

import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.elastic.model.PostElastic;
import com.ftn.redditClone.elastic.repository.PostElasticRepository;
import com.ftn.redditClone.model.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostElasticService {

   public PostElasticRepository postElasticRepository;

   public PostElasticService(PostElasticRepository postElasticRepository) {
       this.postElasticRepository = postElasticRepository;
   }

   public void savePost(Post post) {

       PostElastic postElastic = new PostElastic();

       postElasticRepository.save(postElastic);
   }


}
