-- password : Employee123
INSERT INTO "user" (email, password, role) VALUES ('john.doe@example.com', '$2a$10$1vX7z5K5g8z9y2m3n4p6r.t5u7w9x1y3z5A7B9C1D3E5F7G9H1I3J5', 'EMPLOYEE');

-- password Support101
INSERT INTO "user" (email, password, role) VALUES ('it.support2@example.com', '$2a$10$4pQ7rT9uV1wX3yZ5A7B9C.E1D3F5G7H9I1J3K5L7M9N1P3Q5R7T9U', 'IT_SUPPORT');

COMMIT;