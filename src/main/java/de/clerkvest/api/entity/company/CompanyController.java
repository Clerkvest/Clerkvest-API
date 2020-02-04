package de.clerkvest.api.entity.company;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
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
import java.util.Optional;

/**
 * api <p>
 * de.clerkvest.api.entity.employee.company <p>
 * CompanyController.java <p>
 *
 * @author Danny B.
 * @version 1.0
 * @since 21 Dec 2019 17:18
 */
@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController implements DTOConverter<Company,CompanyDTO> {

    private final CompanyService service;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyController(CompanyService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ROLE_USER') and #auth.companyId.equals(#id)")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<CompanyDTO> getSingleCompany(@PathVariable long id, @AuthenticationPrincipal EmployeeUserDetails auth) {
        Optional<Company> company = service.getById(id);
        if (company.isPresent()) {
            return ResponseEntity.ok(convertToDto(company.get()));
        } else {
            throw new ClerkEntityNotFoundException("Company not found");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') and #auth.companyId.equals(#updated.id)")
    @PutMapping(value = "/update")
    public ResponseEntity<CompanyDTO> updateCompany(@Valid @RequestBody CompanyDTO updated, @AuthenticationPrincipal EmployeeUserDetails auth) throws ParseException {
        Company converted = convertToEntity(updated);
        service.update(converted);
        return ResponseEntity.ok().body(convertToDto(converted));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO fresh, @RequestParam String mail) throws ParseException {
        fresh.setId(-1L);
        Company converted = convertToEntity(fresh);
        service.save(converted);
        return ResponseEntity.ok().body(convertToDto(converted));
    }

    @Override
    public CompanyDTO convertToDto(Company post) {
        CompanyDTO postDto = modelMapper.map(post, CompanyDTO.class);
        LinkBuilder<CompanyDTO> linkBuilder = new LinkBuilder<CompanyDTO>()
                .withSelf(HateoasLink.COMPANY_SINGLE)
                .withCreate(HateoasLink.COMPANY_CREATE)
                .withUpdate(HateoasLink.COMPANY_UPDATE);
        linkBuilder.ifDesiredEmbed(postDto);
        return postDto;
    }

    @Override
    public Company convertToEntity(CompanyDTO postDto) throws ParseException {
        Company post = modelMapper.map(postDto, Company.class);
        if (postDto.getId() != null) {
            Optional<Company> oldPost = service.getById(postDto.getId());
            if (oldPost.isPresent()) {
                Company val = oldPost.get();
                val.setPayInterval(postDto.getPayInterval());
                val.setPayAmount(postDto.getPayAmount());
                val.setInviteOnly(postDto.getInviteOnly());
                val.setName(postDto.getName());
                return val;
            }
        }
        return post;
    }
}
