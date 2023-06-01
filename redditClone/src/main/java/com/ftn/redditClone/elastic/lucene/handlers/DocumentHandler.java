package com.ftn.redditClone.elastic.lucene.handlers;

import com.ftn.redditClone.elastic.model.CommunityElastic;
import com.ftn.redditClone.elastic.model.PostElastic;

import java.io.File;

public abstract class DocumentHandler {
    public abstract CommunityElastic getIndexCommunity(File file);

    public abstract PostElastic getIndexPost(File file);

}
