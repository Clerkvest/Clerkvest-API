package de.clerkvest.api.project.comment;

import de.clerkvest.api.Application;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureEmbeddedDatabase
public class DeleteCommentResourceTest {
}
