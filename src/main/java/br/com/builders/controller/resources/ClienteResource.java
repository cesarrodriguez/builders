package br.com.builders.controller.resources;

import lombok.Data;

import java.util.Date;

@Data
public class ClienteResource {

    private String id;
    private String nome;
    private Date dataNasciemnto;

}
