package org.chatline.service;

import org.chatline.domain.TimeLineFactory;
import org.chatline.domain.WallFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatterConfig {

    @Bean
    public TimeLineFactory timeLineFactory() {
        return new TimeLineFactory();
    }

    @Bean
    public WallFactory wallFactory() {
        return new WallFactory(timeLineFactory());
    }

}
