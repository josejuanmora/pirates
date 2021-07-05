package com.pirates.jpa;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for JPA.
 */
@Configuration
@ComponentScan(basePackageClasses = PiratesJpaConfig.class)
@EnableAutoConfiguration
public class PiratesJpaConfig {
}
