package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.dto.*;
import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.service.CommunityService;
import com.ftn.redditClone.service.DTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTOServiceImpl implements DTOService {



    @Override
    public List<BannedDTO> bannedToDTO(List<Banned> banneds) {
            List<BannedDTO> bannedsDTO = new ArrayList<>();
            for (Banned banned: banneds){
                bannedsDTO.add(new BannedDTO(banned));
            }
            return bannedsDTO;
    }

    @Override
    public List<CommentDTO> commentToDTO(List<Comment> comments) {
        List<CommentDTO> commentDTO = new ArrayList<>();
        for (Comment comment: comments){
            commentDTO.add(new CommentDTO(comment));
        }
        return commentDTO;
    }

    @Override
    public List<CommunityDTO> communityToDTO(List<Community> communities) {
        List<CommunityDTO> communityDTOs = new ArrayList<>();
        for (Community community: communities){
            CommunityDTO communityDTO = new CommunityDTO(community);
            communityDTOs.add(communityDTO);
        }
        return communityDTOs;
    }

    @Override
    public List<FlairDTO> flairToDTO(List<Flair> flairs) {
        List<FlairDTO> flairDTO = new ArrayList<>();
        for (Flair flair: flairs){
            FlairDTO flairDTO1 = new FlairDTO(flair);
            flairDTO.add(flairDTO1);
        }
        return flairDTO;
    }

    @Override
    public List<ModeratorDTO> moderatorToDTO(List<Moderator> moderators) {
        List<ModeratorDTO> moderatorDTOs = new ArrayList<>();
        for (Moderator moderator: moderators){
            ModeratorDTO moderatorDTO = new ModeratorDTO(moderator);
            moderatorDTOs.add(moderatorDTO);
        }
        return moderatorDTOs;
    }

    @Override
    public List<PostDTO> postToDTO(List<Post> posts) {
        List<PostDTO> postDTO = new ArrayList<>();
        for (Post post: posts){
            postDTO.add(new PostDTO(post));
        }
        return postDTO;
    }

    @Override
    public List<ReactionDTO> reactionToDTO(List<Reaction> reactions) {
        List<ReactionDTO> reactionDTO = new ArrayList<>();
        for (Reaction reaction: reactions){
            reactionDTO.add(new ReactionDTO(reaction));
        }
        return reactionDTO;
    }

    @Override
    public List<ReportDTO> reportToDTO(List<Report> reports) {
        List<ReportDTO> reportDTO = new ArrayList<>();
        for (Report report: reports){
            reportDTO.add(new ReportDTO(report));
        }
        return reportDTO;
    }

    @Override
    public List<RuleDTO> ruleToDTO(List<Rule> rules) {
        List<RuleDTO> ruleDTO = new ArrayList<>();
        for (Rule rule: rules){
            RuleDTO ruleDTO1 = new RuleDTO(rule);
            ruleDTO.add(ruleDTO1);
        }
        return ruleDTO;
    }

    @Override
    public List<UserDTO> userToDTO(List<User> users) {
        List<UserDTO> userDTO = new ArrayList<>();
        for (User user: users){
            userDTO.add(new UserDTO(user));
        }
        return userDTO;
    }
}
