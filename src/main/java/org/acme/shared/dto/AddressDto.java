package org.acme.shared.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.infrastructure.adapters.persistence.Address;

/**
 * DTO for {@link Address}
 */
@Data
@NoArgsConstructor
public class AddressDto implements Serializable {

   String street;
   String city;
   String country;

}