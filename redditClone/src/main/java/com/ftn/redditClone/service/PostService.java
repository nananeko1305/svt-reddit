package com.ftn.redditClone.service;


import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.Reaction;

import java.util.List;

public interface PostService {

    public List<Post> findAll();

    public Post save(Post post);

    public void delete(Post post);

    public Post findById(int id);

    List<Post> sortedVote();

    List<Post> sortedDownvote();

    List<Post> sortedList(String sortType);

    List<Post> sortedPostsForCommunity(int communityId, String sortedType);


}
