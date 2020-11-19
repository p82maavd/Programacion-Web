CREATE TABLE `contactos` (
  `email` varchar(30) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellidos` varchar(40) NOT NULL,
  `fechanacimiento` date NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`email`)
)DEFAULT CHARSET=latin1;

CREATE TABLE `anuncios` (
`id` int( 11 ) NOT NULL AUTO_INCREMENT ,
`titulo` varchar( 30 ) DEFAULT NULL ,
`cuerpo` varchar( 600 ) DEFAULT NULL ,
`idautor` varchar( 30 ) NOT NULL ,
`estado` int( 1 ) NOT NULL DEFAULT '1',
`fechapublicacion` timestamp DEFAULT NULL ,
`fechainicio` timestamp DEFAULT NULL ,
`fechafinal` timestamp DEFAULT NULL ,
`tipo` varchar( 50 ) NOT NULL ,
PRIMARY KEY ( `id` ) ,
FOREIGN KEY `fk_anuncios_contactos` ( `idautor` ) REFERENCES contactos( email ) ON DELETE CASCADE
)DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE TABLE `destinatarios` (
  `idcontacto` varchar(30) NOT NULL,
  `idanuncio` int(11) NOT NULL,
  PRIMARY KEY (`idcontacto`,`idanuncio`),
  FOREIGN KEY `fk_destinatarios_anuncios` (`idanuncio`) REFERENCES anuncios( id ) ON DELETE CASCADE,
  FOREIGN KEY `fk_destinatarios_contactos` (`idcontacto`) REFERENCES contactos( email ) ON DELETE CASCADE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE `intereses` (
  `idintereses` int(11) NOT NULL AUTO_INCREMENT,
  `interes` varchar(30) NOT NULL,
  PRIMARY KEY (`idintereses`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE `intereses_anuncios` (
  `idanuncio` int(11) NOT NULL,
  `idinteres` int(11) NOT NULL,
  PRIMARY KEY (`idinteres`,`idanuncio`),
  FOREIGN KEY `fk_interesesanuncios_anuncios` (`idanuncio`) REFERENCES anuncios( id ) ON DELETE CASCADE,
  FOREIGN KEY `fk_interesesanuncios_intereses` (`idinteres`) REFERENCES intereses( idintereses ) ON DELETE CASCADE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

CREATE TABLE `intereses_contactos` (
  `emailcontacto` varchar(30) NOT NULL,
  `idinteres` int(11) NOT NULL,
  PRIMARY KEY (`idinteres`,`emailcontacto`),
  FOREIGN KEY `fk_intereses_contactos` (`emailcontacto`) REFERENCES contactos( email ) ON DELETE CASCADE,
  FOREIGN KEY `fk_interesescontactos_intereses` (`idinteres`) REFERENCES intereses( idintereses ) ON DELETE CASCADE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 ;
