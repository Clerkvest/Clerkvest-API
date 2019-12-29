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
