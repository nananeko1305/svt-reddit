package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Moderator;
import com.ftn.redditClone.repository.ModeratorRepository;
import com.ftn.redditClone.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Override
    public List<Moderator> findAll() {
        return moderatorRepository.findAll();
    }

    @Override
    public void save(Moderator moderator) {
        moderatorRepository.save(moderator);
    }

    @Override
    public void deleteById(int id) {
        moderatorRepository.deleteById(id);
    }

    @Override
    public Optional<Moderator> findById(int id) {
        return moderatorRepository.findById(id);
    }

    @Override
    public void changePassword(String username, String newPassword) {

    }

    @Override
    public Moderator findByUsername(String username) {
        return null;
    }

    @Override
    public Moderator findByUserId(int id, int communityId) {
        return moderatorRepository.findByUserId(id, communityId);
    }
}
