package de.clerkvest.api.config;

import de.clerkvest.api.common.hateoas.constants.HateoasLink;
import io.restassured.config.RestAssuredConfig;

public class TestConfig {
    public static final TestConfig INSTANCE = new TestConfig();

    private TestConfig() {
        RestAssuredConfig.config().getLogConfig().enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static final String REST_BASE_URL = HateoasLink.BASE_ENDPOINT;
}
