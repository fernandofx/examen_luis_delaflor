package com.examen.hexagonal.infraestructure.repository;

import com.examen.hexagonal.domain.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {



}
