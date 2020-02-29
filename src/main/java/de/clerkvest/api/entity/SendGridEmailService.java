package de.clerkvest.api.entity;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import de.clerkvest.api.entity.employee.Employee;
import de.clerkvest.api.entity.project.Project;
import de.clerkvest.api.implement.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SendGridEmailService implements EmailService {
    private final Logger log = LoggerFactory.getLogger(SendGridEmailService.class);
    private final SendGrid sendGridClient;

    @Value("${spring.sendgrid.template.login.id}")
    private String loginTemplateId;

    @Value("${spring.sendgrid.template.funded.id}")
    private String fundedTemplateId;

    @Value("${spring.sendgrid.template.invite.id}")
    private String inviteTemplateId;

    @Autowired
    public SendGridEmailService(SendGrid sendGridClient) {
        this.sendGridClient = sendGridClient;
    }

    @Override
    public void sendText(String from, String to, String subject, String body) {
        Response response = sendEmail(from, to, subject, new Content("text/plain", body));
        if (response != null) {
            System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                    + response.getHeaders());
        }
    }

    @Override
    public void sendHTML(String from, String to, String subject, String body) {
        Response response = sendEmail(from, to, subject, new Content("text/html", body));
        if (response != null) {
            System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                    + response.getHeaders());
        }
    }

    private Response sendEmail(String from, String to, String subject, Content content) {
        Mail mail = new Mail(new Email(from, "Clerkvest"), subject, new Email(to), content);
        mail.setReplyTo(new Email("admin@clerkvest.com", "Clerkvest"));
        return sendRequest(mail);
    }

    private Response sendRequest(Mail mail) {
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGridClient.api(request);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return response;
    }

    public void sendMailToEmployees(List<Employee> employees, Project project) {
        employees.forEach(employee -> sendFundedMail(employee, project));
    }

    public Response sendLoginMail(Employee employee) {
        Mail mail = new Mail();

        Email fromEmail = new Email();
        fromEmail.setName("Clerkvest");
        fromEmail.setEmail("admin@clerkvest.com");
        mail.setFrom(fromEmail);

        mail.setTemplateId(loginTemplateId);

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("name", employee.getFirstname() + " " + employee.getLastname());
        personalization.addDynamicTemplateData("token", employee.getLoginToken());
        personalization.addTo(new Email(employee.getEmail(), employee.getNickname()));
        mail.addPersonalization(personalization);
        return sendRequest(mail);
    }

    public Response sendFundedMail(Employee employee, Project project) {
        Mail mail = new Mail();
        Email fromEmail = new Email();
        fromEmail.setName("Clerkvest");
        fromEmail.setEmail("admin@clerkvest.com");
        mail.setFrom(fromEmail);

        mail.setTemplateId(fundedTemplateId);

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("projectId", String.valueOf(project.getId()));
        personalization.addTo(new Email(employee.getEmail(), employee.getNickname()));
        mail.addPersonalization(personalization);
        return sendRequest(mail);
    }

    public Response sendInviteMail(Employee employee) {
        Mail mail = new Mail();

        Email fromEmail = new Email();
        fromEmail.setName("Clerkvest");
        fromEmail.setEmail("admin@clerkvest.com");
        mail.setFrom(fromEmail);

        mail.setTemplateId(inviteTemplateId);

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("email", employee.getEmail());
        personalization.addDynamicTemplateData("token", employee.getLoginToken());
        personalization.addTo(new Email(employee.getEmail(), employee.getNickname()));
        mail.addPersonalization(personalization);
        return sendRequest(mail);
    }
}