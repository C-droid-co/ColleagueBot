INSERT INTO chats (id, title) VALUES (1, 'title1');
INSERT INTO chats (id, title) VALUES (2, 'title2');
INSERT INTO chats (id, title) VALUES (3, 'title3');

INSERT INTO users (id, first_name, last_name, user_name)
    VALUES (1, 'name1', 'lastname1', 'username1');
INSERT INTO users (id, first_name, last_name, user_name)
    VALUES (2, 'name2', 'lastname2', 'username2');
INSERT INTO users (id, first_name, last_name, user_name)
    VALUES (3, 'name3', 'lastname3', 'username3');

INSERT INTO triggers (trigger, message, chat_id, user_id)
    VALUES ('trigger1', 'message1', 1, 1);
INSERT INTO triggers (trigger, message, chat_id, user_id)
    VALUES ('trigger2', 'message2', 1, 1);
INSERT INTO triggers (trigger, message, chat_id, user_id)
    VALUES ('trigger3', 'message3', 1, 1);

INSERT INTO messages (msg_id, date, text, is_edited, chat_id, user_id)
    VALUES (1, '2017-01-01 12:00:00', 'text1', false, 1, 1);
INSERT INTO messages (msg_id, date, text, is_edited, chat_id, user_id)
    VALUES (2, '2017-01-02 12:00:00', 'text2', false, 1, 2);
INSERT INTO messages (msg_id, date, text, is_edited, chat_id, user_id)
    VALUES (3, '2017-01-03 12:00:00', 'text3', false, 1, 3);

INSERT INTO repeats (message, chat_id, user_id, cron)
    VALUES ('message1', 1, 1, 'cron1');
INSERT INTO repeats (message, chat_id, user_id, cron)
    VALUES ('message2', 1, 1, 'cron2');
INSERT INTO repeats (message, chat_id, user_id, cron)
    VALUES ('message3', 1, 1, 'cron3');

INSERT INTO ignore_triggers (chat_id, user_id)
    VALUES (3, 3);