package com.artlanche.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * Representa um usuário no banco de dados
 * 
 * @since 1.0
 * @author Jean Maciel
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;
    private String role; // somente adm ou comum
    private Boolean ativo;

    /**
     * Construtor padrão que deve ser usado para criar um usuário no banco
     * 
     * @param nome
     * @param cpf
     * @param email
     * @param login
     * @param senha
     * @param role
     * @param ativo
     * @throws IllegalArgumentException nos campos nome, cpf, email, login, senha e role 
     * caso estejam nulos ou vazios
     */
    public Usuario(String nome, String cpf, String email, String login, 
                    String senha, String role, boolean ativo) {
                
            setNome(nome);
            setCpf(cpf);
            setEmail(email);
            setLogin(login);
            setSenha(senha);
            setRole(role);
            setAtivo(ativo);
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para nome");
        } else {
            this.nome = nome;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para CPF");
        } else {
            this.cpf = cpf;
        }
    }

    public String getEmail() {
        return email;
    }

    // Email não é obrigatório
    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Argumento nulo para Email");
        } else {
            this.email = email;
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para login");
        } else {
            this.login = login;
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para senha");
        } else {
            this.senha = senha;
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para role");
        } else {
            this.role = role;
        }
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
