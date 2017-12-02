SET client_encoding = 'UTF8';

CREATE TABLE chats (
    id bigint NOT NULL,
    type text,
    title text
);

CREATE TABLE messages (
    id integer NOT NULL,
    msg_id bigint NOT NULL,
    date timestamp without time zone NOT NULL,
    text text NOT NULL,
    is_edited boolean NOT NULL,
    chat_id bigint,
    user_id bigint
);

CREATE SEQUENCE messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE messages_id_seq OWNED BY messages.id;

CREATE TABLE repeats (
    id integer NOT NULL,
    message text NOT NULL,
    chat_id bigint NOT NULL,
    user_id bigint NOT NULL,
    cron varchar(64) NOT NULL
);

CREATE SEQUENCE repeats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE repeats_id_seq OWNED BY repeats.id;

CREATE TABLE triggers (
    id integer NOT NULL,
    trigger text NOT NULL,
    message text NOT NULL,
    chat_id bigint NOT NULL,
    user_id bigint NOT NULL
);

CREATE SEQUENCE triggers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE triggers_id_seq OWNED BY triggers.id;

CREATE TABLE users (
    id bigint NOT NULL,
    first_name text,
    last_name text,
    user_name text
);

CREATE TABLE ignore_triggers (
    id integer NOT NULL,
    chat_id bigint NOT NULL,
    user_id bigint NOT NULL
);

CREATE SEQUENCE ignore_triggers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE ignore_triggers_id_seq OWNED BY ignore_triggers.id;

ALTER TABLE ONLY messages ALTER COLUMN id SET DEFAULT nextval('messages_id_seq'::regclass);

ALTER TABLE ONLY repeats ALTER COLUMN id SET DEFAULT nextval('repeats_id_seq'::regclass);

ALTER TABLE ONLY triggers ALTER COLUMN id SET DEFAULT nextval('triggers_id_seq'::regclass);

ALTER TABLE ONLY ignore_triggers ALTER COLUMN id SET DEFAULT nextval('ignore_triggers_id_seq'::regclass);

ALTER TABLE ONLY chats
    ADD CONSTRAINT chats_pkey PRIMARY KEY (id);

ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);

ALTER TABLE ONLY repeats
    ADD CONSTRAINT repeats_pkey PRIMARY KEY (id);

ALTER TABLE ONLY triggers
    ADD CONSTRAINT triggers_pkey PRIMARY KEY (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ignore_triggers
    ADD CONSTRAINT ignore_triggers_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ignore_triggers
    ADD CONSTRAINT ignore_triggers_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES chats(id);

ALTER TABLE ONLY ignore_triggers
    ADD CONSTRAINT ignore_triggers_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES chats(id);

ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);
