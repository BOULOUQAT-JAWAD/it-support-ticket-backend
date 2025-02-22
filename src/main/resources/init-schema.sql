CREATE TABLE "user" (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR2(255) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    role VARCHAR2(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ticket (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    description VARCHAR2(1000) NOT NULL,
    priority VARCHAR2(50) NOT NULL,
    category VARCHAR2(50) NOT NULL,
    status VARCHAR2(50) DEFAULT 'NEW' NOT NULL,
    created_by NUMBER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ticket_user
        FOREIGN KEY (created_by)
        REFERENCES "user"(id)
);

CREATE TABLE comments (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ticket_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    content VARCHAR2(1000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comment_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES ticket(id),
    CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id)
        REFERENCES "user"(id)
);

CREATE TABLE audit_log (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ticket_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    action_type VARCHAR2(50) NOT NULL,
    details VARCHAR2(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES ticket(id),
    CONSTRAINT fk_audit_user
        FOREIGN KEY (user_id)
        REFERENCES "user"(id)
);