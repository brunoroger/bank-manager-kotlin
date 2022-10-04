CREATE TABLE transaction (
	id varchar(255) NOT NULL,
	document varchar(255) NOT NULL,
	document_parent varchar(255) NULL,
	operation varchar(255) NOT NULL,
	amount double precision NOT NULL,
	date timestamp NOT NULL,
	CONSTRAINT transaction_pkey PRIMARY KEY (id),
	CONSTRAINT transaction_account_fkey FOREIGN KEY(document) REFERENCES account(document),
	CONSTRAINT transaction_account_parent_fkey FOREIGN KEY(document_parent) REFERENCES account(document)
);