package br.com.itb.projeto.newOPS.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.itb.projeto.newOPS.model.entity.HistoricoOcorrencia;
import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;
import br.com.itb.projeto.newOPS.model.repository.HistoricoOcorrenciaRepository;
import br.com.itb.projeto.newOPS.model.repository.UsuarioRepository;
import br.com.itb.projeto.newOPS.model.repository.OcorrenciaRepository;

@Service

public class HistoricoOcorrenciaService {

    private HistoricoOcorrenciaRepository historicoocorrenciaRepository;
    private UsuarioRepository usuarioRepository;
    private OcorrenciaRepository ocorrenciaRepository;

    public HistoricoOcorrenciaService(HistoricoOcorrenciaRepository historicoocorrenciaRepository,
                                      UsuarioRepository usuarioRepository, OcorrenciaRepository ocorrenciaRepository) {
        super();
        this.historicoocorrenciaRepository = historicoocorrenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    public HistoricoOcorrencia findById(long id) {
        Optional<HistoricoOcorrencia> historicoocorrencia = historicoocorrenciaRepository.findById(id);

        if(historicoocorrencia.isPresent()) {
            return historicoocorrencia.get();
        }

        return null;
    }

    public List<HistoricoOcorrencia> findAll(){
        List<HistoricoOcorrencia> historicoocorrencia = historicoocorrenciaRepository.findAll();
        return historicoocorrencia;

    }

    public void inativarHistoricoOcorrencia(long id) {
        Optional<HistoricoOcorrencia> historicoOptional = historicoocorrenciaRepository.findById(id);

        if (historicoOptional.isPresent()) {
            HistoricoOcorrencia historico = historicoOptional.get();
            historico.setStatusHistorico("INATIVO");
            historicoocorrenciaRepository.save(historico);
        } else {
            throw new RuntimeException("Histórico de ocorrência não encontrado com o ID: " + id);
        }
    }

    public void reativarHistoricoOcorrencia(long id) {
        Optional<HistoricoOcorrencia> historicoOptional = historicoocorrenciaRepository.findById(id);

        if (historicoOptional.isPresent()) {
            HistoricoOcorrencia historico = historicoOptional.get();
            historico.setStatusHistorico("ATIVO");
            historicoocorrenciaRepository.save(historico);
        } else {
            throw new RuntimeException("Histórico de ocorrência não encontrado com o ID: " + id);
        }
    }

    public void deleteById(long id) {
        if (historicoocorrenciaRepository.existsById(id)) {
            historicoocorrenciaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Histórico de ocorrência não encontrado com o ID: " + id);
        }
    }

    public HistoricoOcorrencia save(HistoricoOcorrencia historicoOcorrencia) {
        historicoOcorrencia.setDataHistorico(LocalDateTime.now());
        historicoOcorrencia.setStatusHistorico("ATIVO");

        HistoricoOcorrencia _historicoOcorrencia =  historicoocorrenciaRepository.save(historicoOcorrencia);
        return _historicoOcorrencia;
    }

}
