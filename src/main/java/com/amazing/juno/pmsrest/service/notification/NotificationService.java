package com.amazing.juno.pmsrest.service.notification;

import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    List<Notification> findAllWhereIsActiveAndIsDisplayedAre(boolean isActive, boolean isDisplayed);

    List<Notification> findAllWhereActiveIs(boolean isActive);

    List<Notification> listUnderTheCondition();

    Optional<ResponseSuccess> deleteUnderTheCondition();

    Optional<ResponseSuccess> saveUnderTheCondition();
}
