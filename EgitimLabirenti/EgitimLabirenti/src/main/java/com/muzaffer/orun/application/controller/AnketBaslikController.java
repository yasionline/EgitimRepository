package com.muzaffer.orun.application.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.muzaffer.orun.application.db.QueryResult;
import com.muzaffer.orun.application.entity.AnketBaslik;
import com.muzaffer.orun.application.entity.Cevap;
import com.muzaffer.orun.application.entity.Soru;
import com.muzaffer.orun.application.enums.SoruTipEnum;
import com.muzaffer.orun.application.service.AnketBaslikService;

@Controller
@Scope("view")
public class AnketBaslikController implements Serializable {

	private static final long serialVersionUID = 7335259085794050435L;

	private AnketBaslik anketBaslik;
	private LazyDataModel<AnketBaslik> anketBaslikListe;
	private LazyDataModel<Soru> soruListesi;
	private LazyDataModel<Cevap> cevapListesi;
	private Soru soru;
	private List<SoruTipEnum> soruTipList;
	private Cevap cevap;

	@Autowired
	private transient AnketBaslikService anketBaslikService;

	public AnketBaslik getAnketBaslik() {
		return anketBaslik;
	}

	public void setAnketBaslik(AnketBaslik anketBaslik) {
		this.anketBaslik = anketBaslik;
	}

	public LazyDataModel<AnketBaslik> getAnketBaslikListe() {
		return anketBaslikListe;
	}

	public Soru getSoru() {
		return soru;
	}

	public void setSoru(Soru soru) {
		this.soru = soru;
	}

	public LazyDataModel<Soru> getSoruListesi() {
		return soruListesi;
	}

	public List<SoruTipEnum> getSoruTipList() {
		return soruTipList;
	}

	public LazyDataModel<Cevap> getCevapListesi() {
		return cevapListesi;
	}

	public Cevap getCevap() {
		return cevap;
	}

	public void setCevap(Cevap cevap) {
		this.cevap = cevap;
	}

	@PostConstruct
	public void init() {
		anketBaslik = null;
		anketBaslikListe = new LazyDataModel<AnketBaslik>() {
			private static final long serialVersionUID = -1151580530593061589L;
			@Override
			public List<AnketBaslik> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
				QueryResult<AnketBaslik> qr = anketBaslikService.load(first, pageSize, sortField, sortOrder, filterBy);
				setRowCount(qr.getToplamKayitSayisi().intValue());
				return qr.getListe();
			}
		};

		soruListesi  = new LazyDataModel<Soru>() {
			private static final long serialVersionUID = 6452448078505132516L;
			@Override
			public List<Soru> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
				QueryResult<Soru> qr = anketBaslikService.loadSoru(first, pageSize, filterBy, anketBaslik);
				setRowCount(qr.getToplamKayitSayisi().intValue());
				return qr.getListe();
			}
		};

		cevapListesi  = new LazyDataModel<Cevap>() {
			private static final long serialVersionUID = -8355533855821423550L;
			@Override
			public List<Cevap> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
				QueryResult<Cevap> qr = anketBaslikService.loadCevap(first, pageSize, filterBy, soru);
				setRowCount(qr.getToplamKayitSayisi().intValue());
				return qr.getListe();
			}
		};

		soruTipList = Arrays.asList(SoruTipEnum.values());
	}

	public void yeni() {
		this.anketBaslik = new AnketBaslik();
	}

	public void yeniSoru() {
		this.soru = new Soru();
	}

	public void yeniCevap() {
		this.cevap = new Cevap();
		this.cevap.setSoru(soru);
	}

	public void kaydet() {
		String id = this.anketBaslikService.kaydet(this.anketBaslik);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Baþarýlý Bir Þekilde Kaydedildi", "Kaydedilen id : " + id));
		PrimeFaces.current().executeScript("PF('yeniOverlay').hide()");
	}

	public void kaydetSoru() {
		this.anketBaslikService.kaydetSoru(this.anketBaslik, this.soru);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Baþarýlý Bir Þekilde Kaydedildi", null));
		PrimeFaces.current().executeScript("PF('yeniSoruOverlay').hide()");
	}

	public void kaydetCevap() {
		this.anketBaslikService.kaydetCevap(this.cevap);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Baþarýlý Bir Þekilde Kaydedildi", null));
		PrimeFaces.current().executeScript("PF('yeniCevapOverlay').hide()");
	}



}
