package com.stratio.pirates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Initializes the Spring Boot application.
 */
@SpringBootApplication
/* this overrides the component scan of the @SpringBootApplication */
@ComponentScan(excludeFilters = {
        /* this is copied from the @SpringBootApplication */
        @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        /* this excludes components annotated with @ComponentScanExclude */
        @Filter(ComponentScanExclude.class) })
@EnableAutoConfiguration
@EnableCaching
public class Application {

    /**
     * The base URI of the application.
     */
    public static final String BASE_URI = "/api/v1_0/";

    /**
     * The default timezone of the application.
     */
    public static final ZoneId DEFAULT_ZONE_ID = TimeZone.getTimeZone("Europe/Madrid").toZoneId();

    /**
     * Initializes the application.
     * @param args the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}