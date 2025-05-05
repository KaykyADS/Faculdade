-- Trigger: Impede defesa de TCC1 se percentual < 75 e TCC2 se percentual < 90
DELIMITER //
CREATE TRIGGER trg_verifica_defesa_tcc BEFORE INSERT ON apresentacao
FOR EACH ROW
BEGIN
    DECLARE perc DOUBLE;
    SELECT percentual_conclusao INTO perc FROM aluno WHERE grupo_id = NEW.grupo_id LIMIT 1;
    IF NEW.tipoTcc = 'TCC1' AND perc < 75 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Aluno(s) com menos de 75% não podem defender TCC1';
    END IF;
    IF NEW.tipoTcc = 'TCC2' AND perc < 90 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Aluno(s) com menos de 90% não podem defender TCC2';
    END IF;
END;//
DELIMITER ;

-- Trigger: Garante banca com exatamente 3 professores
DELIMITER //
CREATE TRIGGER trg_banca_tres_professores BEFORE INSERT ON apresentacao_banca
FOR EACH ROW
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total FROM apresentacao_banca WHERE apresentacao_id = NEW.apresentacao_id;
    IF total >= 3 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'A banca deve ter exatamente 3 professores';
    END IF;
END;//
DELIMITER ;

-- Trigger: Garante agendamento com pelo menos 1 semana de antecedência
DELIMITER //
CREATE TRIGGER trg_agendamento_uma_semana BEFORE INSERT ON apresentacao
FOR EACH ROW
BEGIN
    IF DATEDIFF(NEW.data, NOW()) < 7 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'A apresentação deve ser agendada com pelo menos 1 semana de antecedência';
    END IF;
END;//
DELIMITER ;

-- UDF: Conta grupos de um professor
CREATE FUNCTION qtd_grupos_professor(prof_id BIGINT) RETURNS INT DETERMINISTIC
RETURN (SELECT COUNT(*) FROM grupo WHERE orientador_id = prof_id);
