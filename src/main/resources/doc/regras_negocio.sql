CREATE DATABASE Faculdade
GO
USE Faculdade
GO
-- Trigger: Impede defesa de TCC1 se percentual < 75 e TCC2 se percentual < 90
CREATE TRIGGER trg_verifica_defesa_tcc ON apresentacao
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @grupo_id BIGINT, @tipoTcc VARCHAR(10), @data DATE;
    SELECT TOP 1 @grupo_id = grupo_id, @tipoTcc = tipoTcc, @data = data FROM inserted;
    IF @tipoTcc = 'TCC1' AND EXISTS (
        SELECT 1 FROM aluno WHERE grupo_id = @grupo_id AND percentualConclusao < 75
    )
    BEGIN
        RAISERROR('Erro: Aluno(s) com menos de 75 por cento não podem defender TCC1!', 16, 1);
        RETURN;
    END
    IF @tipoTcc = 'TCC2' AND EXISTS (
        SELECT 1 FROM aluno WHERE grupo_id = @grupo_id AND percentualConclusao < 90
    )
    BEGIN
        RAISERROR('Erro: Aluno(s) com menos de 90 por cento não podem defender TCC2!', 16, 1);
        RETURN;
    END
    IF DATEDIFF(DAY, GETDATE(), @data) < 7
    BEGIN
        RAISERROR('Erro: A apresentação deve ser agendada com pelo menos 1 semana de antecedência!', 16, 1);
        RETURN;
    END
END
GO
-- Trigger: Garante banca com exatamente 3 professores
CREATE TRIGGER trg_banca_tres_professores ON apresentacao_banca
INSTEAD OF INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @apresentacao_id BIGINT;
    SELECT TOP 1 @apresentacao_id = apresentacao_id FROM inserted;
    DECLARE @total INT;
    SELECT @total = COUNT(*) FROM apresentacao_banca WHERE apresentacao_id = @apresentacao_id;
    DECLARE @novos INT;
    SELECT @novos = COUNT(*) FROM inserted WHERE apresentacao_id = @apresentacao_id;
    IF (@total + @novos) > 3
    BEGIN
        RAISERROR('A banca deve ter exatamente 3 professores', 16, 1);
        RETURN;
    END
    INSERT INTO apresentacao_banca (apresentacao_id, professor_id)
    SELECT apresentacao_id, professor_id FROM inserted;
END
GO
CREATE TRIGGER trg_limite_alunos_grupo
ON aluno
INSTEAD OF INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    DELETE a
    FROM aluno a
    JOIN deleted d ON a.ra = d.ra;
    IF EXISTS (
        SELECT i.grupo_id
        FROM inserted i
        WHERE i.grupo_id IS NOT NULL
        GROUP BY i.grupo_id
        HAVING 
            COUNT(*) + (
                SELECT COUNT(*) 
                FROM aluno a 
                WHERE a.grupo_id = i.grupo_id
            ) > 4
    )
    BEGIN
        RAISERROR('Erro: O grupo não pode ter mais que 4 alunos!', 16, 1);
        RETURN;
    END
    INSERT INTO aluno (ra, nome, percentualConclusao, grupo_id)
    SELECT ra, nome, percentualConclusao, grupo_id
    FROM inserted;
END;
GO
-- UDF: Conta grupos de um professor
CREATE FUNCTION qtd_grupos_professor (@prof_id BIGINT)
RETURNS INT
AS
BEGIN
    DECLARE @total INT;

    -- Contando os grupos orientados pelo professor
    SELECT @total = COUNT(*) 
    FROM grupo 
    WHERE professor_id = @prof_id;

    RETURN @total;
END;
SELECT * FROM Grupo