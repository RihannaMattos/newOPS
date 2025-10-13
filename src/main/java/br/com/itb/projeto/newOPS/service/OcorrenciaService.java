package br.com.itb.projeto.newOPS.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;
import br.com.itb.projeto.newOPS.model.repository.OcorrenciaRepository;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository ocorrenciaRepository;

    public OcorrenciaService(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    public Ocorrencia findById(long id) {
        return ocorrenciaRepository.findById(id).orElse(null);
    }

    public List<Ocorrencia> findAll(){
        return ocorrenciaRepository.findAll();
    }

    public List<Ocorrencia> findPendentes() {
        return ocorrenciaRepository.findAll().stream()
                .filter(o -> {
                    String status = o.getStatusOcorrencia();
                    return status.equalsIgnoreCase("PENDENTE");
                })
                .collect(Collectors.toList());
    }

    public List<Ocorrencia> findByStatus(String status) {
        return ocorrenciaRepository.findAll().stream()
                .filter(o -> o.getStatusOcorrencia().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    
    public List<Ocorrencia> findByStatusOcorrencia(String status) {
        return ocorrenciaRepository.findByStatusOcorrencia(status);
    }
    
    @Transactional
    public Ocorrencia save(Ocorrencia ocorrencia) {
        ocorrencia.setDataOcorrencia(LocalDateTime.now());
        ocorrencia.setStatusOcorrencia("PENDENTE");
        return ocorrenciaRepository.save(ocorrencia);
    }

    @Transactional
    public Ocorrencia update(long id, Ocorrencia ocorrenciaDetails) {
        Ocorrencia ocorrencia = findById(id);

        if (ocorrencia == null) throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);

        ocorrencia.setDescricao(ocorrenciaDetails.getDescricao());
        ocorrencia.setStatusOcorrencia(ocorrenciaDetails.getStatusOcorrencia());
        ocorrencia.setLocalidade(ocorrenciaDetails.getLocalidade());

        return ocorrenciaRepository.save(ocorrencia);
    }

    @Transactional
    public Ocorrencia marcarComoSolucionada(long id) {
        Ocorrencia ocorrencia = findById(id);
        if (ocorrencia == null) throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);

        ocorrencia.setStatusOcorrencia("SOLUCIONADA");
        return ocorrenciaRepository.save(ocorrencia);
    }

    @Transactional
    public Ocorrencia inativar(long id) {
        Ocorrencia ocorrencia = findById(id);
        if (ocorrencia == null) throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);

        ocorrencia.setStatusOcorrencia("INATIVA");
        return ocorrenciaRepository.save(ocorrencia);
    }

    @Transactional
    public Ocorrencia reativar(long id) {
        Ocorrencia ocorrencia = findById(id);
        if (ocorrencia == null) throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);

        ocorrencia.setStatusOcorrencia("ATIVA");
        return ocorrenciaRepository.save(ocorrencia);
    }

    public void deleteById(long id) {
        if (!ocorrenciaRepository.existsById(id)) {
            throw new RuntimeException("Ocorrencia não encontrada com o ID: " + id);
        }
        ocorrenciaRepository.deleteById(id);
    }

	public List<Ocorrencia> findByUsuarioId(Long id) {
		 List<Ocorrencia> ocorrencias = ocorrenciaRepository.findByUsuarioId(id);
		return ocorrencias;
	}
}
