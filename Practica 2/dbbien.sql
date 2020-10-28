CREATE TABLE `contactos` (
  `email` varchar(30) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellidos` varchar(40) NOT NULL,
  `fechanacimiento` date NOT NULL,
  PRIMARY KEY (`email`)
)DEFAULT CHARSET=latin1;

CREATE TABLE `anuncios` (
`id` int( 11 ) NOT NULL AUTO_INCREMENT ,
`titulo` varchar( 30 ) DEFAULT NULL ,
`cuerpo` varchar( 400 ) DEFAULT NULL ,
`idautor` varchar( 30 ) NOT NULL ,
`estado` int( 1 ) NOT NULL DEFAULT '1',
`fechapublicacion` date DEFAULT NULL ,
`fechainicio` date DEFAULT NULL ,
`fechafinal` date DEFAULT NULL ,
PRIMARY KEY ( `id` ) ,
FOREIGN KEY `fk_anuncios_contactos` ( `idautor` ) REFERENCES contactos( email ) ON DELETE CASCADE
)DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE TABLE `destinatarios` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `idcontacto` varchar(30) NOT NULL,
  `idanuncio` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY `fk_destinatarios_anuncios` (`idanuncio`) REFERENCES anuncios( id ) ON DELETE CASCADE,
  FOREIGN KEY `fk_destinatarios_contactos` (`idcontacto`) REFERENCES contactos( email ) ON DELETE CASCADE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE `intereses` (
  `idintereses` int(11) NOT NULL AUTO_INCREMENT,
  `interes` varchar(30) NOT NULL,
  PRIMARY KEY (`idintereses`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE `intereses_anuncios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idanuncio` int(11) NOT NULL,
  `idinteres` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY `fk_interesesanuncios_anuncios` (`idanuncio`) REFERENCES anuncios( id ) ON DELETE CASCADE,
  FOREIGN KEY `fk_interesesanuncios_intereses` (`idinteres`) REFERENCES intereses( idintereses ) ON DELETE CASCADE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
--Mirar sI PONER COMO CLAVE PRIMARIA EL ID O UN CONJUNTO DE EMAIL Y IDINTERES
CREATE TABLE `intereses_contactos` (
  --`id` int(11) NOT NULL AUTO_INCREMENT,
  `emailcontacto` varchar(30) NOT NULL,
  `idinteres` int(11) NOT NULL,
  PRIMARY KEY (`idinteres`,`emailcontacto`),
  FOREIGN KEY `fk_intereses_contactos` (`emailcontacto`) REFERENCES contactos( email ) ON DELETE CASCADE,
  FOREIGN KEY `fk_interesescontactos_intereses` (`idinteres`) REFERENCES intereses( idintereses ) ON DELETE CASCADE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 ;
