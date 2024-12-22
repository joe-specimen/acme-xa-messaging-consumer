package org.acme.infrastructure.adapters.messaging.common;

import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.XAConnectionFactory;
import jakarta.jms.XAJMSContext;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;

public class ArtemisJmsProducer {

   private final ConnectionFactory connectionFactory;

   @Inject
   @SuppressWarnings("CdiInjectionPointsInspection")
   TransactionManager tm;


   public ArtemisJmsProducer(ConnectionFactory connectionFactory) {
      this.connectionFactory = connectionFactory;
   }

   public void send(String queueName, String body) {
      try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
         send(queueName, context, body);
      }
   }

   public void sendXA(String queueName, String body) throws SystemException, RollbackException {
      sendXA(queueName, body, false);
   }

   public void sendXA(String queueName, String body, boolean rollback) throws SystemException, RollbackException {
      JMSContext context = connectionFactory.createContext();

      tm.getTransaction().registerSynchronization(SynchronizationObject.from(context));

      send(queueName, context, body);

      if (rollback) {
         tm.setRollbackOnly();
      }
   }

   protected void send(String queueName, JMSContext context, String body) {
      JMSProducer producer = context.createProducer();
      producer.send(context.createQueue(queueName), body);
   }

}
