package danekerscode.eventservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import danekerscode.eventservice.dto.EventSearchRequest;
import danekerscode.eventservice.enums.EventType;
import danekerscode.eventservice.mapper.EventMapper;
import danekerscode.eventservice.model.Address;
import danekerscode.eventservice.model.Event;
import danekerscode.eventservice.service.EventElasticService;
import danekerscode.eventservice.utils.EventIndex;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventElasticServiceImpl implements EventElasticService {

    private final RestHighLevelClient elastic;
    private final ObjectMapper json;
    private final EventMapper eventMapper;

    private final static String EVENT = "event";
    private final static RequestOptions DEFAULT = RequestOptions.DEFAULT;

    @SneakyThrows
    @Override
    public List<Event> search(EventSearchRequest eventSearchRequest, int from, int to) {
        var searchRequest = new SearchRequest();
        var searchSourceBuilder = new SearchSourceBuilder();

        var boolQueryBuilder = QueryBuilders.boolQuery();

        if (eventSearchRequest.country()!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("country" , eventSearchRequest.country()));
        }

        if (eventSearchRequest.city()!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("city" , eventSearchRequest.city()));
        }

        if (eventSearchRequest.text() != null) {
            boolQueryBuilder.must(QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("title", eventSearchRequest.text()))
                    .should(QueryBuilders.matchQuery("description", eventSearchRequest.text())));
        }

        if (eventSearchRequest.text() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("time")
                    .gte(eventSearchRequest.time())
                    .format("yyyy-MM-dd"));
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
        var req = createIndexRequest(eventMapper.toIndex(event));
        var res =  elastic.index(req, DEFAULT);
        log.info("add index result info id: {}, {}" , event.getId() , res.getResult().name());
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

        var create = createIndexRequest(eventMapper.toIndex(updatedEvent));
        elastic.index(create, DEFAULT);
    }

    @SneakyThrows
    private IndexRequest createIndexRequest(EventIndex event) {
        IndexRequest indexRequest = new IndexRequest(EVENT);
        indexRequest.id(String.valueOf(event.eventId()));
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
                    System.out.println(sourceAsMap);

                    event.setId(Long.parseLong(sourceAsMap.get("eventId").toString()));
                    event.setTime(parseDateString(sourceAsMap.get("time").toString()));
                    event.setType(EventType.valueOf(sourceAsMap.get("type").toString()));
                    event.setTitle(sourceAsMap.get("title").toString());
                    event.setDescription(sourceAsMap.get("description").toString());

                    Address address = new Address();
                    address.setId(Long.parseLong(sourceAsMap.get("addressId").toString()));
                    address.setCountry(sourceAsMap.get("country").toString());
                    address.setCity(sourceAsMap.get("city").toString());
                    address.setStreet(sourceAsMap.get("street").toString());
                    address.setMark(sourceAsMap.get("mark").toString());
                    address.setBuildingName(sourceAsMap.get("buildingName").toString());

                    event.setAddress(address);

                    return event;
                })
                .toList();
    }

    public static LocalDate parseDateString(String dateString) {
        String[] dateComponents = dateString.replaceAll("[\\[\\]]", "").split(", ");


        int year = Integer.parseInt(dateComponents[0]);
        int month = Integer.parseInt(dateComponents[1]);
        int day = Integer.parseInt(dateComponents[2]);

        return LocalDate.of(year, month, day);
    }


}
