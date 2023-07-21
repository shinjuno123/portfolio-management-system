package com.amazing.juno.pmsrest.dao.notification;

import com.amazing.juno.pmsrest.common.BasicJpaMethods;
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

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import static com.amazing.juno.pmsrest.common.CommonMethod.addUploadedDurationCondition;
import static com.amazing.juno.pmsrest.common.CommonMethod.removeLastMatchingWord;


@Repository
public class NotificationRepositoryImpl extends BasicJpaMethods<Notification> implements NotificationRepository {

    private final NotificationMapper notificationMapper;


    private final static String DATABASE_NAME = "notification";

    private final static String DATABASE_ALIAS = "noti";


    private final PaginationResponseGenerator paginationResponseGenerator;

    private String completeWhereClause;

    public NotificationRepositoryImpl(EntityManager entityManager,
                                      NotificationMapper notificationMapper,
                                      PaginationResponseGenerator paginationResponseGenerator) {
        super(entityManager, Notification.class, DATABASE_NAME, DATABASE_ALIAS);
        this.notificationMapper = notificationMapper;
        this.paginationResponseGenerator = paginationResponseGenerator;
    }

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

    @Override
    @Transactional
    public List<Notification> listActiveAndDisplayedNotifications() {
        TypedQuery<Notification> resultQuery = entityManager.createQuery("SELECT noti FROM notification noti WHERE displayed=true AND active=true ORDER BY updated", Notification.class);

        return resultQuery.getResultList();
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
    public Optional<UUID> deleteById(UUID id) {
        return super.deleteById(id);
    }

    @Override
    public Notification save(Notification data) {
        return super.save(data);
    }

    @Override
    public Optional<Notification> update(Notification data, UUID id) {
        return super.update(data, id);
    }

    @Override
    public long count() {
        return super.count();
    }


}
