package com.muzaffer.orun.application.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ANKET_BASLIK")
public class AnketBaslik implements Serializable {

	private static final long serialVersionUID = -773706043170217692L;

	private String id;
	private String ad;
	private LocalDateTime baslangicTarih;
	private LocalDateTime bitisTarih;
	private String kayitEden;

	@Id
	@GeneratedValue(generator = "S_ANKET_BASLIK")
	@GenericGenerator(name = "S_ANKET_BASLIK", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "ANKET_BASLIK_ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "AD")
	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

    @Column(name = "BASLANGIC_TARIH")
    @Convert(converter = LocalDateTimeConverter.class)
	public LocalDateTime getBaslangicTarih() {
		return baslangicTarih;
	}

	public void setBaslangicTarih(LocalDateTime baslangicTarih) {
		this.baslangicTarih = baslangicTarih;
	}

    @Column(name = "BITIS_TARIH")
    @Convert(converter = LocalDateTimeConverter.class)
	public LocalDateTime getBitisTarih() {
		return bitisTarih;
	}

	public void setBitisTarih(LocalDateTime bitisTarih) {
		this.bitisTarih = bitisTarih;
	}

	@Column(name = "KAYIT_EDEN")
	public String getKayitEden() {
		return kayitEden;
	}

	public void setKayitEden(String kayitEden) {
		this.kayitEden = kayitEden;
	}
}
