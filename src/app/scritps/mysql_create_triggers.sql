-- Personal

DELIMITER $$
DROP TRIGGER IF EXISTS insert_personal$$
CREATE TRIGGER insert_personal
    BEFORE INSERT ON PERSONAL
    FOR EACH ROW
    BEGIN
        SET NEW.salarioS     = (NEW.salarioM / (30 * 7));
        SET NEW.ivss4        = (((NEW.salarioM * 12) / 52) * 0.12 * 4);
        SET NEW.ivss5        = (((NEW.salarioM * 12) / 52) * 0.12 * 5);
        SET NEW.inces        = (NEW.salarioM * 0.02);
        SET NEW.lph          = (NEW.salarioM * 0.02);
        SET NEW.prestaciones = (NEW.salarioM * 0.1825);
        SET NEW.utilidades   = (NEW.salarioM * 0.0925);
        SET NEW.cestaticket  = 6750;
    END$$
DELIMITER;

DELIMITER $$
DROP TRIGGER IF EXISTS update_personal$$
CREATE TRIGGER update_personal
    BEFORE UPDATE ON PERSONAL
    FOR EACH ROW
    BEGIN
        SET NEW.salarioS     = (NEW.salarioM / (30 * 7));
        SET NEW.ivss4        = (((NEW.salarioM * 12) / 52) * 0.12 * 4);
        SET NEW.ivss5        = (((NEW.salarioM * 12) / 52) * 0.12 * 5);
        SET NEW.inces        = (NEW.salarioM * 0.02);
        SET NEW.lph          = (NEW.salarioM * 0.02);
        SET NEW.prestaciones = (NEW.salarioM * 0.1825);
        SET NEW.utilidades   = (NEW.salarioM * 0.0925);
        SET NEW.cestaticket  = 6750;
    END$$
DELIMITER;
/*
DELIMITER $$
DROP PROCEDURE IF EXISTS procedure_personal$$
CREATE PROCEDURE procedure_personal(IN p_cedula int , IN p_salarioM float)
READS SQL DATA
    BEGIN
        UPDATE Personal
        SET
            salarioS     = (p_salarioM / (30 * 7)),
        ivss4        = (((p_salarioM * 12) / 52) * 0.12 * 4),
        ivss5        = (((p_salarioM * 12) / 52) * 0.12 * 5),
        inces        = (p_salarioM * 0.02),
        lph          = (p_salarioM * 0.02),
        prestaciones = (p_salarioM * 0.1825),
        utilidades   = (p_salarioM * 0.0925),
        cestaticket  = 6750 
        WHERE cedula = p_cedula;
    END$$
DELIMITER;
*/
-- Nomina

