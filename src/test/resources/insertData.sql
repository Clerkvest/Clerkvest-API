CREATE TABLE IF NOT EXISTS company
(
    company_id   BIGINT auto_increment PRIMARY KEY,
    name         varchar(255)                                     NOT NULL,
    domain       varchar(255)                                     NOT NULL,
    image_id  bigint                                                        ,
    pay_amount   DECIMAL                                                    NOT NULL,
    pay_interval integer                                                    NOT NULL,
    invite_only  boolean                                                    NOT NULL
);



CREATE TABLE IF NOT EXISTS email_token
(
    email_token_id BIGINT auto_increment PRIMARY KEY,
    employee_id    bigint                                                                                  NOT NULL,
    created_at     timestamp DEFAULT now()                                               NOT NULL,
    token          varchar(255)                                                                  NOT NULL
);


CREATE TABLE IF NOT EXISTS employee
(
    employee_id BIGINT auto_increment PRIMARY KEY,
    company_id  bigint                                                       NOT NULL,
    email       varchar(255)                                       NOT NULL,
    balance     DECIMAL                                                      NOT NULL,
    token       varchar(255),
    firstname   varchar(255)                                       NOT NULL,
    lastname    varchar(255)                                       NOT NULL,
    nickname    varchar(255)                                       NOT NULL,
    is_admin    boolean                                                      NOT NULL,
    CHECK ( balance >= 0 )
);



CREATE TABLE IF NOT EXISTS employee_comment
(
    employee_comment_id BIGINT auto_increment PRIMARY KEY,
    employee_id         bigint                                                                                            NOT NULL,
    commenter_id        bigint                                                                                            NOT NULL,
    comment             varchar(255)                                                                            NOT NULL,
    date                timestamp DEFAULT now()                                                         NOT NULL
);


CREATE TABLE IF NOT EXISTS project
(
    project_id  BIGINT auto_increment PRIMARY KEY,
    employee_id bigint                                                                          NOT NULL,
    company_id  bigint                                                                          NOT NULL,
    link        varchar(255)                                                          NOT NULL,
    name        varchar(255)                                                          NOT NULL,
    description varchar(255)                                                          NOT NULL,
    goal        DECIMAL                                                                         NOT NULL,
    invested_in DECIMAL                                                                         NOT NULL,
    reached     boolean                                                                         NOT NULL,
    image_id  bigint                                                                            ,
    created_at  timestamp DEFAULT now()                                       NOT NULL,
    funded_at   timestamp
);


CREATE TABLE IF NOT EXISTS invest_in
(
    invest_in_id BIGINT auto_increment PRIMARY KEY,
    project_id   bigint                                                         NOT NULL,
    employee_id  bigint                                                         NOT NULL,
    investment   DECIMAL                                                        NOT NULL
);


CREATE TABLE IF NOT EXISTS project_comment
(
    project_comment_id BIGINT auto_increment PRIMARY KEY,
    employee_id        bigint                                                                                          NOT NULL,
    project_id         bigint                                                                                          NOT NULL,
    title              varchar(255)                                                                          NOT NULL,
    text               varchar(255)                                                                          NOT NULL,
    date               timestamp DEFAULT now()                                                       NOT NULL
);

CREATE TABLE IF NOT EXISTS image
(
    image_id   BIGINT auto_increment PRIMARY KEY,
    fileName   varchar(255)                                     NOT NULL,
    path       varchar(255)                                     NOT NULL
);


ALTER TABLE company
    ADD FOREIGN KEY (image_id) REFERENCES image (image_id);

ALTER TABLE email_token
    ADD FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE;
ALTER TABLE employee
    ADD FOREIGN KEY (company_id) REFERENCES company (company_id) ON DELETE CASCADE;

ALTER TABLE employee_comment
    ADD FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE;
ALTER TABLE employee_comment
    ADD FOREIGN KEY (commenter_id) REFERENCES employee (employee_id) ON DELETE CASCADE;

ALTER TABLE project
    ADD FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE;
ALTER TABLE project
    ADD FOREIGN KEY (company_id) REFERENCES company (company_id);
ALTER TABLE project
    ADD FOREIGN KEY (image_id) REFERENCES image (image_id);

ALTER TABLE invest_in
    ADD FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE;
ALTER TABLE invest_in
    ADD FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE;

ALTER TABLE project_comment
    ADD FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE;
ALTER TABLE project_comment
    ADD FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE;

CREATE TRIGGER IF NOT EXISTS investment_insert_trigger
    BEFORE INSERT
    ON invest_in
    FOR EACH ROW
CALL "de.clerkvest.api.H2Triggers$InvestmentInsertTrigger";

CREATE TRIGGER  IF NOT EXISTS investment_delete_trigger
    BEFORE DELETE
    ON invest_in
    FOR EACH ROW
CALL "de.clerkvest.api.H2Triggers$InvestmentDeleteTrigger";

INSERT INTO company(company_id, name, domain, image_id, pay_amount, pay_interval, invite_only)
VALUES (0, 'Clerk GmbH', 'clerkvest.de', null, 25, 1, true);
INSERT INTO company(company_id, name, domain, image_id, pay_amount, pay_interval, invite_only)
VALUES (1, 'Company GmbH', 'company.de', null, 15, 30, false);

INSERT INTO employee(employee_id, company_id, email, balance, token, firstname, lastname, nickname, is_admin)
VALUES (0, 0, 'user1@clerkvest.de', 10, 'exampleToken0', 'Mike', 'User', 'User1ClerkNonAdmin', false);
INSERT INTO employee(employee_id, company_id, email, balance, token, firstname, lastname, nickname, is_admin)
VALUES (1, 0, 'user2@clerkvest.de', 11, 'exampleToken1', 'Bike', 'User2', 'User2ClerkAdmin', true);
INSERT INTO employee(employee_id, company_id, email, balance, token, firstname, lastname, nickname, is_admin)
VALUES (2, 1, 'user1@company.de', 12, 'exampleToken2', 'Mike', 'User', 'User1CompanyNonAdmin', false);
INSERT INTO employee(employee_id, company_id, email, balance, token, firstname, lastname, nickname, is_admin)
VALUES (3, 1, 'user2@company.de', 13, 'exampleToken3', 'Bike', 'User2', 'User2CompanyAdmin', true);

INSERT INTO project(project_id, employee_id, company_id, link, name, description, goal, invested_in, reached,
                    image_id, funded_at)
VALUES (0, 0, 0, 'google.de', 'Google', 'Lets buy google', 100000, 10, false, null, NULL);
INSERT INTO project(project_id, employee_id, company_id, link, name, description, goal, invested_in, reached,
                    image_id, funded_at)
VALUES (1, 2, 1, 'amazon.com', 'Amazon', 'Dis', 5, 5, true, null, now());

INSERT INTO invest_in(invest_in_id, project_id, employee_id, investment)
VALUES (0, 0, 0, 5);
INSERT INTO invest_in(invest_in_id, project_id, employee_id, investment)
VALUES (1, 0, 1, 5);
INSERT INTO invest_in(invest_in_id, project_id, employee_id, investment)
VALUES (2, 1, 2, 5);

INSERT INTO employee_comment(employee_comment_id, employee_id, commenter_id, comment, date)
VALUES (0, 0, 0, 'Krass Brudi', now());
INSERT INTO employee_comment(employee_comment_id, employee_id, commenter_id, comment, date)
VALUES (1, 0, 1, 'Krass Brudi auch von mir', now());
INSERT INTO employee_comment(employee_comment_id, employee_id, commenter_id, comment, date)
VALUES (2, 2, 3, 'Krass Brudi', now());

INSERT INTO project_comment(project_comment_id, employee_id, project_id, title, text, date)
VALUES (0, 0, 0, 'Google ist ja Billig!', 'Brudi Google ist ja krass billig', now());

INSERT INTO email_token(email_token_id, employee_id, created_at, token)
VALUES (0, 0, now(), 'exampleToken0');
INSERT INTO email_token(email_token_id, employee_id, created_at, token)
VALUES (1, 1, now(), 'exampleToken1');
INSERT INTO email_token(email_token_id, employee_id, created_at, token)
VALUES (2, 2, now(), 'exampleToken2');
INSERT INTO email_token(email_token_id, employee_id, created_at, token)
VALUES (3, 3, now(), 'exampleToken3');
