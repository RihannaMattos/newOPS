package br.com.itb.projeto.newOPS.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itb.projeto.newOPS.model.entity.Usuario;

@Repository

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

    Usuario findByEmail(String email);
    Usuario findByRm(String rm);
}
