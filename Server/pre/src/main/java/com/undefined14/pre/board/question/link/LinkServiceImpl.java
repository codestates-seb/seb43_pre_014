package com.undefined14.pre.board.question.link;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Service
public class LinkServiceImpl<T> implements LinkService<T> {
    @Override
    public String createLinkHeader(Page<T> page, HttpServletRequest request, String baseUrl) {
        String fullUrl = baseUrl + request.getRequestURI();
        return fullUrl;
    }
    @Override
    public String createLinkHeader(Page<T> page, HttpServletRequest request) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(request.getRequestURI());
        StringBuilder linkHeader = new StringBuilder();

        //이전 페이지 링크
        if (page.hasPrevious()) {
            int previousPage = page.getNumber() - 1;
            linkHeader.append(buildLinkHeader(uriBuilder, "prev", previousPage, page.getSize()));
        }
        //다음 페이지 링크
        if (page.hasNext()) {
            int nextPage = page.getNumber() + 1;
            linkHeader.append(buildLinkHeader(uriBuilder, "next", nextPage, page.getSize()));
        }

        return linkHeader.toString();
    }

    private String buildLinkHeader(UriComponentsBuilder uriBuilder, String rel, int page, int size) {
        return "<" + uriBuilder.replaceQueryParam("page", page)
                .replaceQueryParam("size", size)
                .toUriString() + ">; rel=\"" + rel + "\" ,";
    }
}
