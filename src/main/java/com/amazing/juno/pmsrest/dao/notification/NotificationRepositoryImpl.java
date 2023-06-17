package com.amazing.juno.pmsrest.dao.notification;

import com.amazing.juno.pmsrest.common.PaginationResponseGenerator;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.mapper.NotificationMapper;
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
import static com.amazing.juno.pmsrest.common.CommonMethod.addUploadedDurationCondition;
import static com.amazing.juno.pmsrest.common.CommonMethod.removeLastMatchingWord;


@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationMapper notificationMapper;

    private final EntityManager entityManager;

    private final static String DATABASE_NAME = "notification";

    private final static String DATABASE_ALIAS = "noti";


    private final PaginationResponseGenerator paginationResponseGenerator;

    private String completeWhereClause;

    @Override
    @Transactional
    public NotificationFindUnderConditionResponseDTO findAllUnderCondition(UUID id,
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

        List<Notification> foundNotifications = paginationResponseGenerator.applyPagination(resultQuery, pageNumber, pageSize).getResultList();

        NotificationFindUnderConditionResponseDTO responseDTO = convertListOfNotificationToNotificationFindUnderConditionResponseDTO(
                foundNotifications
        );

        return (NotificationFindUnderConditionResponseDTO) paginationResponseGenerator.generateFindUnderConditionResponseDTO(pageNumber, pageSize, completeWhereClause,
               DATABASE_NAME, DATABASE_ALIAS, responseDTO );
    }


    private NotificationFindUnderConditionResponseDTO convertListOfNotificationToNotificationFindUnderConditionResponseDTO(List<Notification> notifications){

        NotificationFindUnderConditionResponseDTO notificationFindUnderConditionResponseDTO = new NotificationFindUnderConditionResponseDTO();
        notificationFindUnderConditionResponseDTO.setDataDTOs(
                notifications.stream().map(notificationMapper::notificationToNotificationDTO).toList()
        );

        return notificationFindUnderConditionResponseDTO;
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

        completeWhereClause = addUploadedDurationCondition(resultWhereClause,from, to,"noti");

       return basicSelectQuery + completeWhereClause;

    }



    private String addWhereClauseExceptForDurationCondition(UUID id,
                                                            String subject,
                                                            String body,
                                                            String imageUrl,
                                                            String videoUrl,
                                                            Boolean active,
                                                            Boolean displayed,
                                                            Integer version) {

        if(id == null && (subject == null || subject.isEmpty()) && (body == null || body.isEmpty()) &&
                (imageUrl == null || imageUrl.isEmpty()) &&
                (videoUrl == null || videoUrl.isEmpty()) &&
                active == null && displayed == null && version == null){
            return "";
        }

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
