package br.com.builders.domain.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Cliente {

    private String id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
}
