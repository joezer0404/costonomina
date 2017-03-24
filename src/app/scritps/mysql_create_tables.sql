-- Creando la base de datos

DROP DATABASE IF EXISTS SISTEMA;
CREATE DATABASE SISTEMA
	CHARACTER SET `utf8`
  	COLLATE `utf8_general_ci`;

-- Usando la base de datos

USE SISTEMA;

-- Tabla empleado

DROP TABLE IF EXISTS PERSONAL;
CREATE TABLE PERSONAL(
	cedula   		int 		PRIMARY KEY,
	nombre   		varchar(30)	NOT NULL,
	apellido 		varchar(30)	NOT NULL,
	cargo    		ENUM('ADM', 'MOD') NOT NULL,
	salarioM 		float  		NULL,
	salarioS 		float  		NULL,
	ivss4   		float  		NULL,
	ivss5   		float  		NULL, 
	inces    		float  		NULL,
	lph      		float  		NULL,
	prestaciones 	float  		NULL,
	utilidades 		float  		NULL,
	cestaticket 	float  		NULL
) ENGINE = InnoDB;

CREATE INDEX index_personal ON PERSONAL(nombre);

-- Tabla nomina

DROP TABLE IF EXISTS NOMINA;
CREATE TABLE NOMINA(
	id 				int auto_increment 	PRIMARY KEY,
	fecha 			date 				NOT NULL,
	empresa 		varchar(100) 		NOT NULL,
	rif 			varchar(100)		NOT NULL,
	direccion 		varchar(100) 		NOT NULL,
	salarioM 		float	 			NOT NULL,
	salarioS 		float	 			NULL,
	ivss4	 		float	 			NULL,
	ivss5	 		float	 			NULL,
	inces	 		float	 			NULL,
	lph		 		float	 			NULL,
	prestaciones	float	 			NULL,
	utilidades		float	 			NULL,
	cestaticket		float	 			NULL,
	total 			float				NULL
) ENGINE = InnoDB;

CREATE INDEX index_nomina ON NOMINA(fecha);

-- Tabla nomina personal

DROP TABLE IF EXISTS NOMINA_PERSONAL;
CREATE TABLE NOMINA_PERSONAL(
	cedula   	int 	NOT NULL,
	nomina 		int 	NOT NULL,
	PRIMARY KEY(cedula, nomina),
	FOREIGN KEY (cedula)
            REFERENCES PERSONAL(cedula)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION,
	FOREIGN KEY (nomina)
	    REFERENCES NOMINA(id)
	    	ON DELETE CASCADE
	    	ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE INDEX index_nomina_personal ON NOMINA_PERSONAL(nomina);

-- Tabla Gastos administracion

DROP TABLE IF EXISTS GASTOS_ADMINISTRACION;
CREATE TABLE GASTOS_ADMINISTRACION(
	id			int auto_increment		PRIMARY KEY,
	descripcion varchar(100)			NOT NULL,
	monto		float					NOT NULL
) ENGINE = InnoDB;

CREATE INDEX index_administracion ON GASTOS_ADMINISTRACION(descripcion);

-- Tabla Gastos ventas

DROP TABLE IF EXISTS GASTOS_VENTAS;
CREATE TABLE GASTOS_VENTAS(
	id			int auto_increment		PRIMARY KEY,
	descripcion varchar(100)			NOT NULL,
	monto		float					NOT NULL
) ENGINE = InnoDB;

CREATE INDEX index_ventas ON GASTOS_VENTAS (descripcion);

-- Tabla costo indirecto de fabricacion

DROP TABLE IF EXISTS COSTO_INDIRECTO;
CREATE TABLE COSTO_INDIRECTO(
	id			int auto_increment		PRIMARY KEY,
	descripcion varchar(100)			NOT NULL,
	monto		float					NOT NULL
) ENGINE = InnoDB;

CREATE INDEX index_indirecto ON COSTO_INDIRECTO(descripcion);

-- Tabla costo fijo

DROP TABLE IF EXISTS COSTO_FIJO;
CREATE TABLE COSTO_FIJO(
	id			int auto_increment		PRIMARY KEY,
	descripcion varchar(100)			NOT NULL,
	monto		float					NOT NULL
) ENGINE = InnoDB;

CREATE INDEX index_fijo ON COSTO_FIJO(descripcion);

-- Tabla materia prima

DROP TABLE IF EXISTS MATERIA_PRIMA;
CREATE TABLE MATERIA_PRIMA(
	id			 int auto_increment		PRIMARY KEY,
	descripcion  varchar (100)			NOT NULL, 	
	mts_pso	     float					NOT NULL,
	mts	     	 float					NOT NULL,
	precio	     float					NOT NULL,
	total	     float					NULL
) ENGINE = InnoDB;

CREATE INDEX index_materia ON MATERIA_PRIMA(descripcion);

-- Herencia de tabla materia prima no textil

DROP TABLE IF EXISTS NO_TEXTIL;
CREATE TABLE NO_TEXTIL(
	id		 int                                    PRIMARY KEY,
	peso 		 float					NOT NULL,
	FOREIGN KEY (id)
		REFERENCES MATERIA_PRIMA(id)
			ON DELETE CASCADE
			ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE INDEX index_no_textil ON NO_TEXTIL(id);

-- Herencia de tabla materia prima textil

DROP TABLE IF EXISTS TEXTIL;
CREATE TABLE TEXTIL(
	id		 int                                    PRIMARY KEY,
	unid_med  	 ENUM('KG', 'MT')                       NOT NULL, 
	peso 	  	 float					NOT NULL,
	FOREIGN KEY (id)
		REFERENCES MATERIA_PRIMA(id)
			ON DELETE CASCADE
			ON UPDATE CASCADE
) ENGINE = InnoDB;

CREATE INDEX index_textil ON TEXTIL(id);