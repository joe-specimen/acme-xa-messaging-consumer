package org.acme.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.jms.ConnectionFactory;
import jakarta.transaction.TransactionManager;
import org.acme.infrastructure.adapters.messaging.common.ArtemisJmsConsumer;
import org.acme.infrastructure.adapters.messaging.common.ArtemisJmsProducer;

@ApplicationScoped
public class ArtemisJmsFactory {

   @Produces
   @ApplicationScoped
   ArtemisJmsConsumer artemisJmsConsumerManager(
       @SuppressWarnings("CdiInjectionPointsInspection") ConnectionFactory connectionFactory,
       @SuppressWarnings("CdiInjectionPointsInspection") TransactionManager tm
   ) {
      // We need to inject connection factory and transaction manager
      // from here because ArtemisJmsConsumer is not managed by the Arc
      return new ArtemisJmsConsumer(connectionFactory, tm);
   }

   @Produces
   @ApplicationScoped
   ArtemisJmsProducer artemisJmsProducerManager(
       @SuppressWarnings("CdiInjectionPointsInspection") ConnectionFactory connectionFactory,
       @SuppressWarnings("CdiInjectionPointsInspection") TransactionManager tm
   ) {
      // We need to inject connection factory and transaction manager
      // from here because ArtemisJmsProducer is not managed by the Arc
      return new ArtemisJmsProducer(connectionFactory, tm);
   }

}
