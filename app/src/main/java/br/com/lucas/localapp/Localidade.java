package br.com.lucas.localapp;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Localidade {
  private   double lat;
   private double lon;
    private String data = String.valueOf(LocalDate.now());
    private String descricao;
    private String id;

    public Localidade(){
        super();
    }

    public Localidade(String data,String descricao, double lat, double lon, String id) {
        this.data = data;
        this.descricao = descricao;
        this.lat = lat;
        this.lon = lon;

        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId(){
        return id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("Descrição", this.descricao);
        result.put("Data", this.data);
        result.put("Lat:", this.lat);
        result.put("Lon", this.lon);

        return result;
    }
}
