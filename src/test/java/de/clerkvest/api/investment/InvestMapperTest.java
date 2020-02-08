package de.clerkvest.api.investment;

import de.clerkvest.api.Application;
import de.clerkvest.api.entity.investment.Invest;
import de.clerkvest.api.entity.investment.InvestDTO;
import de.clerkvest.api.entity.investment.InvestRepository;
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
public class InvestMapperTest {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InvestRepository investRepository;

    @Test
    public void whenConvertCompanyEntityToCompanyDto_thenCorrect() {
        Optional<Invest> optionalInvestalEmployee = investRepository.findById(0L);
        assertThat(optionalInvestalEmployee.isEmpty()).isFalse();

        Invest invest = optionalInvestalEmployee.get();
        InvestDTO investDTO = modelMapper.map(invest, InvestDTO.class);

        assertThat(investDTO.getId()).isEqualTo(invest.getId());
        assertThat(investDTO.getInvestment()).isEqualTo(invest.getInvestment());
        assertThat(investDTO.getEmployeeId()).isEqualTo(invest.getEmployee().getId());
        assertThat(investDTO.getProjectId()).isEqualTo(invest.getProject().getId());
    }

    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {
        InvestDTO investDTO = new InvestDTO();
        investDTO.setId(0L);
        investDTO.setEmployeeId(0L);
        investDTO.setProjectId(0L);
        investDTO.setInvestment(BigDecimal.valueOf(5));

        Invest invest = modelMapper.map(investDTO, Invest.class);
        assertThat(investDTO.getId()).isEqualTo(invest.getId());
        assertThat(investDTO.getInvestment()).isEqualTo(invest.getInvestment());
    }
}
