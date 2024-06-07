package com.artlanche.model.dtos;

import com.artlanche.model.entities.Usuario;

import lombok.Getter;

@Getter
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    private String role;
    private Boolean ativo;

    public UsuarioDTO(Usuario usuario) {
        if (usuario != null) {
            this.id = usuario.getId();
            this.nome = usuario.getNome();
            this.cpf = usuario.getCpf();
            this.email = usuario.getEmail();
            this.login = usuario.getLogin();
            this.senha = usuario.getSenha();
            this.role = usuario.getRole();
        }
    }

    public UsuarioDTO(String nome, String cpf, String email,String login, String senha) {
        if (nome != null) {
            this.nome = nome;
        }
        if (cpf != null) {
            this.cpf = cpf;
        }
        if (email != null) {
            this.email = email;
        }
        if (login != null) {
            this.login = login;
        }
        if (senha != null) {
            this.senha = senha;
        }
    }
    
}
