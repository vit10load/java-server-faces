/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.teste.exemplo.mb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;

/**
 *
 * @author vitcl
 */
@ManagedBean
@ViewScoped
public class ChartsMB implements Serializable {
    
    private BarChartModel model;

    public BarChartModel getModel() {
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }
    
    @PostConstruct
    public void inicializar(){
        model = new BarChartModel();
        inicializarSeries();
    }
    
    public void inicializarSeries(){
    
        ChartSeries chartSeries = new ChartSeries("Joao");
        
        List<Number> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(81);
        values.add(56);

        
        List<Object> m = new ArrayList<>();
        m.add("JAN");
        m.add("FEV");
        m.add("MAR");
        m.add("ABR");
        m.add("MAI");
      
        for (int i = 0; i < values.size(); i++) {
            chartSeries.set(m.get(i), values.get(i));
        }
        
        model.addSeries(chartSeries);
        
        
    }
    
    
    
}
