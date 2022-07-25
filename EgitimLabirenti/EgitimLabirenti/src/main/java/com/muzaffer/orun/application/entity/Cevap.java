package com.muzaffer.orun.application.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CEVAP")
public class Cevap implements Serializable {

	private static final long serialVersionUID = 4357581945439962894L;
	private Long id;
	private String tanim;
	private String baslik;
	private Soru soru;

	@Id
	@GeneratedValue(generator = "S_CEVAP", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "S_CEVAP", sequenceName = "S_CEVAP")
	@Column(name = "CEVAP_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "TANIM")
	public String getTanim() {
		return tanim;
	}

	public void setTanim(String tanim) {
		this.tanim = tanim;
	}

	@Column(name = "BASLIK")
	public String getBaslik() {
		return baslik;
	}

	public void setBaslik(String baslik) {
		this.baslik = baslik;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "SORU_ID")
	public Soru getSoru() {
		return soru;
	}

	public void setSoru(Soru soru) {
		this.soru = soru;
	}

}
