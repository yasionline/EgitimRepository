package com.muzaffer.orun.application.service.impl;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muzaffer.orun.application.db.DbDao;
import com.muzaffer.orun.application.db.QueryResult;
import com.muzaffer.orun.application.entity.AnketBaslik;
import com.muzaffer.orun.application.entity.Cevap;
import com.muzaffer.orun.application.entity.Soru;
import com.muzaffer.orun.application.service.AnketBaslikService;

@Service
public class AnketBaslikServiceImpl implements AnketBaslikService {

	@Autowired
	private DbDao dbDao;


	@Override
	public QueryResult<AnketBaslik> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
		return dbDao.getLazyDataModel(AnketBaslik.class, " from AnketBaslik as t where 1=1 ", filterBy, sortField, sortOrder, first, pageSize);
	}

	@Override
	public String kaydet(AnketBaslik anketBaslik) {
		this.dbDao.kaydet(anketBaslik);
		return anketBaslik.getId();
	}

	@Override
	public QueryResult<Soru> loadSoru(int first, int pageSize, Map<String, FilterMeta> filterBy, AnketBaslik anketBaslik) {
		FilterMeta filterMeta = new FilterMeta("anketBaslik", anketBaslik);
		return dbDao.getLazyDataModel(Soru.class, " from Soru as t where 1=1 ", filterBy, "sira", SortOrder.ASCENDING, first, pageSize, filterMeta);
	}

	@Override
	public QueryResult<Cevap> loadCevap(int first, int pageSize, Map<String, FilterMeta> filterBy, Soru soru) {
		FilterMeta filterMeta = new FilterMeta("soru", soru);
		return dbDao.getLazyDataModel(Cevap.class, " from Cevap as t where 1=1 ", filterBy, "baslik", SortOrder.ASCENDING, first, pageSize, filterMeta);
	}

	@Override
	public void kaydetSoru(AnketBaslik anketBaslik, Soru soru) {
		List<Soru> liste = this.dbDao.getirListe(Soru.class, " from Soru as t where t.anketBaslik = :param_0 ", true, anketBaslik);
		soru.setAnketBaslik(anketBaslik);
		soru.setSira(liste.size() + 1);
		this.dbDao.kaydet(soru);
	}

	@Override
	public void kaydetCevap(Cevap cevap) {
		this.dbDao.kaydet(cevap);
	}

}
