package de.clerkvest.api.entity.investment;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.entity.employee.EmployeeService;
import de.clerkvest.api.entity.project.ProjectService;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.DTOConverter;
import de.clerkvest.api.security.EmployeeUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.investment <p>
 * InvestController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 19:12
 */
@RestController
@RequestMapping("/invest")
@CrossOrigin(origins = "http://localhost:4200")
public class InvestController implements DTOConverter<Invest, InvestDTO> {
    private final InvestService service;
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    @Autowired
    public InvestController(InvestService service, EmployeeService employeeService, ProjectService projectService, ModelMapper modelMapper) {
        this.service = service;
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_USER') and (#fresh.employeeId.equals(#auth.employeeId)) and (@projectService.getById(#fresh.projectId).isPresent() ? @projectService.getById(#fresh.projectId).get().company.id.equals(#auth.companyId): true)")
    @PostMapping(value = "/create")
    public ResponseEntity<InvestDTO> createInvestment(@Valid @RequestBody InvestDTO fresh, @AuthenticationPrincipal EmployeeUserDetails auth) throws ParseException {
        fresh.setId(-1L);
        Invest converted = convertToEntity(fresh);
        service.save(converted);
        return ResponseEntity.ok().body(convertToDto(converted));
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@investService.getById(#id).isPresent() ? @investService.getById(#id).get().employee.company.id.equals(#auth.companyId) : true)")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<InvestDTO> getSingleInvestment(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Invest> invest = service.getById(id);
        if (invest.isPresent()) {
            return ResponseEntity.ok(convertToDto(invest.get()));
        } else {
            throw new ClerkEntityNotFoundException("Investment not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@employeeService.getById(#id).isPresent() ? @employeeService.getById(#id).get().company.id.equals(#auth.companyId) : true)")
    @GetMapping(value = "/get/employee/{id}")
    public ResponseEntity<List<InvestDTO>> getInvestmentsByEmployee(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<List<Invest>> investments = service.getByEmployeeId(id);
        List<InvestDTO> investmentDTOs = Arrays.asList(modelMapper.map(investments, InvestDTO[].class));
        LinkBuilder<InvestDTO> linkBuilder = new LinkBuilder<InvestDTO>()
                .withSelf(HateoasLink.INVEST_SINGLE);
        investmentDTOs.forEach(linkBuilder::ifDesiredEmbed);
        return ResponseEntity.ok(investmentDTOs);
    }

    @PreAuthorize("hasRole('ROLE_USER') and (@projectService.getById(#id).isPresent() ? @projectService.getById(#id).get().company.id.equals(#auth.companyId) : true)")
    @GetMapping(value = "/all/{id}")
    public ResponseEntity<List<InvestDTO>> getInvestmentsByProject(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        if (projectService.getById(id).isEmpty()) {
            throw new ClerkEntityNotFoundException("Project does not exist");
        }
        List<Invest> investments = service.getByProjectId(id);
        List<InvestDTO> investmentDTOs = Arrays.asList(modelMapper.map(investments, InvestDTO[].class));
        LinkBuilder<InvestDTO> linkBuilder = new LinkBuilder<InvestDTO>()
                .withSelf(HateoasLink.INVEST_SINGLE);
        investmentDTOs.forEach(linkBuilder::ifDesiredEmbed);
        return ResponseEntity.ok(investmentDTOs);
    }

    @PreAuthorize("(hasRole('ROLE_USER') and (@investService.getById(#id).isPresent() ? @investService.getById(#id).get().employee.id.equals(#auth.employeeId) : true))")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteInvestment(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Invest> employee = service.getById(id);
        employee.ifPresentOrElse(service::delete, () -> {
            throw new ClerkEntityNotFoundException("Investment not found");
        });
        return ResponseEntity.ok().build();
    }

    @Override
    public InvestDTO convertToDto(Invest post) {
        InvestDTO postDto = modelMapper.map(post, InvestDTO.class);
        LinkBuilder<InvestDTO> linkBuilder = new LinkBuilder<InvestDTO>()
                .withSelf(HateoasLink.INVEST_SINGLE)
                //.withAll(HateoasLink.INVEST_ALL)
                .withCreate(HateoasLink.INVEST_CREATE)
                .withDelete(HateoasLink.INVEST_DELETE)
                .withUpdate(HateoasLink.INVEST_UPDATE);
        linkBuilder.ifDesiredEmbed(postDto);
        return postDto;
    }

    @Override
    public Invest convertToEntity(InvestDTO postDto) throws ParseException {
        Invest post = modelMapper.map(postDto, Invest.class);
        if (postDto.getId() != null && post.getId() != -1L) {
            Optional<Invest> oldPost = service.getById(postDto.getId());
            if (oldPost.isPresent()) {
                Invest val = oldPost.get();
                val.setInvestment(postDto.getInvestment());
                return val;
            }
        } else {
            if (postDto.getEmployeeId() != null) {
                employeeService.getById(postDto.getEmployeeId()).ifPresent(post::setEmployee);
            }
            if (postDto.getProjectId() != null) {
                projectService.getById(postDto.getProjectId()).ifPresent(post::setProject);
            }
        }
        return post;
    }
}
