package com.amazing.juno.springwebapp.service.admin;


import com.amazing.juno.springwebapp.dao.admin.IntroRepository;
import com.amazing.juno.springwebapp.entity.Introduction;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class IntroServiceImpl implements IntroService {

    private final IntroRepository introRepository;

    @Override
    @Transactional
    public void saveIntroduction(Introduction intro) {
        introRepository.saveIntroduction(intro);
    }
}
