package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Banned;
import com.ftn.redditClone.repository.BannedRepository;
import com.ftn.redditClone.service.BannedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannedServiceImpl implements BannedService {

    @Autowired
    private BannedRepository bannedRepository;

    @Override
    public void save(Banned banned) {
        bannedRepository.save(banned);
    }

    @Override
    public void delete(int id) {
        bannedRepository.deleteById(id);
    }
}
