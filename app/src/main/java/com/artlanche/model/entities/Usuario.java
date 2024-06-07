package com.artlanche.model.entities;

import com.artlanche.model.dtos.UsuarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@Getter
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200, nullable = false)
    private String nome;
    @Column(length = 200, nullable = false)
    private String cpf;
    @Column(length = 200)
    private String email;
    @Column(length = 200, nullable = false)
    private String login;
    @Column(length = 200, nullable = false)
    private String senha;
    @Column(length = 5, nullable = false)
    private String role; // somente adm ou comum
    @Column(nullable = false)
    private Boolean ativo;

    /**
     * Construtor padrão que deve ser usado para criar um usuário no banco
     * 
     * @param nome - do usuário
     * @param cpf - somente texto
     * @param email - opcional
     * @param login - para autenticar no sistema
     * @param senha - para autenticar no sistema
     * @throws IllegalArgumentException nos campos nome, cpf, email, login, senha e role 
     * caso estejam nulos ou vazios
     */
    public Usuario(UsuarioDTO usuarioDTO, String senhaCifrada) {
                
            setNome(usuarioDTO.getNome());
            setCpf(usuarioDTO.getCpf());
            setEmail(usuarioDTO.getEmail());
            setLogin(usuarioDTO.getLogin());
            setSenha(senhaCifrada);
            setRole("comum");
            setAtivo(true);
    }

    // Setters
    
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para nome");
        } else {
            this.nome = nome;
        }
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para CPF");
        } else {
            this.cpf = cpf;
        }
    }

    // Email não é obrigatório
    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Argumento nulo para Email");
        } else {
            this.email = email;
        }
    }

    public void setLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para login");
        } else {
            this.login = login;
        }
    }

    public void setSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para senha");
        } else {
            this.senha = senha;
        }
    }

    public void setRole(String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Argumento nulo ou vazio para role");
        } else if (role.equals("adm") || role.equals("comum")) {
            this.role = role;
        } else {
            throw new IllegalArgumentException("Role passada é inválida");
        }
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
