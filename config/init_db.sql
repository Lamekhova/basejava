create table resume (
	uuid        CHAR(36) NOT NULL CONSTRAINT resume_pkey PRIMARY KEY,
	full_name   TEXT
);

alter table resume owner to postgres;

CREATE TABLE contact (
  id          SERIAL,
  resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT     NOT NULL,
  value       TEXT     NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);

create table section (
	id          SERIAL PRIMARY KEY,
	resume_uuid CHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
	type        TEXT     NOT NULL,
	content     TEXT     NOT NULL
);

create UNIQUE INDEX section_idx
	ON section (resume_uuid, type);

