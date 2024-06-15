package com.keyuma.managementsystem.payload.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PagedApiResponse<T, U> extends ApiResponse<List<U>> {
    private Pagination pagination;

    public PagedApiResponse(boolean success, String message, Page<T> page, List<U> data) {
        super(success, message, data);
        this.pagination = new Pagination(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Getter
    @Setter
    public static class Pagination {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;

        public Pagination(int page, int size, long totalElements, int totalPages) {
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }
    }
}
