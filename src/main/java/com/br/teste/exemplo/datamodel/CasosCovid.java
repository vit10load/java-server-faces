/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.teste.exemplo.datamodel;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Vitor
 */
@Entity
public class CasosCovid implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String semanaEpisodio;
    private BigDecimal numeroDecasosPorSemana;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemanaEpisodio() {
        return semanaEpisodio;
    }

    public void setSemanaEpisodio(String semanaEpisodio) {
        this.semanaEpisodio = semanaEpisodio;
    }

    public BigDecimal getNumeroDecasosPorSemana() {
        return numeroDecasosPorSemana;
    }

    public void setNumeroDecasosPorSemana(BigDecimal numeroDecasosPorSemana) {
        this.numeroDecasosPorSemana = numeroDecasosPorSemana;
    }

 
    
}
