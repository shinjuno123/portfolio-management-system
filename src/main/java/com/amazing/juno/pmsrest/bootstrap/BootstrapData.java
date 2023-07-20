package com.amazing.juno.pmsrest.bootstrap;

import com.amazing.juno.pmsrest.controller.api.v1.FileRestController;
import com.amazing.juno.pmsrest.controller.api.v1.IntroRestController;
import com.amazing.juno.pmsrest.dao.*;
import com.amazing.juno.pmsrest.dto.IntroDTO;
import com.amazing.juno.pmsrest.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private final FirstCategoryRepository firstCategoryRepository;

    private final SecondCategoryRepository secondCategoryRepository;

    private final ExperienceRepository experienceRepository;

    private final List<FirstCategory> firstCategories = new ArrayList<>();

    private final List<SecondCategory> categories = new ArrayList<>();

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    String dialect;


    @Override
    public void run(String... args) {
        log.debug(username);
        log.debug(password);
        log.debug(url);
        log.debug(driverClassName);
        log.debug(dialect);

        log.info("Bootstrap is loaded");
        saveDefaultIntroduction();
        saveDefaultAbout();
        saveDefaultCertification();
        saveDefaultProject();
        saveSkillSet();
        saveExperience();
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
                            .active(true)
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
                            .diplomaUrl(FileRestController.PUBLIC_FILE_PATH + "/file-categories/diploma/file-names/diploma.pdf")
                            .period("Mar, 2017 ~ Feb, 2023")
                            .regionCountry("Daejeon, South Korea")
                            .transcriptUrl(FileRestController.PUBLIC_FILE_PATH + "/file-categories/transcript/file-names/transcript.pdf")
                            .faceImagePath(FileRestController.PUBLIC_FILE_PATH + "/file-categories/about/file-names/test.jpg")
                            .active(true)
                            .build()

            );
        }
    }

    private void saveDefaultCertification() {
        if (certificationRepository.count() < 1) {
            certificationRepository.save(
                    Certification.builder()
                            .name("AWS Solutions Architect - associate")
                            .downloadUrl(FileRestController.PUBLIC_FILE_PATH + "/file-categories/certification/file-names/AWS-Certified-Solutions-Architect-certificate.pdf")
                            .build()
            );
        }
    }

    private void saveExperience(){
        if(experienceRepository.count() < 1){
            experienceRepository.save(
                    Experience.builder()
                            .title("KT Aivle School Tutor")
                            .company("KT")
                            .description("I have relevant work experience as a tutor at KT Aivle School, where I helped students with web programming. During my time there, I provided guidance to students on solving problems related to AWS server setting, Django, HTML, Javascript and Django REST API.\n" +
                                    "\n" +
                                    "One of my standout experiences as a tutor was when I assisted students in developing a website. The students were not aware that they needed to use deepcopy to avoid reflecting changes of properties to the view of the browser. With my knowledge and expertise, I was able to help them resolve this problem and continue their development.\n" +
                                    "\n" +
                                    "My time at KT Aivle School lasted from August 2022 to November 2022, during my final year of college. My work as a tutor helped me develop strong program-solving skills and an ability to communicate technical concepts effectively to others.\n" +
                                    "\n" +
                                    "Overall, my experience as a web programming tutor demonstrates my proficiency in a range of programming languages and my ability to mentor others. These skills make me an asset to any team and position me well for future opportunities in the field of software development.")
                            .imgPath(FileRestController.PUBLIC_FILE_PATH + "/file-categories/experience/file-names/aivler.png")
                            .positionName("Tutor")
                            .status("Part Time")
                            .workingPeriod("Aug, 2022 ~ Nov, 2022")
                            .build()
            );
        }
    }

    private void saveDefaultProject() {
        if (projectRepository.count() < 4) {
            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-categories/project/file-names/208680381-ceda31c0-d274-47b0-bdf3-89b087dfb56e.png")
                            .projectName("Konyang University Crawler Service")
                            .url("https://github.com/shinjuno123/konyang-university-crawler-service")
                            .build()
            );

            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-categories/project/file-names/208895331-7a96d273-bc9a-4530-8b99-0d36d2659ffd.png")
                            .projectName("App Recommending Solar power plants installation region")
                            .url("https://github.com/shinjuno123/solar-pick")
                            .build()
            );

            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-categories/project/file-names/208895331-7a96d273-nn9a-4530-2399-0d36d2659ffd.png")
                            .projectName("Tomato Disease Detector")
                            .url("https://github.com/shinjuno123/tomato-disease-detector")
                            .build()
            );
            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-categories/project/file-names/208680381-ceda31c0-d274-47b0-bdf3-89b087dfb56e.png")
                            .projectName("Konyang University Crawler Service")
                            .url("https://github.com/shinjuno123/konyang-university-crawler-service")
                            .build()
            );

            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-categories/project/file-names/208895331-7a96d273-bc9a-4530-8b99-0d36d2659ffd.png")
                            .projectName("App Recommending Solar power plants installation region")
                            .url("https://github.com/shinjuno123/solar-pick")
                            .build()
            );

            projectRepository.save(
                    Project.builder()
                            .imagePath(FileRestController.PUBLIC_FILE_PATH + "/file-categories/project/file-names/208895331-7a96d273-nn9a-4530-2399-0d36d2659ffd.png")
                            .projectName("Tomato Disease Detector")
                            .url("https://github.com/shinjuno123/tomato-disease-detector")
                            .build()
            );
        }
    }


    private void saveSkillSet() {
        if (firstCategoryRepository.count() < 1) {
            firstCategories.addAll(savePlatform());
            categories.addAll(saveCategories());

        }

    }


    private List<FirstCategory> savePlatform() {
        FirstCategory firstCategory1 = new FirstCategory();
        firstCategory1.setName("Web");

        FirstCategory firstCategory2 = new FirstCategory();
        firstCategory2.setName("Mobile");

        FirstCategory firstCategory3 = new FirstCategory();
        firstCategory3.setName("Desktop");

        return Arrays.asList(

                firstCategoryRepository.save(
                        firstCategory1
                ),
                firstCategoryRepository.save(
                        firstCategory2
                ),
                firstCategoryRepository.save(
                        firstCategory3
                )
        );


    }

    private List<SecondCategory> saveCategories() {

        for (int i = 0; i < firstCategories.size(); i++) {
            FirstCategory firstCategory = firstCategories.get(i);

            switch (firstCategory.getName()) {
                case "Web" -> categories.addAll(saveCategoriesRelatedToWeb(firstCategory));
                case "Mobile" -> categories.addAll(saveCategoriesRelatedToMobile(firstCategory));
                case "Desktop" -> categories.addAll(saveCategoriesRelatedToDesktop(firstCategory));
            }

            FirstCategory savedFirstCategory = firstCategoryRepository.save(firstCategory);

            firstCategories.set(i, savedFirstCategory);
        }

        return categories;
    }

    private Collection<? extends SecondCategory> saveCategoriesRelatedToDesktop(FirstCategory firstCategory) {
        SecondCategory secondCategory = new SecondCategory();
        secondCategory.setName("OS Independent");

        firstCategory.getSecondCategorySet().add(secondCategory);
        secondCategory.setFirstCategory(firstCategory);

        SecondCategory savedSecondCategory1 = secondCategoryRepository.save(
                secondCategory
        );


        return List.of(savedSecondCategory1);
    }

    private Collection<? extends SecondCategory> saveCategoriesRelatedToMobile(FirstCategory firstCategory) {
        SecondCategory secondCategory1 = new SecondCategory();
        secondCategory1.setName("Android");

        firstCategory.getSecondCategorySet().add(secondCategory1);
        secondCategory1.setFirstCategory(firstCategory);

        SecondCategory savedSecondCategory1 = secondCategoryRepository.save(
                secondCategory1
        );


        return List.of(savedSecondCategory1);
    }

    private Collection<? extends SecondCategory> saveCategoriesRelatedToWeb(FirstCategory firstCategory) {
        SecondCategory secondCategory1 = new SecondCategory();
        secondCategory1.setName("Frontend");


        firstCategory.getSecondCategorySet().add(secondCategory1);
        secondCategory1.setFirstCategory(firstCategory);

        SecondCategory savedSecondCategory1 = secondCategoryRepository.save(
                secondCategory1
        );

        SecondCategory secondCategory2 = new SecondCategory();
        secondCategory2.setName("Backend");

        firstCategory.getSecondCategorySet().add(secondCategory2);
        secondCategory2.setFirstCategory(firstCategory);


        SecondCategory savedSecondCategory2 = secondCategoryRepository.save(secondCategory2);

        return Arrays.asList(savedSecondCategory1, savedSecondCategory2);
    }


}
