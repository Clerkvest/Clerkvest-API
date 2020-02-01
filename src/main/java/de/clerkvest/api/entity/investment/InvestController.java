package de.clerkvest.api.entity.investment;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.exception.ClerkEntityNotFoundException;
import de.clerkvest.api.implement.DTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class InvestController implements DTOConverter<Invest,InvestDTO> {
    private final InvestService service;
    private final ModelMapper modelMapper;

    @Autowired
    public InvestController(InvestService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<InvestDTO> createInvestment(@Valid @RequestBody InvestDTO fresh) throws ParseException {
        Invest converted = convertToEntity(fresh);
        service.save(converted);
        return ResponseEntity.ok().body(convertToDto(converted));
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<InvestDTO> getSingleInvestment(@PathVariable long id) {
        Optional<Invest> invest = service.getById(id);
        if(invest.isPresent()){
            return ResponseEntity.ok(convertToDto(invest.get()));
        }else{
            throw new ClerkEntityNotFoundException("Investment not found");
        }
    }

    @GetMapping(value = "/get/employee/{id}")
    public ResponseEntity<List<InvestDTO>> getInvestmentsByEmployee(@PathVariable long id) {
        Optional<List<Invest>> investments = service.getByEmployeeId(id);
        List<InvestDTO> investmentDTOs = Arrays.asList(modelMapper.map(investments, InvestDTO[].class));
        LinkBuilder<InvestDTO> linkBuilder = new LinkBuilder<InvestDTO>()
                .withSelf(HateoasLink.INVEST_SINGLE);
        investmentDTOs.forEach(linkBuilder::ifDesiredEmbed);
        return ResponseEntity.ok(investmentDTOs);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<InvestDTO>> getInvestmentsByCompany() {
        //TODO: return all Investments based on the company
        List<Invest> investments = service.getAll();
        List<InvestDTO> investmentDTOs = Arrays.asList(modelMapper.map(investments, InvestDTO[].class));
        LinkBuilder<InvestDTO> linkBuilder = new LinkBuilder<InvestDTO>()
                .withSelf(HateoasLink.INVEST_SINGLE);
        investmentDTOs.forEach(linkBuilder::ifDesiredEmbed);
        return ResponseEntity.ok(investmentDTOs);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteInvestment(@PathVariable long id) {
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
                .withAll(HateoasLink.INVEST_ALL)
                .withCreate(HateoasLink.INVEST_CREATE)
                .withDelete(HateoasLink.INVEST_DELETE)
                .withUpdate(HateoasLink.INVEST_UPDATE);
        linkBuilder.ifDesiredEmbed(postDto);
        return postDto;
    }

    @Override
    public Invest convertToEntity(InvestDTO postDto) throws ParseException {
        Invest post = modelMapper.map(postDto, Invest.class);
        if (postDto.getId() != null) {
            Optional<Invest> oldPost = service.getById(postDto.getId());
            //oldPost.ifPresent(value -> {post.setNickname(value.getNickname());});
        }
        return post;
    }
}
