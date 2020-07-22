package de.clerkvest.api.entity.investment;

import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.exception.ViolatedConstraintException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class InvestService implements IService<Invest> {
    private static final int BALANCE_SMALLER_INVESTMENT = -1;
    private final InvestRepository repository;

    @Autowired
    public InvestService(InvestRepository repository) {
        this.repository = repository;
    }

    @Override
    public Invest save(Invest invest) {
        validateConstraints(invest);
        return repository.save(invest);
    }

    @Override
    public Invest saveAndFlush(Invest invest) {
        var freshEntity = save(invest);
        repository.flush();
        return freshEntity;
    }

    @Override
    public Invest update(Invest invest) {
        //Check if company is new
        Optional<Invest> existingInvest = repository.findById(invest.getId());
        if (existingInvest.isPresent()) {
            Invest value = existingInvest.get();
            validateConstraints(invest);
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

    public List<Invest> getAllByProjectAndEmployee(Project project, Employee employee) {
        return repository.getByProjectIdAAndEmployee(project.getId(), employee.getId());
    }

    @Override
    public Optional<Invest> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Invest invest) {
        if (invest.getProject().isReached()) {
            throw new ViolatedConstraintException("Project is already Closed");
        }
        repository.delete(invest);
    }

    public Optional<List<Invest>> getByEmployeeId(long id) {
        return repository.getByEmployeeId(id);
    }

    public List<Invest> getByProjectId(Long id) {
        return repository.getByProjectId(id);
    }


    private void validateConstraints(Invest invest) {
        if (invest.getProject().isReached()) {
            throw new ViolatedConstraintException("Project is already Closed");
        } else if (invest.getEmployee().getBalance().compareTo(invest.getInvestment()) == BALANCE_SMALLER_INVESTMENT) {
            throw new ViolatedConstraintException("Not enough Balance");
        } else if (invest.getProject().getGoal().compareTo(invest.getProject().getInvestedIn().add(invest.getInvestment())) == -1) {
            throw new ViolatedConstraintException("Not allowed to invest more than the Goal");
        }
    }
}
