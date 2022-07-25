package com.muzaffer.orun.application.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.muzaffer.orun.application.enums.SoruTipEnum;

@Entity
@Table(name = "SORU")
public class Soru implements Serializable {

	private static final long serialVersionUID = 4956057573845900732L;

	private Long id;
	private String aciklama;
	private SoruTipEnum tip;
	private Integer sira;
	private AnketBaslik anketBaslik;

	@Id
	@GeneratedValue(generator = "S_SORU", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "S_SORU", sequenceName = "S_SORU")
	@Column(name = "SORU_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ACIKLAMA")
	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	@Column(name = "TIP")
	@Enumerated(EnumType.STRING)
	public SoruTipEnum getTip() {
		return tip;
	}

	public void setTip(SoruTipEnum tip) {
		this.tip = tip;
	}

	@Column(name = "SIRA")
	public Integer getSira() {
		return sira;
	}

	public void setSira(Integer sira) {
		this.sira = sira;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "ANKET_BASLIK_ID")
	public AnketBaslik getAnketBaslik() {
		return anketBaslik;
	}

	public void setAnketBaslik(AnketBaslik anketBaslik) {
		this.anketBaslik = anketBaslik;
	}
}
