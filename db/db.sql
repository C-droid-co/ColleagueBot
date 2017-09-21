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

CREATE TABLE repeaters (
    id integer NOT NULL,
    message text NOT NULL,
    chat_id bigint NOT NULL,
    user_id bigint NOT NULL,
    "time" time without time zone NOT NULL
);

CREATE SEQUENCE repeaters_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE repeaters_id_seq OWNED BY repeaters.id;

CREATE TABLE request (
    id integer NOT NULL,
    message text NOT NULL,
    chat_id bigint NOT NULL,
    requester_id bigint NOT NULL,
    executor_id bigint NOT NULL
);

CREATE SEQUENCE request_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE request_id_seq OWNED BY request.id;

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

ALTER TABLE ONLY messages ALTER COLUMN id SET DEFAULT nextval('messages_id_seq'::regclass);

ALTER TABLE ONLY repeaters ALTER COLUMN id SET DEFAULT nextval('repeaters_id_seq'::regclass);

ALTER TABLE ONLY request ALTER COLUMN id SET DEFAULT nextval('request_id_seq'::regclass);

ALTER TABLE ONLY triggers ALTER COLUMN id SET DEFAULT nextval('triggers_id_seq'::regclass);

ALTER TABLE ONLY chats
    ADD CONSTRAINT chats_pkey PRIMARY KEY (id);

ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);

ALTER TABLE ONLY repeaters
    ADD CONSTRAINT repeaters_pkey PRIMARY KEY (id);


ALTER TABLE ONLY request
    ADD CONSTRAINT request_pkey PRIMARY KEY (id);


ALTER TABLE ONLY triggers
    ADD CONSTRAINT triggers_pkey PRIMARY KEY (id);


ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES chats(id);


ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id);
