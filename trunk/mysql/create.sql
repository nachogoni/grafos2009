DROP database IF EXISTS neo4j;

CREATE database IF NOT EXISTS neo4j DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;

use neo4j;

DROP TABLE IF EXISTS amigos;

CREATE TABLE amigos
(
    id          int AUTO_INCREMENT          not null,
    nombre      char(20)                    not null,
    primary key(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

-- Permisos
GRANT ALL ON neo4j.* TO 'neo4j'@'localhost' IDENTIFIED BY 'neo4j';
