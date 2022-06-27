package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Flair;

import java.util.Optional;

public interface FlairService {

    void save(Flair flair);

    Optional<Flair> findOne(int id);
}
