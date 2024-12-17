package org.acme.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.arc.All;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class ObjectMapperFactory {

   @Singleton
   @Produces
   public ObjectMapper objectMapper(@All Instance<ObjectMapperCustomizer> customizers) {
      ObjectMapper objectMapper = createObjectMapper();

      for (ObjectMapperCustomizer customizer : customizers) {
         customizer.customize(objectMapper);
      }

      return objectMapper;
   }

   private ObjectMapper createObjectMapper() {

      final ObjectMapper mapper = new ObjectMapper();

      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      mapper.registerModule(new JavaTimeModule());

      return mapper;
   }

}
