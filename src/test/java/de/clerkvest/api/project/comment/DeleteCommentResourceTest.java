package de.clerkvest.api.project.comment;

import de.clerkvest.api.Application;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
@AutoConfigureEmbeddedDatabase
public class DeleteCommentResourceTest {
}
