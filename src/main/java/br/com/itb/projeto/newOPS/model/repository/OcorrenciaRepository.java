package br.com.itb.projeto.newOPS.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
	List<Ocorrencia> findByUsuarioId(Long id);

    List<Ocorrencia> findByStatusOcorrencia(String status);

}
