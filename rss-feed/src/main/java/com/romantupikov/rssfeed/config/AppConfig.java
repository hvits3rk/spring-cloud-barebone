package com.romantupikov.rssfeed.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.messaging.Message;


@Configuration
@EnableIntegration
public class AppConfig {

    @Value("${feed.resource}")
    private Resource feedResource;

    // Храним метадата, чтобы не получать дубликаты фидов
    @Bean
    public MetadataStore metadataStore() {
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory("/tmp/si");
        return metadataStore;
    }

    // каждые 5 сек поллим фид с https://habr.com/rss/interesting/
    // и логим его пейлод
    @Bean
    public IntegrationFlow feedFlow() {
        return IntegrationFlows
                .from(Feed.inboundAdapter(this.feedResource, "habrFeed")
                                .metadataStore(metadataStore()),
                        e -> e.poller(p -> p.fixedDelay(5000)))
                .channel(c -> c.queue("entries"))
                .log(LoggingHandler.Level.ERROR, "feed.payload", Message::getPayload)
                .get();
    }
}
