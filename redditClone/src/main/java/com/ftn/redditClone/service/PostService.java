package com.ftn.redditClone.service;


import com.ftn.redditClone.model.entity.Post;

import java.util.List;

public interface PostService {

    public List<Post> findAll();

    public Post save(Post post);

    public void deleteById(int id);

    public Post findById(int id);


}
