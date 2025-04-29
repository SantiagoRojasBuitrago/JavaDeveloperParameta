package com.parameta.empleado.repository;

import com.parameta.empleado.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    // No necesitamos agregar ningún método aquí.  JpaRepository proporciona métodos básicos (save, findById, findAll, deleteById, etc.)
}