package com.amazing.juno.pmsrest.common;

import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaginationResponseGenerator {

    private final EntityManager entityManager;

    private final static Integer PAGE_NUMBER = 1;
    private final static Integer PAGE_SIZE = 10;

    public <T> PaginationResponseDTO<?> generateFindUnderConditionResponseDTO(Integer pageNumber,
                                                                                    Integer pageSize,
                                                                                    String completeWhereClause,
                                                                                    String databaseName,
                                                                                    String databaseAlias,
                                                                                    PaginationResponseDTO<T> paginationResponseDTO) {



        int pageNumberResult = pageNumber != null? pageNumber: PAGE_NUMBER;
        int pageSizeResult = pageSize != null? pageSize: PAGE_SIZE;

        // Count number of notifications under the same condition
        Query queryTotal = entityManager.createQuery
                (String.format("Select count(%s.id) From %s %s ",databaseAlias,databaseName,databaseAlias) + completeWhereClause);


        // Get total number of items
        int totalItemCount = ((Long) queryTotal.getSingleResult()).intValue();

        // Get number of total pages.
        double numberOfTotalPages = Math.ceil((double) totalItemCount / pageSizeResult);

        // Define what page the request is at.
        boolean isFirstPage = numberOfTotalPages == 0 || (pageNumberResult <= numberOfTotalPages && pageNumberResult == 1);
        boolean isLastPage = numberOfTotalPages == 0 || (pageNumberResult <= numberOfTotalPages && pageNumberResult == numberOfTotalPages);

        // fill the response object.
        paginationResponseDTO.setTotalPage((int) numberOfTotalPages);
        paginationResponseDTO.setCurrentPage(pageNumberResult);
        paginationResponseDTO.setFirstPage(isFirstPage);
        paginationResponseDTO.setLastPage(isLastPage);
        paginationResponseDTO.setPageSize(pageSizeResult);

        return paginationResponseDTO;
    }


    public <T> TypedQuery<T> applyPagination(TypedQuery<T> typedQuery, Integer pageNumber, Integer pageSize) {

        int pageNumberResult = pageNumber != null? pageNumber: PAGE_NUMBER;
        int pageSizeResult = pageSize != null? pageSize: PAGE_SIZE;

        // Apply Pagination
        typedQuery.setFirstResult((pageNumberResult - 1) * pageSizeResult);
        typedQuery.setMaxResults(pageSizeResult);

        return typedQuery;
    }
}
