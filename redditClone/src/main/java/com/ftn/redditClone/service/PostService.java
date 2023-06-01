package com.ftn.redditClone.service;


import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.Reaction;

import java.util.List;

public interface PostService {

     List<Post> findAll();
     Post save(Post post);

    void delete(Post post);

    Post findById(int id);

    List<Post> sortedList(String sortType);

    List<Post> sortedPostsForCommunity(int communityId, String sortedType);


}
