package br.com.itb.projeto.newOPS.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException; // Importação obrigatória para tratar o erro de chave estrangeira

import br.com.itb.projeto.newOPS.model.entity.Usuario;
import br.com.itb.projeto.newOPS.model.repository.UsuarioRepository;
import br.com.itb.projeto.newOPS.rest.exception.ResourceNotFoundException;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super();
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario findById(long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        return null;
    }

    public List<Usuario> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    // MÉTODO NOVO: Implementação da Edição de Usuário (RESOLVE O 404 DA EDIÇÃO)
    @Transactional
    public Usuario updateUsuario(long id, Usuario usuarioDetalhes) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuarioAtualizado = usuarioExistente.get();
            // 1. Atualiza SOMENTE os campos corretos (nome, email, nivelAcesso)
            usuarioAtualizado.setNome(usuarioDetalhes.getNome());
            usuarioAtualizado.setEmail(usuarioDetalhes.getEmail());
            usuarioAtualizado.setNivelAcesso(usuarioDetalhes.getNivelAcesso());
            // 2. Salva e retorna o usuário atualizado
            return usuarioRepository.save(usuarioAtualizado);
        }
        // 3. Se o ID não for encontrado, retorna null
        return null;
    }
    // FIM DO MÉTODO NOVO

    // MÉTODO CORRIGIDO: Lança exceções específicas para o DELETE
    public void deleteById(long id) {
        if (!usuarioRepository.existsById(id)) {
            // Lança ResourceNotFoundException se o usuário não existir
            throw new ResourceNotFoundException("Usuario não encontrado com o ID: " + id);
        }
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Captura o erro de chave estrangeira (conflito com dbo.Ocorrencia) e o relança para o Controller
            throw new DataIntegrityViolationException("O usuário possui ocorrências associadas e não pode ser excluído.", e);
        }
    }
    // FIM DO MÉTODO CORRIGIDO

    public Usuario save(Usuario usuario) {
        Usuario _usuario = null;
        if (usuario.getEmail() != null && !usuario.getEmail().equals("")) {
            _usuario = usuarioRepository.findByEmail(usuario.getEmail());
        } else {
            _usuario = usuarioRepository.findByRm(usuario.getRm());
        }

        if (_usuario == null) {
            String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
            usuario.setSenha(senha);
            usuario.setDataCadastro(LocalDateTime.now());
            usuario.setStatusUsuario("ATIVO");

            if (usuario.getNivelAcesso().equals("ALUNO")) {
                String email = "rm" + usuario.getRm() + "@estudante.fieb.edu.br";
                usuario.setEmail(email);
            } else {
                usuario.setRm(null);
            }

            return usuarioRepository.save(usuario);
        }
        return null;
    }

    @Transactional
    public Usuario login(String email, String rm, String senha) {
        // Verifica se senha foi informada
        if (senha == null || senha.isEmpty()) {
            return null; // Senha é obrigatória
        }

        // Verifica se apenas um dos dois (email ou RM) foi informado
        boolean isEmailProvided = email != null && !email.isEmpty();
        boolean isRmProvided = rm != null && !rm.isEmpty();

        if (isEmailProvided == isRmProvided) {
            // Se ambos forem informados ou ambos nulos, retorna null
            return null;
        }

        Usuario _usuario = null;

        if (isEmailProvided) {
            // Login de técnico/administrador
            _usuario = usuarioRepository.findByEmail(email);
            if (_usuario != null && !_usuario.getNivelAcesso().equalsIgnoreCase("PROFESSOR")
                    && !_usuario.getNivelAcesso().equalsIgnoreCase("ADMIN")) {
                return null; // E-mail não pertence a técnico/admin
            }
        } else {
            // Login de aluno por RM
            _usuario = usuarioRepository.findByRm(rm);
            if (_usuario != null && !_usuario.getNivelAcesso().equalsIgnoreCase("ALUNO")) {
                return null; // RM não pertence a aluno
            }
        }

        if (_usuario != null && "ATIVO".equalsIgnoreCase(_usuario.getStatusUsuario())) {
            byte[] decodedPass = Base64.getDecoder().decode(_usuario.getSenha());
            if (new String(decodedPass).equals(senha)) {
                return _usuario;
            }
        }

        return null;
    }

    @Transactional
    public Usuario _login(String email, String rm, String senha) {
        Usuario usuario = null;
        if (email == null || email.isEmpty()) { // Uso do RM
            usuario = usuarioRepository.findByRm(rm);
        } else { // Uso do Email
            usuario = usuarioRepository.findByEmail(email);
        }

        if (usuario != null) {
            if (!usuario.getStatusUsuario().equals("INATIVO")) {
                byte[] decodedPass = Base64.getDecoder().decode(usuario.getSenha());
                if (new String(decodedPass).equals(senha)) {
                    return usuario;
                }
            }
        }
        return null;
    }

    @Transactional
    public Usuario alterarSenha(long id, Usuario usuario) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);
        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            String senha = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
            usuarioAtualizado.setSenha(senha);
            // Normalmente, a data de cadastro não seria alterada ao mudar a senha,
            // mas mantive a linha como estava no seu código.
            usuarioAtualizado.setDataCadastro(LocalDateTime.now());
            usuarioAtualizado.setStatusUsuario("ATIVO");
            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    @Transactional
    public Usuario inativar(long id) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);
        String senhaPadrao = "12345678";

        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            String senha = Base64.getEncoder().encodeToString(senhaPadrao.getBytes());
            usuarioAtualizado.setSenha(senha);
            usuarioAtualizado.setDataCadastro(LocalDateTime.now());
            usuarioAtualizado.setStatusUsuario("INATIVO");
            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }

    @Transactional
    public Usuario reativar(long id) {
        Optional<Usuario> _usuario = usuarioRepository.findById(id);
        String senhaPadrao = "12345678"; // A senha é resetada/padronizada

        if (_usuario.isPresent()) {
            Usuario usuarioAtualizado = _usuario.get();
            String senha = Base64.getEncoder().encodeToString(senhaPadrao.getBytes());
            usuarioAtualizado.setSenha(senha);
            usuarioAtualizado.setDataCadastro(LocalDateTime.now());
            // O seu código usava "REATIVO". Para a maioria dos sistemas, "ATIVO" seria o status correto após a reativação.
            // Mantive "REATIVO" conforme seu código, mas considere mudar para "ATIVO" se for o caso.
            usuarioAtualizado.setStatusUsuario("REATIVO");
            return usuarioRepository.save(usuarioAtualizado);
        }
        return null;
    }
}