package com.ylqm.codeshelf.gateway.config;

import com.ylqm.codeshelf.gateway.globalFilter.Custom2GlobalFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration {

    @Bean
    public GlobalFilter customFilter() {

        return new Custom2GlobalFilter();

    }
}
