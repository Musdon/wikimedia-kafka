package com.musdon.springboot;

import com.musdon.springboot.entity.WikimediaData;
import com.musdon.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    @Autowired
    private WikimediaDataRepository dataRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    @KafkaListener(topics = "wikimedia_recentchange", groupId = "myGroup")
    public void consume(String eventMessage){
        LOGGER.info(String.format("Event Message received -> %s", eventMessage));
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        dataRepository.save(wikimediaData);
    }
}
