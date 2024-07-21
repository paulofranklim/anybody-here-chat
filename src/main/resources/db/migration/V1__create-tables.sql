CREATE TABLE TB_USERS
(
    user_id       SERIAL PRIMARY KEY,
    username      VARCHAR(50) UNIQUE  NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE TB_CONTACTS
(
    user_id    INT REFERENCES tb_users (user_id) ON DELETE CASCADE,
    contact_id INT REFERENCES tb_users (user_id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, contact_id)
);
CREATE TABLE TB_GROUPS
(
    group_id   SERIAL PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL,
    created_by INT          REFERENCES tb_users (user_id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE tb_group_members
(
    group_id  INT REFERENCES tb_groups (group_id) ON DELETE CASCADE,
    user_id   INT REFERENCES tb_users (user_id) ON DELETE CASCADE,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (group_id, user_id)
);
CREATE TABLE tb_messages
(
    message_id   SERIAL PRIMARY KEY,
    sender_id    INT  REFERENCES tb_users (user_id) ON DELETE SET NULL,
    recipient_id INT  REFERENCES tb_users (user_id) ON DELETE SET NULL,
    group_id     INT REFERENCES tb_groups (group_id) ON DELETE CASCADE,
    content      TEXT NOT NULL,
    sent_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read      BOOLEAN   DEFAULT FALSE
);
CREATE TABLE tb_notifications
(
    notification_id SERIAL PRIMARY KEY,
    user_id         INT REFERENCES tb_users (user_id) ON DELETE CASCADE,
    message         TEXT NOT NULL,
    is_read         BOOLEAN   DEFAULT FALSE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);