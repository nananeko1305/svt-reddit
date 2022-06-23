package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.dto.*;
import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.service.DTOService;
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
        List<CommunityDTO> communityDTO = new ArrayList<>();
        for (Community community: communities){
            communityDTO.add(new CommunityDTO(community));
        }
        return communityDTO;
    }

    @Override
    public List<FlairDTO> flairToDTO(List<Flair> flairs) {
        List<FlairDTO> flairDTO = new ArrayList<>();
        for (Flair flair: flairs){
            flairDTO.add(new FlairDTO(flair));
        }
        return flairDTO;
    }

    @Override
    public List<ModeratorDTO> moderatorToDTO(List<Moderator> moderators) {
        List<ModeratorDTO> moderatorDTO = new ArrayList<>();
        for (Moderator moderator: moderators){
            moderatorDTO.add(new ModeratorDTO(moderator));
        }
        return moderatorDTO;
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
            ruleDTO.add(new RuleDTO(rule));
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
