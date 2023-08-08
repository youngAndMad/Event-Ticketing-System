package danekerscode.eventservice.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${spring.data.elasticsearch.schema}") // TODO: 8/8/2023 add to yml file in  config service
    private String schema;

    @Value("${spring.data.elasticsearch.port}")
    private Integer port;

    @Value("${spring.data.elasticsearch.host}")
    private String host;

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, schema)));
    }

}
