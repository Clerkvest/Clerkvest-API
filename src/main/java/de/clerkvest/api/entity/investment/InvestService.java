package de.clerkvest.api.entity.investment;

import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.exception.ViolatedConstraintException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.investment <p>
 * InvestService.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:12
 */
@Service
public class InvestService implements IService<Invest> {
    private static final int BALANCE_SMALLER_INVESTMENT = -1;
    private final InvestRepository repository;

    @Autowired
    public InvestService(InvestRepository repository) {
        this.repository = repository;
    }

    @Override
    public Invest save(Invest invest) {
        if (invest.getProject().isReached()) {
            throw new ViolatedConstraintException("Project is already Closed");
        } else if (invest.getEmployee().getBalance().compareTo(invest.getInvestment()) == BALANCE_SMALLER_INVESTMENT) {
            throw new ViolatedConstraintException("Not enough Balance");
        }
        return repository.save(invest);
    }

    @Override
    public Invest update(Invest invest) {
        //Check if company is new
        Optional<Invest> existingInvest = repository.findById(invest.getId());
        if (existingInvest.isPresent()) {
            Invest value = existingInvest.get();
            value.setInvestment(invest.getInvestment());
            return repository.save(value);
        } else {
            throw new ClerkEntityNotFoundException("Invest can't be updated, not saved yet.");
        }
    }

    @Override
    public List<Invest> getAll() {
        return repository.findAll();
    }


    @Override
    public Optional<Invest> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Invest invest) {
        repository.delete(invest);
    }

    public Optional<List<Invest>> getByEmployeeId(long id) {
        return repository.getByEmployeeId(id);
    }

    public List<Invest> getByProjectId(Long id) {
        return repository.getByProjectId(id);
    }
}
