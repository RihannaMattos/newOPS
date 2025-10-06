package br.com.itb.projeto.newOPS.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itb.projeto.newOPS.model.entity.Localidade;

@Repository

public interface LocalidadeRepository extends JpaRepository<Localidade, Long> {
}
