package org.acme.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link org.acme.infrastructure.adapters.persistence.Person}
 */
@Data
@NoArgsConstructor
public class CreatePersonDto implements Serializable {

   String name;
   Integer age;
   List<AddressDto> addresses = new ArrayList<>();

}