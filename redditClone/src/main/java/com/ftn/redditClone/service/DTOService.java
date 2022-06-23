package com.ftn.redditClone.service;

import com.ftn.redditClone.model.dto.*;
import com.ftn.redditClone.model.entity.*;

import java.util.ArrayList;
import java.util.List;

public interface DTOService {

    List<BannedDTO> bannedToDTO(List<Banned> banneds);

    List<CommentDTO> commentToDTO(List<Comment> comments);

    List<CommunityDTO> communityToDTO(List<Community> communities);

    List<FlairDTO> flairToDTO(List<Flair> flairs);

    List<ModeratorDTO> moderatorToDTO(List<Moderator> moderators);

    List<PostDTO> postToDTO(List<Post> posts);

    List<ReactionDTO> reactionToDTO(List<Reaction> reactions);

    List<ReportDTO> reportToDTO(List<Report> reports);

    List<RuleDTO> ruleToDTO(List<Rule> rules);

    List<UserDTO> userToDTO(List<User> users);



}
