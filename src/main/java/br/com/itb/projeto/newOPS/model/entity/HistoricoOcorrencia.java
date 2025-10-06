package br.com.itb.projeto.newOPS.model.entity;

import java.time.LocalDateTime;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
@Table (name = "HistoricoOcorrencia")
public class HistoricoOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable=false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ocorrencia_id", nullable=false)
    private Ocorrencia ocorrencia;



    private LocalDateTime dataHistorico;

    private String descricao;
    private String statusHistorico;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public LocalDateTime getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(LocalDateTime dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatusHistorico() {
        return statusHistorico;
    }

    public void setStatusHistorico(String statusHistorico) {
        this.statusHistorico = statusHistorico;
    }
}
