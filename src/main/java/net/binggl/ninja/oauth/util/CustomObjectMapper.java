package net.binggl.ninja.oauth.util;

import ninja.lifecycle.Start;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomObjectMapper {

    @Inject 
    ObjectMapper objectMapper;

    @Start(order = 90)
    public void configureObjectMapper() {
        // Adding Joda Time parsing and rendering support to Jackson
        objectMapper.registerModule(new JodaModule());     
    }
}