package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public List<Notification> findAllUnderCondition(UUID id,
                                                    String subject,
                                                    String body,
                                                    String imageUrl,
                                                    String videoUrl,
                                                    Boolean active,
                                                    Boolean displayed,
                                                    Integer version,
                                                    LocalDateTime from, LocalDateTime to) {

        String query ="SELECT noti" +
                " FROM notification noti " +
                "WHERE ";

        String whereClause = "";

        if(id != null){
            whereClause += String.format("noti.id='%s' AND",id);
        }

        if(subject != null && !subject.isEmpty()){
            whereClause += String.format("noti.subject='%s' AND",subject);
        }

        if(body != null && !body.isEmpty()){
            whereClause += String.format("noti.body='%s' AND",body);
        }

        if(imageUrl != null && !imageUrl.isEmpty()){
            whereClause += String.format("noti.imgUrl='%s' AND",imageUrl);
        }

        if(videoUrl != null && !videoUrl.isEmpty()){
            whereClause += String.format("noti.imgUrl='%s' AND",videoUrl);
        }

        if(active != null){
            whereClause += String.format("noti.active='%s' AND",active);
        }

        if(displayed != null){
            whereClause += String.format("noti.displayed='%s' AND",displayed);
        }

        if(version != null) {
            whereClause += String.format("noti.version='%s' AND",version);
        }

        // Remove last AND
        String resultWhereClause =removeLastMatchingWord("AND", whereClause);


        if(from != null && to != null){
            resultWhereClause += String.format("AND noti.uploaded BETWEEN '%s' AND '%s'",from, to);
        } else if(from != null){
            resultWhereClause += String.format("AND noti.uploaded BETWEEN '%s' AND '%s'",from, LocalDateTime.now());
        }

        String finalQuery = query + resultWhereClause;

        TypedQuery<Notification> resultQuery = entityManager.createQuery(finalQuery,Notification.class);

        return resultQuery.getResultList();
    }

    @Override
    @Transactional
    public UUID saveNotification(Notification notification) {
        entityManager.persist(notification);

        return notification.getId();
    }

    @Override
    @Transactional
    public UUID updateNotification(Notification notification) {
        Notification updatedNotification = entityManager.merge(notification);

        return updatedNotification.getId();
    }

    private String removeLastMatchingWord(String word, String text){
        int wordPointer = word.length() - 1;
        int textPointer = text.length() - 1;
        boolean firstMatchedFlag = false;
        int lastMatchedIndex = word.length() - 1;

        while(textPointer >= 0) {
            if(wordPointer == -1){
                break;
            }


            if(word.charAt(wordPointer) == text.charAt(textPointer)) {
                if (!firstMatchedFlag){
                    firstMatchedFlag = true;
                    lastMatchedIndex = textPointer;
                }
                wordPointer--;
            }


            textPointer--;
        }

        if(textPointer == -1){
            return text;
        }

        int firstMatchedIndex = textPointer + 1;

        return text.substring(0, firstMatchedIndex) + text.substring(lastMatchedIndex + 1);
    }


    @Override
    @Transactional
    public long count() {
        Query query = entityManager.createQuery("SELECT count(*) FROM notification");

        return (long) query.getSingleResult();
    }
}
