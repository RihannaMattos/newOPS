package br.com.itb.projeto.newOPS.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itb.projeto.newOPS.model.entity.Usuario;
import br.com.itb.projeto.newOPS.rest.exception.ResourceNotFoundException;
import br.com.itb.projeto.newOPS.service.UsuarioService;

@RestController
@RequestMapping("/usuario")


public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        super();
        this.usuarioService = usuarioService;
    }

    @GetMapping("/test") // END POINT
    public String getTest() {
        return "Olá, Usuário!";
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable long id){

        Usuario usuario = usuarioService.findById(id);

        if (usuario != null) {
            return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
        }

        throw new ResourceNotFoundException("Usuário não encontrado!");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Usuario>> findAll(){

        List<Usuario> usuarios = usuarioService.findAll();

        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Usuario usuario) {
        Usuario _usuario = usuarioService.save(usuario);

        if(_usuario != null) {
            return ResponseEntity.ok().body("Usuário cadastrado com sucesso!");

        }
        throw new ResourceNotFoundException("Usuário já cadastrado!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
 
        Usuario _usuario = usuarioService._login(usuario.getEmail(), usuario.getRm(), usuario.getSenha());

        if (_usuario == null) {
            throw new ResourceNotFoundException("*** Dados Incorretos! ***");
        }

        return ResponseEntity.ok().body(_usuario);
    }

    @PutMapping("/alterarSenha/{id}")
    public ResponseEntity<?> alterarSenha(@PathVariable long id, @RequestBody Usuario usuario){
        Usuario _usuario = usuarioService.alterarSenha(id, usuario);

        if (_usuario != null) {
            return ResponseEntity.ok().body("Senha alterada com sucesso!");
        }

        throw new ResourceNotFoundException("Erro ao alterar senha!");
    }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<?> inativar(@PathVariable long id){
        Usuario _usuario = usuarioService.inativar(id);

        if (_usuario != null) {
            return ResponseEntity.ok().body("Conta de usuário inativada com sucesso!");
        }

        throw new ResourceNotFoundException("Erro ao inativar a conta de usuário!");
    }

    @PutMapping("/reativar/{id}")
    public ResponseEntity<?> reativar(@PathVariable long id){
        Usuario _usuario = usuarioService.reativar(id);

        if (_usuario != null) {
            return ResponseEntity.ok().body("Conta de usuário reativada com sucesso!");
        }

        throw new ResourceNotFoundException("Erro ao reativar a conta de usuário!");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.ok("Usuario deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Erro: " + e.getMessage());
        }
    }
}
