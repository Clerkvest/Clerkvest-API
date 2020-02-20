package de.clerkvest.api.common.mail;

import de.clerkvest.api.entity.company.Company;
import de.clerkvest.api.entity.company.CompanyService;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.exception.ViolatedConstraintException;
import org.apache.commons.lang3.StringUtils;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressCriteria;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressParser;
import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator;

import java.math.BigDecimal;
import java.util.Optional;

public class MailUtil {
    public static Employee createEmployeeFromMail(String mail, CompanyService companyService) {
        if (!EmailAddressValidator.isValid(mail, EmailAddressCriteria.DEFAULT)) {
            throw new ViolatedConstraintException("Email is not valid");
        }
        var name = EmailAddressParser.getPersonalName(mail, EmailAddressCriteria.DEFAULT, true);
        var firstname = "";
        var lastname = "";
        if (name != null) {
            var firstAndLastName = StringUtils.split(name, ' ');
            if (firstAndLastName.length > 1) {
                firstname = firstAndLastName[0];
                lastname = firstAndLastName[1];
            } else {
                lastname = name;
            }
        } else {
            lastname = mail;
        }
        Optional<Company> company = companyService.getByDomain(getDomain(mail));
        Employee employee = Employee.builder().employeeId(-1L).balance(BigDecimal.ONE).company(null).email(mail).firstname(firstname).lastname(lastname).isAdmin(false).nickname(mail).build();
        company.ifPresent(employee::setCompany);
        return employee;
    }

    public static String getDomain(String from) {
        return EmailAddressParser.getDomain(from, EmailAddressCriteria.DEFAULT, false);
    }
}
