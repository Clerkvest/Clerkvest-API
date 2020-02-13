CREATE TABLE company
(
    company_id   BIGSERIAL PRIMARY KEY,
    name         character varying(255) NOT NULL,
    domain       character varying(255) NOT NULL,
    image_id     bigint,
    pay_amount   DECIMAL                NOT NULL,
    pay_interval integer                NOT NULL,
    invite_only  boolean                NOT NULL
);

ALTER TABLE company
    OWNER TO postgres;


CREATE TABLE email_token
(
    email_token_id BIGSERIAL PRIMARY KEY,
    employee_id    bigint                                    NOT NULL,
    created_at     timestamp without time zone DEFAULT now() NOT NULL,
    token          character varying(255)                    NOT NULL
);

ALTER TABLE email_token
    OWNER TO postgres;

CREATE TABLE employee
(
    employee_id BIGSERIAL PRIMARY KEY,
    company_id  bigint                 NOT NULL,
    email       character varying(255) NOT NULL,
    balance     DECIMAL                NOT NULL,
    token       character varying(255),
    login_token character varying(255),
    firstname   character varying(255) NOT NULL,
    lastname    character varying(255) NOT NULL,
    nickname    character varying(255) NOT NULL,
    is_admin    boolean                NOT NULL,
    CHECK ( balance >= 0 )
);

ALTER TABLE employee
    OWNER TO postgres;


CREATE TABLE employee_comment
(
    employee_comment_id BIGSERIAL PRIMARY KEY,
    employee_id         bigint                                    NOT NULL,
    commenter_id        bigint                                    NOT NULL,
    comment             character varying(255)                    NOT NULL,
    date                timestamp without time zone DEFAULT now() NOT NULL
);

ALTER TABLE employee_comment
    OWNER TO postgres;

CREATE TABLE project
(
    project_id  BIGSERIAL PRIMARY KEY,
    employee_id bigint                 NOT NULL,
    company_id  bigint                 NOT NULL,
    link        character varying(255) NOT NULL,
    name        character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    goal        DECIMAL                NOT NULL,
    invested_in DECIMAL                NOT NULL,
    reached     boolean                NOT NULL,
    image_id    bigint,
    created_at  timestamp WITHOUT TIME ZONE DEFAULT now(),
    funded_at   timestamp WITHOUT TIME ZONE
);

ALTER TABLE project
    OWNER TO postgres;

CREATE TABLE invest_in
(
    invest_in_id BIGSERIAL PRIMARY KEY,
    project_id   bigint  NOT NULL,
    employee_id  bigint  NOT NULL,
    investment   DECIMAL NOT NULL
);

ALTER TABLE invest_in
    OWNER TO postgres;

CREATE TABLE project_comment
(
    project_comment_id BIGSERIAL PRIMARY KEY,
    employee_id        bigint                                    NOT NULL,
    project_id         bigint                                    NOT NULL,
    title              character varying(255)                    NOT NULL,
    text               character varying(255)                    NOT NULL,
    date timestamp WITHOUT TIME ZONE DEFAULT now()
);

CREATE TABLE image
(
    image_id       BIGSERIAL PRIMARY KEY,
    content_Id     character varying(255) NOT NULL,
    content_Length bigint
);

ALTER TABLE company
    OWNER TO postgres;

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

CREATE OR REPLACE FUNCTION my_investment_insert_function()
    RETURNS trigger AS
$BODY$
BEGIN
    UPDATE project AS p SET invested_in = invested_in + new.investment WHERE (project_id = new.project_id);
    UPDATE employee AS e SET balance = balance - new.investment WHERE (employee_id = new.employee_id);
    IF (Select invested_in FROM project WHERE project_id = new.project_id) >=
       (Select goal FROM project WHERE project_id = new.project_id) THEN
        UPDATE project AS p SET reached = true WHERE (project_id = new.project_id);
    end if;
    RETURN NEW;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE -- Says the function is implemented in the plpgsql language; VOLATILE says the function has side effects.
    COST 100;

CREATE TRIGGER investment_insert_trigger
    BEFORE INSERT
    ON invest_in
    FOR EACH ROW
EXECUTE PROCEDURE my_investment_insert_function();

CREATE OR REPLACE FUNCTION my_investment_delete_function()
    RETURNS trigger AS
$BODY$
BEGIN
    IF (Select reached FROM project WHERE project_id = old.project_id) THEN
        RAISE EXCEPTION 'Cannot removed Investment from finished Project';
    end if;
    UPDATE project AS p SET invested_in = invested_in - old.investment WHERE (project_id = old.project_id);
    UPDATE employee AS e SET balance = balance + old.investment WHERE (employee_id = old.employee_id);
    RETURN OLD;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE -- Says the function is implemented in the plpgsql language; VOLATILE says the function has side effects.
    COST 100;

CREATE TRIGGER investment_delete_trigger
    BEFORE DELETE
    ON invest_in
    FOR EACH ROW
EXECUTE PROCEDURE my_investment_delete_function();

CREATE OR REPLACE FUNCTION payOut(companyID bigint, payOut decimal)
    RETURNS void AS
$BODY$
DECLARE
    i employee;
BEGIN
    FOR i IN SELECT * FROM employee WHERE employee.company_id = companyID
        LOOP
            UPDATE i AS e SET e.balance = e.balance + payOut;
        END LOOP;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE -- Says the function is implemented in the plpgsql language; VOLATILE says the function has side effects.
    COST 100;

INSERT INTO company(company_id, name, domain, image_id, pay_amount, pay_interval, invite_only)
VALUES (0, 'Clerk GmbH', 'clerkvest.de', null, 25, 1, true);
INSERT INTO company(company_id, name, domain, image_id, pay_amount, pay_interval, invite_only)
VALUES (1, 'Company GmbH', 'company.de', null, 15, 30, false);

INSERT INTO employee(employee_id, company_id, email, balance, token, login_token, firstname, lastname, nickname,
                     is_admin)
VALUES (0, 0, 'user1@clerkvest.de', 10, 'exampleToken0', null, 'Mike', 'User', 'User1ClerkNonAdmin', false);
INSERT INTO employee(employee_id, company_id, email, balance, token, login_token, firstname, lastname, nickname,
                     is_admin)
VALUES (1, 0, 'user2@clerkvest.de', 11, 'exampleToken1', null, 'Bike', 'User2', 'User2ClerkAdmin', true);
INSERT INTO employee(employee_id, company_id, email, balance, token, login_token, firstname, lastname, nickname,
                     is_admin)
VALUES (2, 1, 'user1@company.de', 12, 'exampleToken2', null, 'Mike', 'User', 'User1CompanyNonAdmin', false);
INSERT INTO employee(employee_id, company_id, email, balance, token, login_token, firstname, lastname, nickname,
                     is_admin)
VALUES (3, 1, 'user2@company.de', 13, 'exampleToken3', null, 'Bike', 'User2', 'User2CompanyAdmin', true);

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

SELECT setval('project_comment_project_comment_id_seq', 1, true);
SELECT setval('company_company_id_seq', 2, true);
SELECT setval('employee_comment_employee_comment_id_seq', 3, true);
SELECT setval('employee_employee_id_seq', 4, true);
SELECT setval('invest_in_invest_in_id_seq', 3, true);
SELECT setval('project_project_id_seq', 2, true);
SELECT setval('email_token_email_token_id_seq', 4, true);