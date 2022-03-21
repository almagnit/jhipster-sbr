package sbr.converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbr.converter.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
