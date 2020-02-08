package de.clerkvest.api.company;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyDTO;
import de.clerkvest.api.entity.company.CompanyRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@AutoConfigureEmbeddedDatabase
public class CompanyMapperTest {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void whenConvertCompanyEntityToCompanyDto_thenCorrect() {
        Optional<Company> optionalCompany = companyRepository.findById(0L);
        assertThat(optionalCompany.isEmpty()).isFalse();

        Company company = optionalCompany.get();
        CompanyDTO companyDTO = modelMapper.map(company, CompanyDTO.class);

        assertThat(companyDTO.getId()).isEqualTo(company.getId());
        assertThat(companyDTO.getDomain()).isEqualTo(company.getDomain());
        //assertThat(companyDTO.getImage()).isEqualTo(company.getImageId().getId());
        assertThat(companyDTO.getInviteOnly()).isEqualTo(company.isInviteOnly());
        assertThat(companyDTO.getName()).isEqualTo(company.getName());
        assertThat(companyDTO.getPayAmount()).isEqualTo(company.getPayAmount());
        assertThat(companyDTO.getPayInterval()).isEqualTo(company.getPayInterval());
    }

    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(0L);
        companyDTO.setDomain("clerkvest.de");
        companyDTO.setName("Clerk GmbH");
        companyDTO.setPayAmount(BigDecimal.valueOf(25));
        companyDTO.setPayInterval(1);
        companyDTO.setInviteOnly(true);

        Company company = modelMapper.map(companyDTO, Company.class);
        assertThat(companyDTO.getId()).isEqualTo(company.getId());
        assertThat(companyDTO.getDomain()).isEqualTo(company.getDomain());
        assertThat(companyDTO.getInviteOnly()).isEqualTo(company.isInviteOnly());
        assertThat(companyDTO.getName()).isEqualTo(company.getName());
        assertThat(companyDTO.getPayAmount()).isEqualTo(company.getPayAmount());
        assertThat(companyDTO.getPayInterval()).isEqualTo(company.getPayInterval());
    }
}
