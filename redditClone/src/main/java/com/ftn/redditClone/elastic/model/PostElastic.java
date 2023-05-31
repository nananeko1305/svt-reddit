package com.ftn.redditClone.elastic.model;

import com.ftn.redditClone.model.entity.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(indexName = "posts")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class PostElastic {

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Integer)
    private Integer karma;

    @Field(type = FieldType.Integer)
    private Integer numberOfComments;

    @Field(type = FieldType.Object)
    private CommunityElastic communityElastic;

    @Field(type = FieldType.Object)
    private FlairElastic flairElastic;

    @Field(type = FieldType.Object)
    private List<CommentElastic> commentElasticList;

    @Field(type = FieldType.Text)
    private String pdfDescription;

    private String filename;

    public PostElastic(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.text = post.getText();
        if (post.getReactions() != null) {
            for(Reaction reaction: post.getReactions()){
                if(reaction.getReactionType() == ReactionType.UPVOTE){
                    this.karma++;
                }else {
                    this.karma--;
                }
            }
        }
        if(post.getComments() != null) {
            this.numberOfComments = post.getComments().size();
        }

        this.communityElastic = new CommunityElastic(post.getCommunity(),0,"");
        if (post.getFlair() != null) {
            this.flairElastic = new FlairElastic(post.getFlair());
        }

        if(post.getComments() != null) {
            List<CommentElastic> commentElastics = new ArrayList<>();
            for (Comment comment: post.getComments()){
                commentElastics.add(new CommentElastic(comment));
            }
            this.commentElasticList = commentElastics;
        }
        this.pdfDescription = "";
    }

}
