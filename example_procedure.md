Creating tables

```sql
-- Table: db.woman

-- DROP TABLE db.woman;

CREATE TABLE db.woman
(
  id numeric NOT NULL,
  CONSTRAINT woman_pk PRIMARY KEY (id),
  CONSTRAINT u_w_id UNIQUE (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE db.woman
  OWNER TO postgres;


-- Table: db.man

-- DROP TABLE db.man;

CREATE TABLE db.man
(
  id numeric NOT NULL,
  name character varying,
  CONSTRAINT id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE db.man
  OWNER TO postgres;


-- Table: db.dates

-- DROP TABLE db.dates;

CREATE TABLE db.dates
(
  m_id numeric,
  w_id numeric,
  CONSTRAINT m_fk FOREIGN KEY (m_id)
      REFERENCES db.man (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT w_fk FOREIGN KEY (w_id)
      REFERENCES db.woman (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE db.dates
  OWNER TO postgres;
```






```sql
CREATE OR REPLACE FUNCTION insertMan(in name varchar(20)) RETURNS integer AS $$

	DECLARE
	vn_id numeric;
        BEGIN
                SELECT count(*) INTO vn_id FROM db.man;
		vn_id = vn_id+1;
                INSERT INTO db.man (id, name) VALUES (vn_id, name);
        RETURN vn_id;
                
END
$$
  LANGUAGE 'plpgsql';
```
