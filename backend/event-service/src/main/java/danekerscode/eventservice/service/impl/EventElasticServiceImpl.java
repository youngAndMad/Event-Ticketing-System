package danekerscode.eventservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import danekerscode.eventservice.dto.EventSearchRequest;
import danekerscode.eventservice.enums.EventType;
import danekerscode.eventservice.model.Address;
import danekerscode.eventservice.model.Event;
import danekerscode.eventservice.service.EventElasticService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventElasticServiceImpl implements EventElasticService {

    private final RestHighLevelClient elastic;
    private final ObjectMapper json;

    private final static String EVENT = "events";
    private final static RequestOptions DEFAULT = RequestOptions.DEFAULT;

    @SneakyThrows
    @Override
    public List<Event> search(EventSearchRequest eventSearchRequest, int from, int to) {
        var searchRequest = new SearchRequest();
        var searchSourceBuilder = new SearchSourceBuilder();

        var boolQueryBuilder = QueryBuilders.boolQuery();

        if (eventSearchRequest.address() != null) {
            boolQueryBuilder.must(QueryBuilders.nestedQuery("address",
                    QueryBuilders.boolQuery()
                            .must(QueryBuilders.matchQuery("address.country", eventSearchRequest.address().country()))
                            .must(QueryBuilders.matchQuery("address.city", eventSearchRequest.address().country())),
                    ScoreMode.None));
        }

        if (eventSearchRequest.text() != null) {
            boolQueryBuilder.must(QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("title", eventSearchRequest.text()))
                    .should(QueryBuilders.matchQuery("description", eventSearchRequest.text())));
        }

        if (eventSearchRequest.text() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("time")
                    .gte(eventSearchRequest.time())
                    .format("yyyy-MM-dd'T'HH:mm:ss"));
        }

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(to);

        searchRequest.source(searchSourceBuilder);

        return convertToJavaEventObjects(elastic.search(searchRequest, DEFAULT));
    }

    @SneakyThrows
    @Override
    public void addIndex(Event event) {
        var req = createIndexRequest(event);
        elastic.index(req, DEFAULT);
    }

    @SneakyThrows
    @Override
    public void deleteIndex(Long eventId) {
        var req = deleteRequest(eventId);
        elastic.delete(req, DEFAULT);
    }

    @SneakyThrows
    @Override
    public void updateIndex(Event updatedEvent) {
        var delete = deleteRequest(updatedEvent.getId());
        elastic.delete(delete, DEFAULT);

        var create = createIndexRequest(updatedEvent);
        elastic.index(create, DEFAULT);
    }

    @SneakyThrows
    private IndexRequest createIndexRequest(Event event) {
        IndexRequest indexRequest = new IndexRequest(EVENT);
        indexRequest.id(String.valueOf(event.getId()));
        indexRequest.source(json.writeValueAsString(event), XContentType.JSON);
        return indexRequest;
    }

    public DeleteRequest deleteRequest(Long id) {
        return new DeleteRequest(EVENT, String.valueOf(id));
    }

    private List<Event> convertToJavaEventObjects(SearchResponse searchResponse) {
        return Arrays.stream(searchResponse.getHits().getHits())
                .map(SearchHit::getSourceAsMap)
                .map(sourceAsMap -> {
                    Event event = new Event();

                    event.setId(Long.parseLong(sourceAsMap.get("id").toString()));
                    event.setTime(LocalDateTime.parse(sourceAsMap.get("time").toString(), DateTimeFormatter.ISO_DATE_TIME));
                    event.setType(EventType.valueOf(sourceAsMap.get("type").toString()));
                    event.setTitle(sourceAsMap.get("title").toString());
                    event.setDescription(sourceAsMap.get("description").toString());

                    Map<String, Object> addressMap = (Map<String, Object>) sourceAsMap.get("address");
                    Address address = new Address();
                    address.setCountry(addressMap.get("country").toString());
                    address.setCity(addressMap.get("city").toString());
                    address.setStreet(addressMap.get("street").toString());
                    address.setMark(addressMap.get("mark").toString());
                    address.setBuildingName(addressMap.get("buildingName").toString());

                    event.setAddress(address);

                    return event;
                })
                .toList();
    }

}
