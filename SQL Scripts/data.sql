USE DMS
-- inserting data into document
INSERT INTO document (name, type, created_by, created_at)
SELECT 'Contract No 5/AB', 'PDF', 'petr', GETDATE()
UNION ALL
SELECT 'Contract No 5/BV', 'PDF', 'petr', GETDATE()
UNION ALL
SELECT 'Contract No 888/A', 'PDF', 'petr', GETDATE()
UNION ALL
SELECT 'Photo - front of the building', 'JPG', 'petr', GETDATE()
UNION ALL
SELECT 'Photo - top of the building', 'JPG', 'petr', GETDATE()
UNION ALL
SELECT 'Photo - inventory', 'JPG', 'petr', GETDATE()
UNION ALL
SELECT 'Photo - left view', 'TIFF', 'petr', GETDATE()
UNION ALL
SELECT 'Photo - right view', 'TIFF', 'petr', GETDATE()

-- inserting data into protocol  
INSERT INTO protocol (name, status, created_by, created_at)
SELECT 'Project - Marketing', 'NEW', 'petr', GETDATE()
INSERT INTO protocol (name, status, created_by, created_at)
SELECT 'Investment - new products', 'NEW', 'petr', GETDATE()
INSERT INTO protocol (name, status, created_by, created_at)
SELECT 'Market research', 'NEW', 'petr', GETDATE()


--inserting relation between protocol and its documents
INSERT INTO protocol_document values (1, 1)
INSERT INTO protocol_document values (1, 3)
INSERT INTO protocol_document values (1, 4)
INSERT INTO protocol_document values (2, 2)
INSERT INTO protocol_document values (2, 3)
INSERT INTO protocol_document values (2, 5)
INSERT INTO protocol_document values (2, 7)


