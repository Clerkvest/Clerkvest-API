package de.clerkvest.api.project.comment;

import de.clerkvest.api.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional

public class DeleteCommentResourceTest {
}
