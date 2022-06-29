package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.Reaction;
import com.ftn.redditClone.repository.PostRepository;
import com.ftn.redditClone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post findById(int id) {
        return postRepository.findById(id).orElseGet(null);
    }

    @Override
    public List<Post> sortedList(String sortType) {
        if (sortType.equals("Top")) {
            return postRepository.sortedTop();
        } else {
            return postRepository.sortedHot();
        }
    }

    @Override
    public List<Post> sortedPostsForCommunity(int communityId, String sortedType) {
        if(sortedType.equals("Top")){
            return postRepository.sortedTopForCommunity(communityId);
        }else {
            return postRepository.sortedHotForCommunity(communityId);
        }
    }


}
