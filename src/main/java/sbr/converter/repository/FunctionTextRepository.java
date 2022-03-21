package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.FunctionText;

/**
 * Spring Data SQL repository for the FunctionText entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctionTextRepository extends JpaRepository<FunctionText, Long> {}
