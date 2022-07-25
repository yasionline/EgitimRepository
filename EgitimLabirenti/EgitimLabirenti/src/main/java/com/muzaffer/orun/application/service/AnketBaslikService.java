package com.muzaffer.orun.application.service;

import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortOrder;

import com.muzaffer.orun.application.db.QueryResult;
import com.muzaffer.orun.application.entity.AnketBaslik;
import com.muzaffer.orun.application.entity.Cevap;
import com.muzaffer.orun.application.entity.Soru;

public interface AnketBaslikService {

	QueryResult<AnketBaslik> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy);

	String kaydet(AnketBaslik anketBaslik);

	QueryResult<Soru> loadSoru(int first, int pageSize, Map<String, FilterMeta> filterBy, AnketBaslik anketBaslik);

	void kaydetSoru(AnketBaslik anketBaslik, Soru soru);

	QueryResult<Cevap> loadCevap(int first, int pageSize, Map<String, FilterMeta> filterBy, Soru soru);

	void kaydetCevap(Cevap cevap);

}
