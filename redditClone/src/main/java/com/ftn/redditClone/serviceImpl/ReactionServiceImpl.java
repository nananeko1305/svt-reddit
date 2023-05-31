package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.elastic.model.PostElastic;
import com.ftn.redditClone.elastic.service.CommunityElasticService;
import com.ftn.redditClone.elastic.service.PostElasticService;
import com.ftn.redditClone.model.entity.Post;
import com.ftn.redditClone.model.entity.Reaction;
import com.ftn.redditClone.model.entity.ReactionType;
import com.ftn.redditClone.repository.ReactionRepository;
import com.ftn.redditClone.service.CommunityService;
import com.ftn.redditClone.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final CommunityElasticService communityElasticService;
    private final PostElasticService postElasticService;

    private final CommunityService communityService;


    public ReactionServiceImpl(ReactionRepository reactionRepository, CommunityElasticService communityElasticService, PostElasticService postElasticService, CommunityService communityService) {
        this.reactionRepository = reactionRepository;
        this.communityElasticService = communityElasticService;
        this.postElasticService = postElasticService;
        this.communityService = communityService;
    }

    @Override
    public void saveReaction(Reaction reaction) {
        Reaction reaction1 = reactionRepository.save(reaction);
        PostElastic postElastic = postElasticService.getOnePost(reaction1.getPost().getId());
        if(reaction1.getReactionType() == ReactionType.UPVOTE){
            if(postElastic.getKarma() == null){
                postElastic.setKarma(1);
            }else{
                postElastic.setKarma(postElastic.getKarma() + 1);
            }
        }else {
            if(postElastic.getKarma() == null){
                postElastic.setKarma(-1);

            }else{
                postElastic.setKarma(postElastic.getKarma() - 1);
            }
        }
        postElasticService.savePostElastic(postElastic);
        CommunityElastic communityElastic = communityElasticService.findById(postElastic.getCommunityElastic().getId());
        communityElastic.setAverageKarma(communityService.getAverageCarmaForCommunity(communityElastic.getId()));
        communityElasticService.index(communityElastic);
    }

    @Override
    public List<Reaction> alreadyVoted(int userID, int postID) {
        return reactionRepository.alreadyVoted(userID, postID);
    }

    @Override
    public List<Reaction> alreadyVotedComment(int userID, int commentID) {
        return reactionRepository.alreadyVotedComment(userID, commentID);
    }

    @Override
    public void delete(Reaction reaction) {
        reactionRepository.delete(reaction);
    }
}
