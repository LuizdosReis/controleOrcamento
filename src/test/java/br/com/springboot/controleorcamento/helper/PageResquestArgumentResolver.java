package br.com.springboot.controleorcamento.helper;

import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;

public class PageResquestArgumentResolver {

    public static SortHandlerMethodArgumentResolver sortArgumentResolver() {
        return new SortHandlerMethodArgumentResolver();
    }

    public static PageableHandlerMethodArgumentResolver pageRequestArgumentResolver() {
        return new PageableHandlerMethodArgumentResolver(sortArgumentResolver());
    }
}
