package com.ftn.redditClone.elastic.service;

import com.ftn.redditClone.elastic.model.CommentElastic;
import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.elastic.model.FlairElastic;
import com.ftn.redditClone.elastic.model.PostElastic;
import com.ftn.redditClone.elastic.repository.PostElasticRepository;
import com.ftn.redditClone.model.entity.Flair;
import com.ftn.redditClone.model.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostElasticService {

   public PostElasticRepository postElasticRepository;

   private ModelMapper mapper;

   public PostElasticService(PostElasticRepository postElasticRepository) {
       this.postElasticRepository = postElasticRepository;
   }

   public void savePost(Post post) {
       PostElastic postElastic = new PostElastic(post);
       postElasticRepository.save(postElastic);
   }

    public void savePostElastic(PostElastic postElastic) {
        postElasticRepository.save(postElastic);
    }

   public PostElastic getOnePost(int id){
       return postElasticRepository.findById(Integer.valueOf(id)).get();
   }


}
