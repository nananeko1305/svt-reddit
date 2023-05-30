package com.ftn.redditClone.elastic.lucene.handlers;

import com.ftn.redditClone.elastic.model.CommunityElastic;

import java.io.File;

public abstract class DocumentHandler {
    public abstract CommunityElastic getIndexUnit(File file);
}
