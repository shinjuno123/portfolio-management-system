package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final EntityManager entityManager;

    private Integer PAGE_NUMBER = 1;
    private Integer PAGE_SIZE = 10;

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
                                                    LocalDateTime from, LocalDateTime to,
                                                    Integer pageNumber,
                                                    Integer pageSize) {


        String finalQuery = makeEntireDynamicQueryForSelectingNotification(id,
                subject,
                body,
                imageUrl,
                videoUrl,
                active,
                displayed,
                version,from,to);

        TypedQuery<Notification> resultQuery = entityManager.createQuery(finalQuery,Notification.class);


        return applyPagination(resultQuery, pageNumber, pageSize).getResultList();
    }



    private String makeEntireDynamicQueryForSelectingNotification(UUID id,
                                                                  String subject,
                                                                  String body,
                                                                  String imageUrl,
                                                                  String videoUrl,
                                                                  Boolean active,
                                                                  Boolean displayed,
                                                                  Integer version, LocalDateTime from, LocalDateTime to){
        String basicSelectQuery ="SELECT noti" +
                " FROM notification noti ";

        String whereClause = addWhereClauseExceptForDurationCondition(id, subject, body,
                imageUrl,
                videoUrl,
                active,
                displayed,version);


        // Remove last AND
        String resultWhereClause = removeLastMatchingWord("AND", whereClause);


       return basicSelectQuery + addUploadedDurationCondition(resultWhereClause,from, to);

    }

    private TypedQuery<Notification> applyPagination(TypedQuery<Notification> typedQuery,Integer pageNumber, Integer pageSize){
        if(pageNumber != null){
            PAGE_NUMBER = pageNumber;
        }
        if(pageSize != null){
            PAGE_SIZE = pageSize;
        }

        // Apply Pagination
        typedQuery.setFirstResult((PAGE_NUMBER - 1) * PAGE_SIZE);
        typedQuery.setMaxResults(PAGE_SIZE);

        return typedQuery;
    }

    private String addWhereClauseExceptForDurationCondition(UUID id,
                                                            String subject,
                                                            String body,
                                                            String imageUrl,
                                                            String videoUrl,
                                                            Boolean active,
                                                            Boolean displayed,
                                                            Integer version) {
        String whereClause = "WHERE ";

        if(id != null){
            whereClause += String.format("noti.id='%s' AND ",id);
        }

        if(subject != null && !subject.isEmpty()){
            whereClause += String.format("noti.subject='%s' AND ",subject);
        }

        if(body != null && !body.isEmpty()){
            whereClause += String.format("noti.body='%s' AND ",body);
        }

        if(imageUrl != null && !imageUrl.isEmpty()){
            whereClause += String.format("noti.imageUrl='%s' AND ",imageUrl);
        }

        if(videoUrl != null && !videoUrl.isEmpty()){
            whereClause += String.format("noti.videoUrl='%s' AND ",videoUrl);
        }

        if(active != null){
            whereClause += String.format("noti.active=%s AND ",active);
        }

        if(displayed != null){
            whereClause += String.format("noti.displayed=%s AND ",displayed);
        }

        if(version != null) {
            whereClause += String.format("noti.version=%d AND ",version);
        }

        return whereClause;
    }


    private String addUploadedDurationCondition(String resultWhereClause,LocalDateTime from, LocalDateTime to){
        if(from != null && to != null){
            resultWhereClause += String.format("AND noti.uploaded BETWEEN '%s' AND '%s'",from, to);
        } else if(from != null){
            resultWhereClause += String.format("AND noti.uploaded BETWEEN '%s' AND '%s'",from, LocalDateTime.now());
        }

        return resultWhereClause;
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
    public Notification saveNotification(Notification notification) {
        entityManager.persist(notification);

        return notification;
    }

    private Optional<Notification> findById(UUID id){
        TypedQuery<Notification> typedQuery = entityManager.createQuery("SELECT noti from notification noti WHERE noti.id=:id",Notification.class);
        typedQuery.setParameter("id", id);

        List<Notification> notification = typedQuery.getResultList();

        if(notification.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(notification.get(0));
    }



    // if id exists, return Optional<Notification>.
    // if not return Optional empty.
    @Override
    @Transactional
    public Optional<Notification> updateNotification(Notification notification) {
        Optional<Notification> savedNotificationOptional = findById(notification.getId());
        AtomicReference<Optional<Notification>> atomicReference = new AtomicReference<>();

        savedNotificationOptional.ifPresentOrElse(
                (savedNotification) -> {
                    Notification updatedNotification = entityManager.merge(notification);
                    atomicReference.set(
                            Optional.of(updatedNotification)
                    );
                },
                () -> {
                    atomicReference.set(Optional.empty());
                }
        );

        return atomicReference.get();
    }


    // if id exists, return Optional<Null>.
    // if not return Optional empty.
    @Override
    @Transactional
    public Optional<UUID> deleteNotificationById(UUID id) {
        Optional<Notification> savedNotificationOptional = findById(id);
        AtomicReference<Optional<UUID>> atomicReference = new AtomicReference<>();

        savedNotificationOptional.ifPresentOrElse(
                (savedNotification) -> {
                    entityManager.remove(savedNotification);
                    atomicReference.set(
                            Optional.of(savedNotification.getId())
                    );
                },
                () -> atomicReference.set(Optional.empty())

        );


        return atomicReference.get();
    }


    @Override
    @Transactional
    public long count() {
        Query query = entityManager.createQuery("SELECT count(*) FROM notification");

        return (long) query.getSingleResult();
    }
}
