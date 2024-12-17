package org.acme.infrastructure.adapters.messaging.common;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Message;
import jakarta.jms.XAConnectionFactory;
import jakarta.jms.XAJMSContext;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import java.util.Optional;


public class ArtemisJmsConsumer {

   private final ConnectionFactory connectionFactory;
   private final XAConnectionFactory xaConnectionFactory;
   private final TransactionManager tm;

   public ArtemisJmsConsumer(ConnectionFactory connectionFactory, XAConnectionFactory xaConnectionFactory, TransactionManager tm) {
      this.connectionFactory = connectionFactory;
      this.xaConnectionFactory = xaConnectionFactory;
      this.tm = tm;
   }

   public Optional<Message> receive(String queueName, long timeout) {
      try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
           JMSConsumer consumer = context.createConsumer(context.createQueue(queueName))) {
         return Optional.ofNullable(consumer.receive(timeout));
      }
   }

   public Optional<Message> receiveXA(String queueName, long timeout) throws SystemException, RollbackException {
      return receiveXA(queueName, timeout, false);
   }

   public Optional<Message> receiveXA(String queueName, long timeout, boolean rollback) throws SystemException, RollbackException {
      XAJMSContext context = xaConnectionFactory.createXAContext();
      JMSConsumer consumer = context.createConsumer(context.createQueue(queueName));

      tm.getTransaction().enlistResource(context.getXAResource());
      tm.getTransaction().registerSynchronization(SynchronizationObject.from(context, consumer));

      if (rollback) {
         tm.setRollbackOnly();
      }

      return Optional.ofNullable(consumer.receive(timeout));
   }

}
