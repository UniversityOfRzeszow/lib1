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
