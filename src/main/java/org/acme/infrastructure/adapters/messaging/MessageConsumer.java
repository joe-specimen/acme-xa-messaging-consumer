package org.acme.infrastructure.adapters.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.extern.jbosslog.JBossLog;
import org.acme.application.services.CreatePersonService;
import org.acme.infrastructure.adapters.messaging.common.ArtemisJmsConsumer;
import org.acme.shared.dto.CreatePersonDto;

@ApplicationScoped
@JBossLog
public class MessageConsumer {

   @Inject
   ArtemisJmsConsumer artemisJmsConsumer;

   @Inject
   ObjectMapper objectMapper;

   @Inject
   CreatePersonService createPersonService;

   @Transactional
   public void consumeXA() throws SystemException, RollbackException, JMSException, JsonProcessingException {
      log.infof("Waiting for message!");

      Optional<Message> possibleMessage = artemisJmsConsumer.receiveXA("queueDemo", 1000L);

      if (possibleMessage.isEmpty()) {
         return;
      }

      Message message = possibleMessage.get();

      String type = message.getStringProperty("type_");

      log.infof("Message Received: %s", type);

      if ("fail".equalsIgnoreCase(type)) {
         throw new RuntimeException("Go To DLQ!!!");
      }

      String body = message.getBody(String.class);

      CreatePersonDto createPersonDto = objectMapper.readValue(body, CreatePersonDto.class);
      createPersonService.createPerson(createPersonDto);
   }

}
