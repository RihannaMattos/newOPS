package br.com.itb.projeto.newOPS.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.itb.projeto.newOPS.model.entity.Ocorrencia;
import br.com.itb.projeto.newOPS.service.OcorrenciaService;

@RestController
@RequestMapping("/ocorrencia")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @GetMapping("/test")
    public String getTest() {
        return "Olá, Ocorrencia!";
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Ocorrencia> findById(@PathVariable long id){
        Ocorrencia ocorrencia = ocorrenciaService.findById(id);
        if (ocorrencia != null) {
            return ResponseEntity.ok(ocorrencia);
        }
        throw new RuntimeException("Ocorrencia não encontrada!");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Ocorrencia>> findAll(){
        return ResponseEntity.ok(ocorrenciaService.findAll());
    }

    @GetMapping("/findPendentes")
    public ResponseEntity<List<Ocorrencia>> findPendentes() {
        return ResponseEntity.ok(ocorrenciaService.findPendentes());
    }

    @GetMapping("/findSolucionadas")
    public ResponseEntity<List<Ocorrencia>> findSolucionadas() {
        return ResponseEntity.ok(ocorrenciaService.findByStatus("SOLUCIONADA"));
    }
    
    @GetMapping("/pendentes")
    public ResponseEntity<List<Ocorrencia>> pendentes() {
    	return ResponseEntity.ok(ocorrenciaService.findByStatusOcorrencia("PENDENTE"));
    }

    @GetMapping("/solucionadas")
    public ResponseEntity<List<Ocorrencia>> solucionadas() {
        return ResponseEntity.ok(ocorrenciaService.findByStatusOcorrencia("SOLUCIONADA"));
    }
    
    
    @GetMapping("/usuario/{id}/ocorrencias")
    public ResponseEntity<List<Ocorrencia>> listarPorUsuario(@PathVariable Long id) {
        List<Ocorrencia> ocorrencias = ocorrenciaService.findByUsuarioId(id);
        return ResponseEntity.ok(ocorrencias);
    }


    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Ocorrencia ocorrencia) {
        ocorrenciaService.save(ocorrencia);
        return ResponseEntity.ok("Ocorrencia cadastrada com sucesso!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ocorrencia> update(@PathVariable long id, @RequestBody Ocorrencia ocorrenciaDetails) {
        return ResponseEntity.ok(ocorrenciaService.update(id, ocorrenciaDetails));
    }

    @PutMapping("/solucionar/{id}")
    public ResponseEntity<String> marcarComoSolucionada(@PathVariable long id) {
        ocorrenciaService.marcarComoSolucionada(id);
        return ResponseEntity.ok("Ocorrência marcada como solucionada!");
    }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<String> inativar(@PathVariable long id) {
        ocorrenciaService.inativar(id);
        return ResponseEntity.ok("Ocorrência inativada com sucesso.");
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<String> reativar(@PathVariable long id) {
        ocorrenciaService.reativar(id);
        return ResponseEntity.ok("Ocorrência reativada com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOcorrencia(@PathVariable long id) {
        ocorrenciaService.deleteById(id);
        return ResponseEntity.ok("Ocorrência deletada com sucesso.");
    }
}
