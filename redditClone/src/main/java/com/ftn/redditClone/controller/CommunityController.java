package com.ftn.redditClone.controller;

import antlr.Token;
import com.ftn.redditClone.model.dto.*;
import com.ftn.redditClone.model.entity.*;
import com.ftn.redditClone.security.TokenUtils;
import com.ftn.redditClone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModeratorService moderatorService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private DTOService dtoService;

    @Autowired
    private FlairService flairService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommunityDTO> findOne(@PathVariable int id) {
        Community community = communityService.findById(id);
        if (community.isSuspended == true) {
            return new ResponseEntity<>(new CommunityDTO(), HttpStatus.NOT_FOUND);
        } else {
            CommunityDTO communityDTO = new CommunityDTO(community);
            communityDTO.setModerators(dtoService.moderatorToDTO(community.getModerators()));
            return new ResponseEntity<>(communityDTO, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDTO>> findAllPostsForCommunity(@PathVariable int id) {

        Community community = communityService.findById(id);
        List<Post> posts = community.getPosts();
        List<Post> returnPosts = new ArrayList<>();
        for (Post post : posts) {
            if (!post.isDeleted()) {
                returnPosts.add(post);
            }
        }
        return new ResponseEntity<>(dtoService.postToDTO(returnPosts), HttpStatus.OK);
    }

    @GetMapping("{id}/posts/sort/{sortType}")
    public ResponseEntity<List<PostDTO>> findAllPostsForCommunitySorted(@PathVariable int id, @PathVariable String sortType) {
        List<Post> posts = new ArrayList<>();
        if (sortType.equals("Top")) {
            posts = postService.sortedPostsForCommunity(id, "Top");
        } else {
            posts = postService.sortedPostsForCommunity(id, "Hot");
        }
        return new ResponseEntity<>(dtoService.postToDTO(posts), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<CommunityDTO>> findAll() {

        List<Community> communities = communityService.findAll();
        List<Community> returnCommunities = new ArrayList<>();

        for (Community community : communities) {
            if (community.isSuspended == false)
                returnCommunities.add(community);
        }
        List<CommunityDTO> communityDTOs = dtoService.communityToDTO(returnCommunities);
        return new ResponseEntity<>(communityDTOs, HttpStatus.OK);
    }

    @GetMapping("{id}/flairs")
    public ResponseEntity<List<FlairDTO>> returnFlairsForCommunity(@PathVariable int id) {
        Community community = communityService.findById(id);
        List<Flair> returnFlairs = new ArrayList<>();
        for (Flair flair : community.getFlairs()) {
            if (!flair.isDeleted()) {
                returnFlairs.add(flair);
            }
        }
        return new ResponseEntity<>(dtoService.flairToDTO(returnFlairs), HttpStatus.OK);
    }

    @GetMapping("{id}/reports/{reportType}")
    public ResponseEntity<List<ReportDTO>> getReportsForCommunity(@PathVariable int id, @PathVariable int reportType) {

        Community community = communityService.findById(id);
        List<Report> reports = new ArrayList<>();
        for (Post post : community.getPosts()) {
            if (reportType == 1) {
                for (Comment comment : post.getComments()) {
                    for (Report report : comment.getReports()) {
                        if (report.getComment() != null && !report.isAccepted() && !comment.isDeleted()) {
                            reports.add(report);
                        }
                    }
                }
            } else {
                for (Report report : post.getReports()) {
                    if (report.getPost() != null && !report.isAccepted() && !post.isDeleted()) {
                        reports.add(report);
                    }
                }
            }
        }

        return new ResponseEntity<>(dtoService.reportToDTO(reports), HttpStatus.OK);
    }

    @PostMapping("{id}/flairs")
    public ResponseEntity<FlairDTO> addFlairToCommunity(@PathVariable int id, @RequestBody FlairDTO flairDTO, @RequestHeader("Authorization") String bearer) {
        Community community = communityService.findById(id);
        Set<Community> communities = new HashSet<>();
        communities.add(community);
        Flair flair = new Flair(flairDTO);
        flair.setCommunities(communities);
        community.getFlairs().add(flair);
        communityService.save(community);
        return new ResponseEntity<>(new FlairDTO(flair), HttpStatus.OK);
    }

    @DeleteMapping("{id}/flairs/{flairId}")
    public ResponseEntity<FlairDTO> deleteFlairCommunity(@PathVariable int id, @PathVariable int flairId, @RequestHeader("Authorization") String bearer) {
        Flair flair = flairService.findOne(flairId).get();
        flair.setDeleted(true);
        flairService.save(flair);
        return new ResponseEntity<>(new FlairDTO(flair), HttpStatus.OK);
    }

    @GetMapping("{id}/rules")
    public ResponseEntity<List<RuleDTO>> returnRulesForCommunity(@PathVariable int id) {
        Community community = communityService.findById(id);
        List<Rule> returnRules = new ArrayList<>();
        for (Rule rule : community.getRules()) {
            if (!rule.isDeleted()) {
                returnRules.add(rule);
            }
        }
        return new ResponseEntity<>(dtoService.ruleToDTO(returnRules), HttpStatus.OK);
    }

    @PostMapping("{id}/rules")
    public ResponseEntity<RuleDTO> addRulesToCommunity(@PathVariable int id, @RequestBody RuleDTO ruleDTO, @RequestHeader("Authorization") String bearer) {
        Community community = communityService.findById(id);
        Rule rule = new Rule(ruleDTO);
        rule.setCommunity(community);
        community.getRules().add(rule);
        communityService.save(community);
        return new ResponseEntity<>(new RuleDTO(rule), HttpStatus.OK);
    }

    @DeleteMapping("{id}/rules/{ruleId}")
    public ResponseEntity<RuleDTO> deleteRuleFromCommunity(@PathVariable int id, @PathVariable int ruleId, @RequestHeader("Authorization") String bearer) {
        Community community = communityService.findById(id);
        Rule rule = ruleService.findOne(ruleId).get();
        rule.setDeleted(true);
        rule.setCommunity(community);
        ruleService.save(rule);
        return new ResponseEntity<>(new RuleDTO(rule), HttpStatus.OK);
    }


    //	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<CommunityDTO> createCommunity(@RequestBody CommunityDTO communityDTO, @RequestHeader("Authorization") String bearer) {

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        communityDTO.setId(null);
        Community community = new Community(communityDTO);
        community = communityService.save(community);
        Moderator moderator = new Moderator(0, userService.findByUsername(username), community, false);
        List<Flair> flairs = new ArrayList<>();
        List<Rule> rules = new ArrayList<>();
        if (!communityDTO.getFlairs().isEmpty()) {
            for (FlairDTO flairDTO : communityDTO.getFlairs()) {
                flairs.add(new Flair(flairDTO));
            }
        }
        if (!communityDTO.getRules().isEmpty()) {
            for (RuleDTO ruleDTO : communityDTO.getRules()) {
                Rule rule = new Rule(ruleDTO);
                rule.setCommunity(community);
                rule.setDescription(ruleDTO.getDescription());
                rules.add(rule);
            }
        }
        community.setRules(rules);
        community.setFlairs(flairs);
        communityService.save(community);
        moderatorService.save(moderator);
        CommunityDTO communityDTO1 = new CommunityDTO(community);
        communityDTO1.setFlairs(dtoService.flairToDTO(flairs));
        List<RuleDTO> ruleDTOS = dtoService.ruleToDTO(rules);
        communityDTO1.setRules(ruleDTOS);
        return new ResponseEntity<>(communityDTO1, HttpStatus.OK);
    }

    @PostMapping("{id}/suspend")
    public ResponseEntity<CommunityDTO> suspendCommunity(@RequestHeader("Authorization") String bearer, @RequestBody CommunityDTO communityDTO) {

        String token = bearer.substring(7);
        String username = tokenUtils.getUsernameFromToken(token);
        Community community = communityService.findById(communityDTO.getId());
        community.isSuspended = true;
        community.suspendedReason = communityDTO.getSuspendedReason();
        communityService.save(community);
        for (Moderator moderator : community.getModerators()) {
            moderator.setDeleted(true);
            moderatorService.save(moderator);
        }
        for (Post post : community.getPosts()) {
            post.setDeleted(true);
            postService.save(post);
        }
        return new ResponseEntity<>(new CommunityDTO(community), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", value = "/{id}")
    public ResponseEntity<CommunityDTO> updateCommunity(@RequestBody CommunityDTO communityDTO, @PathVariable int id) {

        if (id == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Community community = communityService.findById(id);

            if (community.getName() != null) {
                community.setName(communityDTO.getName());
            }

            if (community.getDescription() != null) {
                community.setDescription(communityDTO.getDescription());
            }
            if (community.getSuspendedReason() != null) {
                community.setSuspendedReason(communityDTO.getSuspendedReason());
            }
            communityService.save(community);
            return new ResponseEntity<>(new CommunityDTO(community), HttpStatus.CREATED);
        }
    }

    @DeleteMapping(value = "delete", consumes = "application/json")
    public void deleteCommunity(@RequestBody CommunityDTO communityDTO) {
        communityService.deleteById(communityDTO.getId());
    }

}
