package de.clerkvest.api.implement;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import de.clerkvest.api.common.hateoas.link.LinkBuilder;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.employee.EmployeeDTO;

import java.text.ParseException;
import java.util.Optional;

public interface DTOConverter<T,V> {
    public V convertToDto(T post);

    public T convertToEntity(V postDto) throws ParseException;
}
