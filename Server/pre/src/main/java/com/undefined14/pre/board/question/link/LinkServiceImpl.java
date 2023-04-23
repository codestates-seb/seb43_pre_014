package com.undefined14.pre.board.question.link;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LinkServiceImpl<T> implements LinkService<T> {
    @Override
    public String createLinkHeader(Page<T> page, HttpServletRequest request, String baseUrl) {
        String fullUrl = baseUrl + request.getRequestURI();

        StringBuilder linkHeader = new StringBuilder();

        // 전체 데이터 수
        long totalElements = page.getTotalElements();

        // 현재 페이지 번호
        int currentPage = page.getNumber() + 1;

        // 한 페이지에 보여줄 데이터 수
        int pageSize = page.getSize();

        // 총 페이지 수
        int totalPages = page.getTotalPages();

        // 첫 페이지 링크
        linkHeader.append("<").append(fullUrl).append("?page=1&size=").append(pageSize).append(">; rel=\"first\", ");

        // 이전 페이지 링크
        if (currentPage > 1) {
            linkHeader.append("<").append(fullUrl).append("?page=").append(currentPage - 1).append("&size=").append(pageSize).append(">; rel=\"prev\", ");
        }

        // 다음 페이지 링크
        if (currentPage < totalPages) {
            linkHeader.append("<").append(fullUrl).append("?page=").append(currentPage + 1).append("&size=").append(pageSize).append(">; rel=\"next\", ");
        }

        // 마지막 페이지 링크
        linkHeader.append("<").append(fullUrl).append("?page=").append(totalPages).append("&size=").append(pageSize).append(">; rel=\"last\"");

        return linkHeader.toString();
    }

}
