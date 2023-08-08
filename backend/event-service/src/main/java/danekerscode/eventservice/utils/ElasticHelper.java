package danekerscode.eventservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component()
@Scope(SCOPE_PROTOTYPE)
public class ElasticHelper<T>{
    private final ObjectMapper json;
    private String INDEX_NAME;

    @Autowired
    public ElasticHelper(ObjectMapper json) {
        this.json = json;
    }

    public IndexRequest indexRequest(T t , String id) throws JsonProcessingException {
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
        indexRequest.id(id);
        indexRequest.source(json.writeValueAsString(t), XContentType.JSON);
        return indexRequest;
    }

    public DeleteRequest deleteRequest(String id){
        return new DeleteRequest(INDEX_NAME , id);
    }

    public UpdateRequest updateRequest(T t , String id){
        // https://iridakos.com/programming/2019/05/02/add-update-delete-elasticsearch-nested-objects todo
        return null;
    }


}