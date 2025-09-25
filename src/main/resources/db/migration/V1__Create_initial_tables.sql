-- Tabela para armazenar os dados dos tutores (usuários)
CREATE TABLE tutor (
                       id BIGSERIAL PRIMARY KEY,
                       nome VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       senha VARCHAR(255) NOT NULL
);

-- Tabela para armazenar os dados dos pets, com relacionamento para um tutor
CREATE TABLE pet (
                     id BIGSERIAL PRIMARY KEY,
                     nome VARCHAR(100) NOT NULL,
                     especie VARCHAR(50),
                     raca VARCHAR(50),
                     data_nascimento DATE,
                     tutor_id BIGINT NOT NULL,
                     CONSTRAINT fk_pet_tutor FOREIGN KEY (tutor_id) REFERENCES tutor(id)
);

-- Tabela para armazenar os registros de vacinas, com relacionamento para um pet
CREATE TABLE vacina (
                        id BIGSERIAL PRIMARY KEY,
                        nome_vacina VARCHAR(100) NOT NULL,
                        data_aplicacao DATE NOT NULL,
                        data_reforco DATE,
                        pet_id BIGINT NOT NULL,
                        CONSTRAINT fk_vacina_pet FOREIGN KEY (pet_id) REFERENCES pet(id)
);