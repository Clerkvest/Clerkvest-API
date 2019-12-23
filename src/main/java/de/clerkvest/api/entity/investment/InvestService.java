package de.clerkvest.api.entity.investment;

import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * InvestService.java <p>
 *
 * @author q1x
 * @version 1.0
 * @since 21 Dec 2019 19:12
 */
@Service
public class InvestService implements IService<Invest> {

    private final InvestRepository repository;

    @Autowired
    public InvestService (InvestRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save (Invest invest) {
        repository.save(invest);
    }

    @Override
    public List<Invest> getAll () {
        return repository.findAll();
    }

    @Override
    public Optional<Invest> getById (long id) {
        return repository.findById(id);
    }

    @Override
    public void delete (Invest invest) {
        repository.delete(invest);
    }
}
