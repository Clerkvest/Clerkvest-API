package de.clerkvest.api.entity.company;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.exception.DuplicateEntityException;
import de.clerkvest.api.exception.ViolatedConstraintException;
import de.clerkvest.api.implement.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.employee.company <p>
 * CompanyService.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:17
 */
@Service
public class CompanyService implements IService<Company> {

    private final CompanyRepository repository;

    @Autowired
    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Company save(Company company) {
        LinkBuilder<Company> linkBuilder = new LinkBuilder<Company>()
                .withSelf(HateoasLink.COMPANY_SINGLE)
                .withCreate(HateoasLink.COMPANY_CREATE)
                .withUpdate(HateoasLink.COMPANY_UPDATE);
        linkBuilder.ifDesiredEmbed(company);
        //Check if the Company is already saved
        if (repository.existsById(company.getId())) {
            return company;
        }
        repository.findCompaniesByDomain(company.getDomain()).ifPresent(c -> {
            throw new DuplicateEntityException("A Company for " + c.getDomain() + " already exists");
        });
        if (company.getPayAmount().intValue() < 1) {
            throw new ViolatedConstraintException("Pay Amount can't be below 1");
        }
        if (company.getPayInterval() < 1) {
            throw new ViolatedConstraintException("Pay Interval can't be below 1");
        }
        return repository.save(company);
    }

    @Override
    public Company saveAndFlush(Company company) {
        var freshCompany = save(company);
        repository.flush();
        return freshCompany;
    }

    @Override
    public Company update(Company company) {
        //Check if company is new
        Optional<Company> existingCompany = repository.findById(company.getId());
        if (existingCompany.isPresent()) {
            Company value = existingCompany.get();
            value.setName(company.getName());
            value.setImage(company.getImage());
            value.setInviteOnly(company.isInviteOnly());
            value.setPayAmount(company.getPayAmount());
            value.setPayInterval(company.getPayInterval());
            return repository.save(value);
        } else {
            throw new ClerkEntityNotFoundException("Company can't be updated, not saved yet.");
        }
    }

    @Override
    public List<Company> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Company> getById(long id) {
        Optional<Company> optionalCompany = repository.findById(id);

        LinkBuilder<Company> linkBuilder = new LinkBuilder<Company>()
                .withSelf(HateoasLink.COMPANY_SINGLE)
                .withCreate(HateoasLink.COMPANY_CREATE)
                .withUpdate(HateoasLink.COMPANY_UPDATE);

        optionalCompany.ifPresent(linkBuilder::ifDesiredEmbed);
        return optionalCompany;
    }

    public Optional<Company> getByDomain(String domain) {
        return repository.getCompanyByDomain(domain);
    }

    @Override
    public void delete(Company company) {
        repository.delete(company);
    }
}
