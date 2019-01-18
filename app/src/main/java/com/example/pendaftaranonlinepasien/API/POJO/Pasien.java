package com.example.pendaftaranonlinepasien.API.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pasien implements Serializable {

    @SerializedName("id")
    int id;
    @SerializedName("user_id")
    int user_id;
    @SerializedName("norm")
    int norm;
    @SerializedName("nama")
    String nama;
    @SerializedName("ttl")
    String ttl;
    @SerializedName("nik")
    String nik;
    @SerializedName("jk")
    String jk;
    @SerializedName("kerja")
    String kerja;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("hp")
    String hp;
    @SerializedName("ibu")
    String ibu;
    @SerializedName("role")
    String role;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;

    /**
     * ======================= Setter =============================
     */
    public void setIdUser(int idUser) {
        this.user_id = idUser;
    }
    public void setNorm(int norm) {
        this.norm = norm;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
    public void setNik(String nik) {
        this.nik = nik;
    }
    public void setJK(String jk) {
        this.jk = jk;
    }
    public void setKerja(String kerja) {
        this.kerja = kerja;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public void setHp(String hp) {
        this.hp = hp;
    }
    public void setIbu(String ibu) {
        this.ibu = ibu;
    }
    public void setRole(String role) {
        this.role = role;
    }
    /**
     * ==================== End of Setter ==========================
     */

    public int getId() {
        return id;
    }
    public int getIdUser() {
        return user_id;
    }
    public int getNorm() {
        return norm;
    }
    public String getNama() {
        return nama;
    }
    public String getTtl() {
        return ttl;
    }
    public String getNik() {
        return nik;
    }
    public String getJK() {
        return jk;
    }
    public String getKerja() {
        return kerja;
    }
    public String getAlamat() {
        return alamat;
    }
    public String getHp() {
        return hp;
    }
    public String getIbu() {
        return ibu;
    }
    public String getRole() {
        return role;
    }
    /**
     * ==================== End of Getter ==========================
     */
}
