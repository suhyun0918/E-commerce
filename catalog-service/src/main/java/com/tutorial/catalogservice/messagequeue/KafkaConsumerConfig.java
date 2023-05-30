package com.tutorial.catalogservice.messagequeue;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Bean // 접속할 수 있는 카프카의 정보
    public ConsumerFactory<String, String> consumerFactory(){ // Key: String, Value: String
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 접속할 서버
        // 그룹아이디 : 카프카에서 토픽에 쌓여있는 메시지를 가져가는 컨슈머들을 그룹화 할 수 있다.
        // 나중에 여러개의 컨슈머가 데이터를 가져갈 때 특정한 컨슈머그룹을 만들어놓고 특정한 그룹이 가져가게 할 수 있다.
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // 키 직렬화 : StringType
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // 밸류 직렬화 : ''

        return new DefaultKafkaConsumerFactory<>(properties); // 설정 정보 반환
    }

    @Bean // 접속정보를 이용해 실제 Listener 하나 등록
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();

        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory()); // 설정 접속 정보 등록

        return kafkaListenerContainerFactory;
    }
}
