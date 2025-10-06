package br.com.itb.projeto.newOPS.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


import br.com.itb.projeto.newOPS.model.entity.HistoricoOcorrencia;
import br.com.itb.projeto.newOPS.model.entity.Localidade;
import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;
import br.com.itb.projeto.newOPS.model.entity.Usuario;
import br.com.itb.projeto.newOPS.rest.exception.ResourceNotFoundException;
import br.com.itb.projeto.newOPS.service.LocalidadeService;

@RestController
@RequestMapping("/localidade")


public class LocalidadeController {

    private LocalidadeService localidadeService;

    public LocalidadeController(LocalidadeService localidadeService) {
        super();
        this.localidadeService = localidadeService;
    }

    @GetMapping("/test")
    public String getTest() {
        return "Olá, Localidade!";
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Localidade> findById(@PathVariable long id){

        Localidade localidade = localidadeService.findById(id);

        if (localidade != null) {
            return new ResponseEntity<Localidade>(localidade, HttpStatus.OK);
        }

        throw new ResourceNotFoundException("Localidade não encontrada!");
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<Localidade>> findAll(){

        List<Localidade> localidades = localidadeService.findAll();

        return new ResponseEntity<List<Localidade>>(localidades, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Localidade localidade) {

        Localidade _localidade = localidadeService.save(localidade);

        return ResponseEntity.ok()
                .body("Localidade cadastrada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocalidade(@PathVariable long id) {
        try {
            localidadeService.deleteById(id);
            return ResponseEntity.ok("Localidade deletada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLocalidade(@PathVariable long id, @RequestBody Localidade localidadeAtualizada) {
        try {
            Localidade atualizada = localidadeService.alterarLocalidade(id, localidadeAtualizada);
            return ResponseEntity.ok("Localidade atualizada com sucesso.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar localidade.");
        }
    }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<String> inativar(@PathVariable long id) {
        try {
            Localidade localidade = localidadeService.inativar(id);
            return ResponseEntity.ok("Localidade inativada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<String> reativar(@PathVariable long id) {
        try {
            Localidade localidade = localidadeService.reativar(id);
            return ResponseEntity.ok("Localidade reativada com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }
}
