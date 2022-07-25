package com.muzaffer.orun.application.db;

import java.io.Serializable;
import java.util.List;

public class QueryResult<T extends Serializable> {

	private List<T> liste;
	private Long toplamKayitSayisi;

	public QueryResult(List<T> liste, Long toplamKayitSayisi) {
		super();
		this.liste = liste;
		this.toplamKayitSayisi = toplamKayitSayisi;
	}

	public List<T> getListe() {
		return liste;
	}

	public Long getToplamKayitSayisi() {
		return toplamKayitSayisi;
	}

}
