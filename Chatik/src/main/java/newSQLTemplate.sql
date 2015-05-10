CREATE TABLE messages
(
  messageid serial NOT NULL,
  nickname character varying(255),
  text character varying(255),
  addeddate character varying(255),
  userid integer NOT NULL,
  modification integer NOT NULL,
  CONSTRAINT messages_pkey PRIMARY KEY (messageid)
)

CREATE TABLE users
(
  user_id serial NOT NULL,
  user_name character varying(255),
  password character varying(255),
  email character varying(255),
  member_since character varying(255),
  CONSTRAINT users_pkey PRIMARY KEY (user_id)
)
