// src/main/java/br/com/entitys/Pessoa.java
package br.com.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String sobrenome;
    private Integer idade;
    private String login;
    private String senha;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    private String sexo;
    private Boolean ativo;
    private Boolean inativo;

    // ✅ Mapeia List<String> corretamente no banco relacional
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "pessoa_setor",
        joinColumns = @JoinColumn(name = "pessoa_id")
    )
    @Column(name = "setor")
    private List<String> setor = new ArrayList<>();

    public Pessoa() {}

    public String getSetorFormatado() {
        if (setor == null || setor.isEmpty()) return "-";
        return String.join(", ", setor);
    }

    // ================= GETTERS/SETTERS =================
    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }
    public String getNome()                      { return nome; }
    public void setNome(String nome)             { this.nome = nome; }
    public String getSobrenome()                 { return sobrenome; }
    public void setSobrenome(String sobrenome)   { this.sobrenome = sobrenome; }
    public Integer getIdade()                    { return idade; }
    public void setIdade(Integer idade)          { this.idade = idade; }
    public Date getDataNascimento()              { return dataNascimento; }
    public void setDataNascimento(Date d)        { this.dataNascimento = d; }
    public String getSexo()                      { return sexo; }
    public void setSexo(String sexo)             { this.sexo = sexo; }
    public Boolean getAtivo()                    { return ativo; }
    public void setAtivo(Boolean ativo)          { this.ativo = ativo; }
    public Boolean getInativo()                  { return inativo; }
    public void setInativo(Boolean inativo)      { this.inativo = inativo; }
    public String getLogin()                     { return login; }
    public void setLogin(String login)           { this.login = login; }
    public String getSenha()                     { return senha; }
    public void setSenha(String senha)           { this.senha = senha; }
    public List<String> getSetor()               { return setor; }
    public void setSetor(List<String> setor)     { this.setor = setor; }

    @Override
    public int hashCode()                        { return Objects.hash(id); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pessoa)) return false;
        Pessoa other = (Pessoa) obj;
        return Objects.equals(id, other.id);
    }
}