package br.com.lucas.localapp;

public class Localidade {
    double lat;
    double lon;
    String data, descricao;

    public Localidade(){

    }

    public Localidade(double lat, double lon, String data, String descricao) {
        this.lat = lat;
        this.lon = lon;
        this.data = data;
        this.descricao = descricao;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
