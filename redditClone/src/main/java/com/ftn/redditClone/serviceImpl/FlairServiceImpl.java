package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Flair;
import com.ftn.redditClone.repository.FlairRepository;
import com.ftn.redditClone.service.FlairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlairServiceImpl implements FlairService {

    @Autowired
    private FlairRepository flairRepository;

    @Override
    public void save(Flair flair) {
        flairRepository.save(flair);
    }

    @Override
    public Optional<Flair> findOne(int id) {
        return flairRepository.findById(id);
    }
}
