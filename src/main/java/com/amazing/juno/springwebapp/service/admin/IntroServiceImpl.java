package com.amazing.juno.springwebapp.service.admin;


import com.amazing.juno.springwebapp.dao.admin.IntroRepository;
import com.amazing.juno.springwebapp.entity.Introduction;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class IntroServiceImpl implements IntroService {

    private final IntroRepository introRepository;

    @Override
    @Transactional
    public void saveIntroduction(Introduction intro) {
        intro.setUploaded(LocalDateTime.now());
        introRepository.saveIntroduction(intro);
    }


    @Override
    @Transactional
    public List<Introduction> getAllIntroductionRecords(){
        return introRepository.getAllIntroductionRecords();
    }


    @Override
    @Transactional
    public Introduction getIntroductionById(UUID id){
        return introRepository.getIntroductionById(id);
    }

    @Override
    @Transactional
    public Introduction getRecentIntroduction() {
        return introRepository.getRecentIntroduction();
    }


}
