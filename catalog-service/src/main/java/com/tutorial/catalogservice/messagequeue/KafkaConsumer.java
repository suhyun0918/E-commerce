package com.tutorial.catalogservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.catalogservice.jpa.CatalogEntity;
import com.tutorial.catalogservice.jpa.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
    CatalogRepository repository;

    @Autowired
    public KafkaConsumer(CatalogRepository repository) {
        this.repository = repository;
    }

    // "example-catalog-topic" 이 토픽에 어떤 데이터가 전달이 되면, 그 데이터 값을 가져와서 이 메소드가 실행됨
    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka Message: -> " + kafkaMessage);

        // 메세지를 아까 직렬화해서 전달을 했음. 때문에, 다시 역직렬화 해서 사용을 해야함.
        // 직렬화? 자바에서 데이터를 네트워크로 보낼때 자바가 인코딩하는 방법
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        // 위 과정이 다 끝나면 Map에서 상품의 id를 가져올것
        CatalogEntity entity = repository.findByProductId((String) map.get("productId"));// Object 형태로 받아올테니, String 형태로 바꿔야함
        if (entity != null) {
            entity.setStock(entity.getStock() - (Integer)map.get("qty"));
            repository.save(entity);
        }

    }
}
