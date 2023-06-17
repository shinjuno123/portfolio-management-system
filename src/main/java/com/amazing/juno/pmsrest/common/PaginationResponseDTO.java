package com.amazing.juno.pmsrest.common;

import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import lombok.Data;

import java.util.List;

@Data
public abstract class PaginationResponseDTO<T> {
    private List<T> dataDTOs;

    Integer pageSize;

    Integer currentPage;

    Integer totalPage;

    boolean isLastPage;

    boolean isFirstPage;
}
