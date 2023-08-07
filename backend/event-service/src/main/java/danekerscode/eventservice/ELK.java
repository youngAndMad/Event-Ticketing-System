package danekerscode.eventservice;

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
//        var articles = generateArticles(100);
//        articles.stream().map(this::createIndexRequest)
//                .forEach(articleIndex -> {
//                            try {
//                                var res = elastic.index(articleIndex, RequestOptions.DEFAULT);
//                                System.out.println(res);
//                            } catch (IOException e) {
//                                System.err.println(e.getMessage());
//                            }
//                        }
//                );
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

    static List<Article> generateArticles(int count) {
        List<Article> articles = new ArrayList<>();
        Random random = new Random();

        String[] possibleTitles = {
                "The Art of Programming",
                "Exploring Java Generics",
                "Introduction to Machine Learning",
                "Cooking Delicious Meals",
                "Traveling on a Budget",
                "Effective Time Management",
                "Understanding Quantum Computing",
                "Fitness and Health",
                "Nature Photography",
                "Financial Planning",
        };

        String[] possibleDescriptions = {
                "Learn the best practices of programming.",
                "Master the concepts of Java generics and type erasure.",
                "Dive into the world of machine learning with hands-on examples.",
                "Discover mouth-watering recipes to impress your guests.",
                "Explore exciting travel destinations without breaking the bank.",
                "Optimize your productivity with time management techniques.",
                "Unravel the mysteries of quantum computing and its potential applications.",
                "Stay fit and healthy with expert tips and workout routines.",
                "Capture the beauty of nature through photography and visual storytelling.",
                "Plan your financial future wisely with expert financial advice.",
        };

        for (int i = 0; i < count; i++) {
            String title = possibleTitles[random.nextInt(possibleTitles.length)];
            String description = possibleDescriptions[random.nextInt(possibleDescriptions.length)];

            Article article = new Article(title, description);
            articles.add(article);
        }

        return articles;
    }
}
