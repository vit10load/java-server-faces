/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.teste.exemplo.mb;

import com.br.teste.exemplo.datamodel.Venda;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author vitcl
 */
@ManagedBean
@ViewScoped
public class ChartsMB implements Serializable {
    
    private BarChartModel model;
    private Venda venda;
    private EntityManagerFactory emf;
    private EntityManager entityManager;

    @PostConstruct
    public void inicializar() {
        inicializarEntityManager();
        inicializarVenda();
        inicializarChartModel();
        inicializarSeries();
    }

    public void inicializarEntityManager() {
        emf = Persistence.createEntityManagerFactory("exemplo-pu");
        entityManager = emf.createEntityManager();
    }

    public void salvar() {
        entityManager.getTransaction().begin();
        entityManager.persist(venda);
        entityManager.getTransaction().commit();
        inicializarVenda();
        inicializarChartModel();
        inicializarSeries();
    }

    public void inicializarVenda() {
        venda = new Venda();
    }

    public void inicializarChartModel() {
        model = new BarChartModel();
        
    }

    public void inicializarSeries() {
        List<Venda> vendas = entityManager.createQuery("SELECT v FROM Venda v").getResultList();
        HashMap<String, ChartSeries> seriesMap = new HashMap();
        ChartSeries series;
   
        for (Venda venda : vendas) {
        
            if (seriesMap.containsKey(venda.getNomeVendedor())) {
                series = seriesMap.get(venda.getNomeVendedor());
                series.set(String.valueOf(venda.getMes()).toUpperCase(), venda.getValorTotal());
            } else {
                series = new ChartSeries(venda.getNomeVendedor());
                series.set(String.valueOf(venda.getMes()).toUpperCase(), venda.getValorTotal());
                seriesMap.put(venda.getNomeVendedor(), series); 
            }     
            
            model.addSeries(series);

        }
    }

    public BarChartModel getModel() {
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}
