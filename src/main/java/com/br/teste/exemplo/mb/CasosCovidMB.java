/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.teste.exemplo.mb;

import com.br.teste.exemplo.datamodel.CasosCovid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Vitor
 */
@ManagedBean
@ViewScoped
public class CasosCovidMB implements Serializable {

    private BarChartModel model;
    private EntityManagerFactory emf;
    private EntityManager entityManager;
    private UploadedFile file;
    private CasosCovid casosCovid;

    @PostConstruct
    public void inicializar() {
        inicializarEntityManager();
        inicializarCasoCovid();
        inicializarChartModel();
        inicializarSeries();
    }

    public void upload() throws IOException {
        InputStreamReader inp = new InputStreamReader(file.getInputStream());
        BufferedReader buff = new BufferedReader(inp);
        // 9 e 11 são as posições
        int numeroCasos = 0;
        buff.readLine();
        String campos[];
        String linha;

        String auxDiaDaSemama;

        entityManager.getTransaction().begin();

        while (buff.ready()) {

            linha = buff.readLine();

            if (linha.length() <= 1) {
                continue;
            }
            campos = linha.split(";");

            auxDiaDaSemama = campos[8];

            numeroCasos = Integer.parseInt(campos[11]);

            casosCovid = new CasosCovid();
            casosCovid.setSemanaEpisodio(auxDiaDaSemama);
            casosCovid.setNumeroDecasosPorSemana(new BigDecimal(numeroCasos));

            entityManager.persist(casosCovid);

        }

        entityManager.getTransaction().commit();
    }

    public void inicializarEntityManager() {
        emf = Persistence.createEntityManagerFactory("exemplo-pu");
        entityManager = emf.createEntityManager();
    }

    public void inicializarCasoCovid() {
        casosCovid = new CasosCovid();
    }

    public void inicializarChartModel() {
        model = new BarChartModel();
        model.setLegendPosition("ne");
        model.getAxis(AxisType.Y).setLabel("Total");
        model.getAxis(AxisType.X).setLabel("Semanas");
        model.setTitle("Total de casos do covid-19");
    }

    public void inicializarSeries() {

        List<CasosCovid> casosCovidList = entityManager.createQuery("SELECT c FROM CasosCovid c").getResultList();
        List<Integer> somatorio = new ArrayList<>();
        List<String> semanas = new ArrayList<>();

        int aux = 0;
        int s = 0;
        String semana;

        for (int i = 0; i < casosCovidList.size(); i++) {
       
            semana = casosCovidList.get(i).getSemanaEpisodio();
            
            if ((i + 1) < casosCovidList.size()) {

                if (casosCovidList.get(i + 1).getSemanaEpisodio().equalsIgnoreCase(casosCovidList.get(i).getSemanaEpisodio())) {
                    
                    aux = casosCovidList.get(i).getNumeroDecasosPorSemana().intValue();
                    s += aux;

                }else if(!casosCovidList.get(i + 1).getSemanaEpisodio().equalsIgnoreCase(casosCovidList.get(i).getSemanaEpisodio())){
      
                    s += casosCovidList.get(i).getNumeroDecasosPorSemana().intValue();
                    somatorio.add(s);
                    semanas.add(semana);
                    s = 0;
                    semana = null;
                }

            }else {

                 s += casosCovidList.get(i).getNumeroDecasosPorSemana().intValue();
                 somatorio.add(s);
                 semanas.add(semana);
                 semana = null;
                 s = 0;
            }

        }

        ChartSeries series = new ChartSeries("Número de casos");
        for (int i = 0; i < semanas.size(); i++) {
           series.set(semanas.get(i), somatorio.get(i));
        }
        model.addSeries(series);
    }

    public BarChartModel getModel() {
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
