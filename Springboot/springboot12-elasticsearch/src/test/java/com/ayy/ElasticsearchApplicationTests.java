package com.ayy;

import com.ayy.bean.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ElasticsearchApplicationTests {
    private final String INDEX = "test_index";
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(INDEX);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    public void testGetIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDEX);
        boolean exist = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exist);
    }

    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(INDEX);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
    }

    @Test
    public void testAddDoc() throws IOException {
        User user = new User("user", 12);
        IndexRequest request = new IndexRequest(INDEX);

        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(10));
        request.timeout("10s");

        IndexRequest source = request.source(mapper.writeValueAsString(user), XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        System.out.println(response.toString());
        System.out.println(response.status());
    }

    @Test
    public void testGetDoc() throws IOException {
        GetRequest request = new GetRequest(INDEX, "1");
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    public void testGetDocInfo() throws IOException {
        GetRequest request = new GetRequest(INDEX, "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        User user = mapper.readValue(response.getSourceAsString(), User.class);
        System.out.println(user);
    }

    @Test
    public void testUpdateDocInfo() throws IOException {
        UpdateRequest request = new UpdateRequest(INDEX, "1");
        User user = new User("user2", 18);
        request.doc(mapper.writeValueAsString(user), XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response.toString());
    }

    @Test
    public void testDeleteDocInfo() throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX, "1");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response.toString());
    }

    @Test
    public void testBulkRequest() throws IOException{
        BulkRequest bulkRequest = new BulkRequest();
        List<User> users = new ArrayList<>();
        users.add(new User("user1",10));
        users.add(new User("user2",20));
        users.add(new User("user3",30));
        users.add(new User("user4",40));
        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(
                    new IndexRequest(INDEX)
                            .id(""+(i+1))
                            .source(mapper.writeValueAsString(users.get(i)),XContentType.JSON));
        }
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "user2");

        // MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(termQueryBuilder);
        // searchSourceBuilder.from();
        // searchSourceBuilder.size();

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println(mapper.writeValueAsString(response.getHits()));

        response.getHits().forEach((h)->{
            System.out.println(h.getSourceAsString());
        });
    }
}
