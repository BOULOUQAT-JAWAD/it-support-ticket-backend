-- password : securePassword123
INSERT INTO "user" (email, password, role) VALUES ('john.doe@example.com', '$2a$10$4CxaVWSWzc1sbiLQs99/BOkjBF9SK4r3.JZVLJXmJYpAff3UXLWr6', 'EMPLOYEE');
INSERT INTO "user" (email, password, role) VALUES ('john.doe2@example.com', '$2a$10$4CxaVWSWzc1sbiLQs99/BOkjBF9SK4r3.JZVLJXmJYpAff3UXLWr6', 'EMPLOYEE');
INSERT INTO "user" (email, password, role) VALUES ('it.support2@example.com', '$2a$10$4CxaVWSWzc1sbiLQs99/BOkjBF9SK4r3.JZVLJXmJYpAff3UXLWr6', 'IT_SUPPORT');

COMMIT;
