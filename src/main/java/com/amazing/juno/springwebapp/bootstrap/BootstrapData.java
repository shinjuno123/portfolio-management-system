package com.amazing.juno.springwebapp.bootstrap;

import com.amazing.juno.springwebapp.controller.api.AboutRestController;
import com.amazing.juno.springwebapp.controller.api.IntroRestController;
import com.amazing.juno.springwebapp.dao.AboutRepository;
import com.amazing.juno.springwebapp.dao.IntroRepository;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.dto.IntroDTO;
import com.amazing.juno.springwebapp.entity.About;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Component
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements CommandLineRunner {

    private final IntroRestController introRestController;

    private final IntroRepository introRepository;

    private final AboutRestController aboutRestController;

    private final AboutRepository aboutRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Bootstrap is loaded");
        saveDefaultIntroduction();
        saveDefaultAbout();
    }

    @Transactional
    public void saveDefaultIntroduction(){
        if(introRepository.count() < 1){
            introRestController.saveIntroduction(
                    IntroDTO.builder()
                            .sayHi("Hello, My name is")
                            .name("Junho Shin")
                            .opening("I'm a passionate developer who is always looking to learn to and grow. I believe that programming is not just a job, but a way of life, and I bring that passion to every project I work on.\n" +
                                    "\n" +
                                    "As a developer, I take great pride in writing clean, efficient, and maintainable code. I understand that software development is a team effort, and I value collaboration and communication with my colleagues to deliver high-quality solutions that meet the needs of our clients.\n" +
                                    "\n" +
                                    "Overall, I am a dedicated and enthusiastic developer who is always eager to take on new challenges and grow both professionally and personally.")
                            .build()
            );
        }
    }

    @Transactional
    public void saveDefaultAbout() {

        if(aboutRepository.count() < 1){
            aboutRepository.save(
                    About.builder()
                            .description("Allow me to introduce myself. I am a South Korean native who graduated with a degree in Computer Science and relocated to America after tying the knot. I have a deep passion for software development and currently seeking employment in America as a developer. My primary area of interest lies in crafting web applications as I find it both challenging and fulfilling.\n" +
                                    "\n" +
                                    "I first discovered my love for programming during my sophomore year of college when I created a desktop application that provided freshmen with essential information about our institution. During the project, I encountered an issue with crawling website images but managed to solve it by importing a form that takes an image source. This experience ignited my fascination with programming, leading me down the path to become a passionate developer.\n" +
                                    "\n" +
                                    "In summary, I am a motivated and determined individual with a background in computer science, seeking opportunities to contribute my problem-solving abilities to the world of software development.")
                            .name("Junho Shin")
                            .school("Konyang University")
                            .diploma("B.S in Computer Science")
                            .diplomaUrl("/api/public/files/images/image-category-diploma/file-name-diploma.pdf")
                            .period("Mar, 2017 ~ Feb, 2023")
                            .regionCountry("Daejeon, South Korea")
                            .faceImagePath("/api/public/files/images/image-category-about/file-name-test.jpg")
                            .build()

            );
        }
    }


}
