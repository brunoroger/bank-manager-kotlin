CREATE TABLE account (
	document varchar(255) NOT NULL,
	balance double precision NOT NULL,
	CONSTRAINT account_pkey PRIMARY KEY (document)
);