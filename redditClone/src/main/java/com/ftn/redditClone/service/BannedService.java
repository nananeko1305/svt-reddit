package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Banned;

import java.util.List;
import java.util.Optional;

public interface BannedService {

    List<Banned> findAll();

    Optional<Banned> findOne(int id);

    void save(Banned banned);

    void delete(int id);
}
