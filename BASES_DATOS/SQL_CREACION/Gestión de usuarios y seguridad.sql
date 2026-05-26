-- Crear usuarios
CREATE USER 'jugador1'@'localhost' IDENTIFIED BY 'jugador1@1234' PASSWORD EXPIRE NEVER;
CREATE USER 'administrador1'@'%' IDENTIFIED BY 'administrador1@1234' PASSWORD EXPIRE INTERVAL 15 DAY;

-- Crear roles
CREATE ROLE 'jugador';
CREATE ROLE 'Admin';

-- Dar permisos a los roles
GRANT SELECT, INSERT ON desafio_grupo6.* TO 'jugador';
GRANT ALL PRIVILEGES ON desafio_grupo6.* TO 'Admin';

-- Asignar roles a los usuarios
GRANT 'jugador' TO 'jugador1'@'localhost';
GRANT 'Admin' TO 'administrador1'@'%';

-- Crear vista
USE desafio_grupo6;

CREATE VIEW stats_player AS
SELECT * FROM jugador;

-- Dar acceso a la vista al rol jugador
GRANT SELECT ON desafio_grupo6.stats_player TO 'jugador';



CREATE VIEW admin_profile as
select *
from administrador;

GRANT SELECT ON desafio_grupo6.stats_player TO 'administrador';
 
FLUSH PRIVILEGES;

