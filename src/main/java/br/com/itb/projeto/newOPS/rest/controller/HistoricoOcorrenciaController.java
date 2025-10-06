package br.com.itb.projeto.newOPS.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.beans.factory.annotation.Autowired;
import br.com.itb.projeto.newOPS.model.entity.HistoricoOcorrencia;
import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;
import br.com.itb.projeto.newOPS.rest.exception.ResourceNotFoundException;
import br.com.itb.projeto.newOPS.service.HistoricoOcorrenciaService;


@RestController
@RequestMapping("/historicoocorrencia")
public class HistoricoOcorrenciaController {

    private HistoricoOcorrenciaService historicoocorrenciaService;

    @Autowired
    public HistoricoOcorrenciaController(HistoricoOcorrenciaService historicoocorrenciaService) {
        super();
        this.historicoocorrenciaService = historicoocorrenciaService;
    }



    @GetMapping("/test")
    public String getTest() {
        return "Olá, Histórico!";
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<HistoricoOcorrencia> findById(@PathVariable long id){

        HistoricoOcorrencia historicoocorrencia = historicoocorrenciaService.findById(id);

        if (historicoocorrencia != null) {
            return new ResponseEntity<HistoricoOcorrencia>(historicoocorrencia, HttpStatus.OK);
        }

        throw new ResourceNotFoundException("Histórico não encontrado!");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<HistoricoOcorrencia>> findAll(){

        List<HistoricoOcorrencia> historicoocorrencias = historicoocorrenciaService.findAll();

        return new ResponseEntity<List<HistoricoOcorrencia>>(historicoocorrencias, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody HistoricoOcorrencia historicoOcorrencia) {

        HistoricoOcorrencia _historicoOcorrencia = historicoocorrenciaService.save(historicoOcorrencia);

        return ResponseEntity.ok()
                .body("Historico da Ocorrencia cadastrado com sucesso!");


    }



    @PutMapping("/{id}/inativar")
    public ResponseEntity<Void> inativar(@PathVariable long id) {
        try {
            historicoocorrenciaService.inativarHistoricoOcorrencia(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/reativar")
    public ResponseEntity<Void> reativar(@PathVariable long id) {
        try {
            historicoocorrenciaService.reativarHistoricoOcorrencia(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistoricoOcorrencia(@PathVariable long id) {
        try {
            historicoocorrenciaService.deleteById(id);
            return ResponseEntity.ok("Histórico de ocorrência deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }
}
