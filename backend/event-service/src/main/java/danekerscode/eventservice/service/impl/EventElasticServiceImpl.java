package danekerscode.eventservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import danekerscode.eventservice.dto.EventSearchRequest;
import danekerscode.eventservice.model.Event;
import danekerscode.eventservice.service.EventElasticService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventElasticServiceImpl implements EventElasticService {

    private final RestHighLevelClient elastic;

    private final static String ARTICLE_INDEX = "article";

    @Override
    public List<Event> search(EventSearchRequest search, int from, int to) {
        return null;
    }

    @Override
    public void addIndex(Event event) {

    }

    @Override
    public void deleteIndex(Long eventId) {

    }

    @Override
    public void updateIndex(Event updatedEvent) {

    }


}
