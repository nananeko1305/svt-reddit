package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Banned;
import com.ftn.redditClone.repository.BannedRepository;
import com.ftn.redditClone.service.BannedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BannedServiceImpl implements BannedService {

    @Autowired
    private BannedRepository bannedRepository;

    @Override
    public List<Banned> findAll() {
        return bannedRepository.findAll();
    }

    @Override
    public Optional<Banned> findOne(int id) {
        return bannedRepository.findById(id);
    }

    @Override
    public void save(Banned banned) {
        bannedRepository.save(banned);
    }


    @Override
    public void delete(int id) {
         bannedRepository.delete(id);
    }
}
