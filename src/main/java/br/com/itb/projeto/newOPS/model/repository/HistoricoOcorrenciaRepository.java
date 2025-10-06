package br.com.itb.projeto.newOPS.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itb.projeto.newOPS.model.entity.HistoricoOcorrencia;

@Repository


public interface HistoricoOcorrenciaRepository  extends JpaRepository<HistoricoOcorrencia, Long>{



}
