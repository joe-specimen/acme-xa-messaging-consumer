package org.acme.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.XAConnectionFactory;
import jakarta.transaction.TransactionManager;
import org.acme.infrastructure.adapters.messaging.common.ArtemisJmsConsumer;
import org.acme.infrastructure.adapters.messaging.common.ArtemisJmsProducer;

@ApplicationScoped
public class ArtemisJmsFactory {

   @Produces
   @ApplicationScoped
   ArtemisJmsConsumer artemisJmsConsumerManager(
       @SuppressWarnings("CdiInjectionPointsInspection") ConnectionFactory connectionFactory,
       @SuppressWarnings("CdiInjectionPointsInspection") XAConnectionFactory xaConnectionFactory,
       @SuppressWarnings("CdiInjectionPointsInspection") TransactionManager tm
   ) {
      return new ArtemisJmsConsumer(connectionFactory, xaConnectionFactory, tm);
   }

   @Produces
   @ApplicationScoped
   ArtemisJmsProducer artemisJmsProducerManager(
       @SuppressWarnings("CdiInjectionPointsInspection") ConnectionFactory connectionFactory,
       @SuppressWarnings("CdiInjectionPointsInspection") XAConnectionFactory xaConnectionFactory,
       @SuppressWarnings("CdiInjectionPointsInspection") TransactionManager tm
   ) {
      return new ArtemisJmsProducer(connectionFactory, xaConnectionFactory, tm);
   }

}
