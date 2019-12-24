package de.clerkvest.api.entity.investment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * api <p>
 * de.clerkvest.api.entity.investment <p>
 * InvestRepository.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:14
 */
public interface InvestRepository extends JpaRepository<Invest, Long> {

}
