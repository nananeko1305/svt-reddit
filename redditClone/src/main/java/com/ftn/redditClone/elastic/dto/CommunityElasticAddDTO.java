package com.ftn.redditClone.elastic.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@NoArgsConstructor
@Setter
public class CommunityElasticAddDTO {

    private int id;
    private String name;
    private String description;
    private MultipartFile file;


}
