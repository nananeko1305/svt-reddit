package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Banned;

public interface BannedService {

    void save(Banned banned);

    void delete(int id);
}
