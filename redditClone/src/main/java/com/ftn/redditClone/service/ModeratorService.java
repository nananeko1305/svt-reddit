package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Moderator;

import java.util.List;
import java.util.Optional;

public interface ModeratorService {

    List<Moderator> findAll();

    void save(Moderator moderator);

    void deleteById(int id);

    Optional<Moderator> findById(int id);

    void changePassword(String username, String newPassword);

    Moderator findByUsername(String username);

    Moderator findByUserId(int id, int communityId);

}
