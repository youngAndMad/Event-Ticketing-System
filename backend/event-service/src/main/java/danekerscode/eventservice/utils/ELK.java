package danekerscode.eventservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Component;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class ELK implements Runnable {
    private final RestHighLevelClient elastic;
    private final ObjectMapper objectMapper = new ObjectMapper();


    private final static String ARTICLE_INDEX = "article";

    @Override
    public void run() {
        trySearch();

    }

    @SneakyThrows
    private void trySearch() {
        SearchRequest searchRequest = new SearchRequest(ARTICLE_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("title","Nature"))
                .mustNot(QueryBuilders.matchQuery("description" , "programming")));
        searchRequest.source(searchSourceBuilder);

        /*
        searchSourceBuilder.size(3);
        searchSourceBuilder.from(0);
         pagination
         */
        SearchResponse searchResponse = elastic.search(searchRequest, RequestOptions.DEFAULT);

        Arrays.stream(searchResponse.getHits().getHits())
                .map(SearchHit::getSourceAsMap)
                .map(map -> {
                    return new Article(
                            (String) map.get("title"),
                            (String) map.get("description")
                    );
                })
                .forEach(System.out::println);
    }

    @SneakyThrows
    private IndexRequest createIndexRequest(Article article) {
        IndexRequest indexRequest = new IndexRequest(ARTICLE_INDEX);
        indexRequest.id(UUID.randomUUID().toString());
        indexRequest.source(objectMapper.writeValueAsString(article), XContentType.JSON);

        return indexRequest;
    }

    record Article(
            String title,
            String description
    ) {
    }

}
