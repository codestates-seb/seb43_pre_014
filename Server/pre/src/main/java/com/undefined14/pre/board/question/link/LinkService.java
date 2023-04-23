package com.undefined14.pre.board.question.link;

import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface LinkService<T> {
    String createLinkHeader(Page<T> page, HttpServletRequest request, String baseUrl);
}
