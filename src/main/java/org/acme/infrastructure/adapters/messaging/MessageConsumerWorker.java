package org.acme.infrastructure.adapters.messaging;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.jbosslog.JBossLog;

@ApplicationScoped
@JBossLog
public class MessageConsumerWorker implements Runnable {

   private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

   @Inject
   MessageConsumer messageConsumer;

   void onStart(@Observes StartupEvent ev) {
      scheduler.submit(this);
   }

   void onStop(@Observes ShutdownEvent ev) {
      scheduler.shutdown();
   }

   @Override
   public void run() {
      while (true) {
         try {
            messageConsumer.consumeXA();
         } catch (Exception e) {
            log.error(e);
         }
      }
   }
}
