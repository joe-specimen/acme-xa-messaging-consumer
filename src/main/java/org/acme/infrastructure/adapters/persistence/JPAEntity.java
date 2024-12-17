package org.acme.infrastructure.adapters.persistence;

import java.util.Objects;
import org.hibernate.proxy.HibernateProxy;

public abstract class JPAEntity<T> {

   public abstract T getId();

   @Override
   public final boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null) {
         return false;
      }
      Class<?> oEffectiveClass =
          o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                                      : o.getClass();
      Class<?> thisEffectiveClass =
          this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                                         : this.getClass();
      if (thisEffectiveClass != oEffectiveClass) {
         return false;
      }
      Person person = (Person) o;
      return getId() != null && Objects.equals(getId(), person.getId());
   }

   @Override
   public final int hashCode() {
      return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
   }

}
