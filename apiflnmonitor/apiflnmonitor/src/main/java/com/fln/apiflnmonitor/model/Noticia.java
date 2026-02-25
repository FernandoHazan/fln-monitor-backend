package com.fln.apiflnmonitor.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    @Column(columnDefinition = "TEXT", unique = true)
    private String link;
    private String tipo;
    private LocalDateTime data;
    private String fonte;
    @Column(columnDefinition = "TEXT")
    private String conteudo;
    private String cidade;
    private String orgao;

    public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String descricao) {
        this.conteudo = descricao;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        this.data = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Noticia{" +
                "manchete='" + titulo + '\'' +
                ", link='" + link + '\'' +
                ", fonte='" + fonte + '\'' +
                '}';
    }


}

