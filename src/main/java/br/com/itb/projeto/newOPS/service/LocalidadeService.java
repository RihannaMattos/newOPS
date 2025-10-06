package br.com.itb.projeto.newOPS.service;


import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.itb.projeto.newOPS.rest.exception.ResourceNotFoundException;
import br.com.itb.projeto.newOPS.model.entity.Localidade;
import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;
import br.com.itb.projeto.newOPS.model.entity.Usuario;
import br.com.itb.projeto.newOPS.model.repository.LocalidadeRepository;
import jakarta.transaction.Transactional;

@Service
public class LocalidadeService {
    private LocalidadeRepository localidadeRepository;

    public LocalidadeService(LocalidadeRepository localidadeRepository) {
        super();
        this.localidadeRepository = localidadeRepository;
    }


    public Localidade findById(long id) {
        Optional<Localidade> localidade = localidadeRepository.findById(id);

        if(localidade.isPresent()) {
            return localidade.get();
        }

        return null;
    }

    public List<Localidade> findAll(){
        List<Localidade> localidades = localidadeRepository.findAll();
        return localidades;
    }

    @Transactional
    public Localidade save(Localidade localidade) {
        Localidade _localidade = localidadeRepository.save(localidade);
        return _localidade;


    }

    public void deleteById(long id) {
        if (localidadeRepository.existsById(id)) {
            localidadeRepository.deleteById(id);
        } else {
            throw new RuntimeException("localidade não encontrada com o ID: " + id);
        }

    }

    @Transactional
    public Localidade alterarLocalidade(long id, Localidade localidadeAtualizada) {
        Optional<Localidade> optionalLocalidade = localidadeRepository.findById(id);

        if (optionalLocalidade.isPresent()) {
            Localidade localidadeExistente = optionalLocalidade.get();

            // Atualiza os campos com os valores recebidos
            localidadeExistente.setNome(localidadeAtualizada.getNome());
            localidadeExistente.setStatusLocal(localidadeAtualizada.getStatusLocal());

            return localidadeRepository.save(localidadeExistente);
        } else {
            throw new ResourceNotFoundException("Localidade com ID: " + id + " não encontrada.");
        }
    }

    @Transactional
    public Localidade inativar(long id) {
        Localidade localidade = findById(id);
        if (localidade == null) {
            throw new RuntimeException("Localidade com ID:" + id + "não encontrada.");
        }


        localidade.setStatusLocal("INATIVO");

        return localidadeRepository.save(localidade);
    }
    @Transactional
    public Localidade reativar(long id) {
        Localidade localidade = findById(id);
        if (localidade == null) {
            throw new RuntimeException("Localidade com ID não encontrada com o ID: " + id + "não encontrada.");
        }

        // Alterando o status para "Ativa"
        localidade.setStatusLocal("ATIVO");

        return localidadeRepository.save(localidade);  // Salva a ocorrência com o status atualizado
    }

}
