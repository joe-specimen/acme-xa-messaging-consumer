package org.acme.infrastructure.adapters.messaging.common;

import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.transaction.Synchronization;

public class SynchronizationObject implements Synchronization {

   private final JMSContext context;
   private final JMSConsumer possibleConsumer;

   public static SynchronizationObject from(JMSContext context) {
      return new SynchronizationObject(context, null);
   }

   public static SynchronizationObject from(JMSContext context, JMSConsumer consumer) {
      return new SynchronizationObject(context, consumer);
   }

   private SynchronizationObject(JMSContext context, JMSConsumer consumer) {
      this.context = context;
      this.possibleConsumer = consumer;
   }

   @Override
   public void beforeCompletion() {}

   @Override
   public void afterCompletion(int status) {
      if (possibleConsumer != null) {
         possibleConsumer.close();
      }

      context.close();
   }
}
