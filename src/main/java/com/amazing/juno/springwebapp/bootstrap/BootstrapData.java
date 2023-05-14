package com.amazing.juno.springwebapp.bootstrap;

import com.amazing.juno.springwebapp.controller.api.FileRestController;
import com.amazing.juno.springwebapp.controller.api.IntroRestController;
import com.amazing.juno.springwebapp.dao.*;
import com.amazing.juno.springwebapp.dto.IntroDTO;
import com.amazing.juno.springwebapp.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;



@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class BootstrapData implements CommandLineRunner {

    private final IntroRestController introRestController;

    private final IntroRepository introRepository;

    private final AboutRepository aboutRepository;

    private final CertificationRepository certificationRepository;

    private final ProjectRepository projectRepository;

    private final PlatformRepository platformRepository;

    private final CategoryRepository categoryRepository;

    private final List<Platform> platforms = new ArrayList<>();

    private final List<Category> categories = new ArrayList<>();


    @Override
    public void run(String... args) {
        log.info("Bootstrap is loaded");
        saveDefaultIntroduction();
        saveDefaultAbout();
        saveDefaultCertification();
        saveDefaultProject();
        saveSkillSet();
    }

    private void saveDefaultIntroduction() {
        if (introRepository.count() < 1) {
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

    private void saveDefaultAbout() {

        if (aboutRepository.count() < 1) {
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
                            .diplomaUrl(FileRestController.PUBLIC_FILE_PATH + "/file-category-diploma/file-name-diploma.pdf")
                            .period("Mar, 2017 ~ Feb, 2023")
                            .regionCountry("Daejeon, South Korea")
                            .faceImagePath(FileRestController.PUBLIC_FILE_PATH + "/file-category-about/file-name-test.jpg")
                            .build()

            );
        }
    }

    private void saveDefaultCertification() {
        if (certificationRepository.count() < 1) {
            certificationRepository.save(
                    Certification.builder()
                            .name("AWS Solutions Architect - associate")
                            .downloadUrl(FileRestController.PUBLIC_FILE_PATH + "/file-category-certification/file-name-AWS-Certified-Solutions-Architect-certificate.pdf")
                            .build()
            );
        }
    }

    private void saveDefaultProject() {
        if (projectRepository.count() < 1) {
            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-category-project/file-name-208680381-ceda31c0-d274-47b0-bdf3-89b087dfb56e.png")
                            .projectName("Konyang University Crawler Service")
                            .url("https://github.com/shinjuno123/konyang-university-crawler-service")
                            .build()
            );

            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-category-project/file-name-208895331-7a96d273-bc9a-4530-8b99-0d36d2659ffd.png")
                            .projectName("App Recommending Solar power plants installation region")
                            .url("https://github.com/shinjuno123/solar-pick")
                            .build()
            );

            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-category-project/file-name-208895331-7a96d273-nn9a-4530-2399-0d36d2659ffd.png")
                            .projectName("Tomato Disease Detector")
                            .url("https://github.com/shinjuno123/tomato-disease-detector")
                            .build()
            );
        }
    }


    private void saveSkillSet() {
        if (platformRepository.count() < 1) {
            platforms.addAll(savePlatform());
            categories.addAll(saveCategories());

        }

    }


    private List<Platform> savePlatform() {
        Platform platform1 = new Platform();
        platform1.setName("Web");

        Platform platform2 = new Platform();
        platform2.setName("Mobile");

        Platform platform3 = new Platform();
        platform3.setName("Desktop");

        return Arrays.asList(

                platformRepository.save(
                        platform1
                ),
                platformRepository.save(
                        platform2
                ),
                platformRepository.save(
                        platform3
                )
        );


    }

    private List<Category> saveCategories() {

        for (int i = 0; i < platforms.size(); i++) {
            Platform platform = platforms.get(i);

            switch (platform.getName()) {
                case "Web" -> categories.addAll(saveCategoriesRelatedToWeb(platform));
                case "Mobile" -> categories.addAll(saveCategoriesRelatedToMobile(platform));
                case "Desktop" -> categories.addAll(saveCategoriesRelatedToDesktop(platform));
            }

            Platform savedPlatform = platformRepository.save(platform);

            platforms.set(i, savedPlatform);
        }

        return categories;
    }

    private Collection<? extends Category> saveCategoriesRelatedToDesktop(Platform platform) {
        Category category = new Category();
        category.setName("OS Independent");

        platform.getCategorySet().add(category);
        category.setPlatform(platform);

        Category savedCategory1 = categoryRepository.save(
                category
        );


        return List.of(savedCategory1);
    }

    private Collection<? extends Category> saveCategoriesRelatedToMobile(Platform platform) {
        Category category1 = new Category();
        category1.setName("Android");

        platform.getCategorySet().add(category1);
        category1.setPlatform(platform);

        Category savedCategory1 = categoryRepository.save(
                category1
        );


        return List.of(savedCategory1);
    }

    private Collection<? extends Category> saveCategoriesRelatedToWeb(Platform platform) {
        Category category1 = new Category();
        category1.setName("Frontend");


        platform.getCategorySet().add(category1);
        category1.setPlatform(platform);

        Category savedCategory1 = categoryRepository.save(
                category1
        );

        Category category2 = new Category();
        category2.setName("Backend");

        platform.getCategorySet().add(category2);
        category2.setPlatform(platform);


        Category savedCategory2 = categoryRepository.save(category2);

        return Arrays.asList(savedCategory1, savedCategory2);
    }


}
