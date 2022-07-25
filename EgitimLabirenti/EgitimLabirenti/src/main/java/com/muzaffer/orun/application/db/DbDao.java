package com.muzaffer.orun.application.db;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DbDao {

	@Autowired
	SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public <T extends Serializable> QueryResult<T> getLazyDataModel(Class<T> clazz, String sorgu, Map<String, FilterMeta> filterBy, String sortField, SortOrder sortOrder, int first, int pageSize, FilterMeta... filterMetas) {

		StringBuilder sb = new StringBuilder(sorgu);

		for (Map.Entry<String, FilterMeta> deger : filterBy.entrySet()) {
			sb.append(" and ( upper(lower(upper(t.").append(deger.getKey()).append("))) like upper(lower(upper(").append(" :").append(deger.getKey()).append("))) or ").append(" :").append(deger.getKey()).append(" is null ) ");
		}

		if(Objects.nonNull(filterMetas)) {
			for(FilterMeta filterMeta : filterMetas) {
				sb.append(" and ( upper(lower(upper(t.").append(filterMeta.getFilterField()).append("))) like upper(lower(upper(").append(" :").append(filterMeta.getFilterField()).append("))) or ").append(" :").append(filterMeta.getFilterField()).append(" is null ) ");
			}
		}

		String order = "";
		if (sortField != null && !"".equals(sortField)) {
			order = " order by t." + sortField + (sortOrder.equals(SortOrder.ASCENDING) ? " asc" : " desc");
		}
		Query<Long> queryTotal = getCurrentSession().createQuery("select count(*) " + sb.toString(), Long.class);
		Query<T> queryList = getCurrentSession().createQuery(sb.toString() + order, clazz);
		for (Map.Entry<String, FilterMeta> deger : filterBy.entrySet()) {
			if (deger.getValue().getFilterValue() instanceof String) {
				queryTotal.setParameter(deger.getKey(), "%" + deger.getValue().getFilterValue() + "%");
				queryList.setParameter(deger.getKey(), "%" + deger.getValue().getFilterValue() + "%");
			} else {
				queryTotal.setParameter(deger.getKey(), deger.getValue().getFilterValue());
				queryList.setParameter(deger.getKey(), deger.getValue().getFilterValue());
			}
		}
		if(Objects.nonNull(filterMetas)) {
			for(FilterMeta filterMeta : filterMetas) {
				queryTotal.setParameter(filterMeta.getFilterField(), filterMeta.getFilterValue());
				queryList.setParameter(filterMeta.getFilterField(), filterMeta.getFilterValue());
			}
		}

		queryList.setMaxResults(pageSize);
		queryList.setFirstResult(first * pageSize);
		return new QueryResult<>(queryList.list(), queryTotal.getSingleResult());
	}

	public <T extends Serializable> void kaydet(T t) {
		this.getCurrentSession().saveOrUpdate(t);
	}

	public <T extends Serializable, R extends Serializable> T getir(Class<T> clazz, String sorgu, @SuppressWarnings("unchecked") R... parametreler) {
		Query<T> q = this.getCurrentSession().createQuery(sorgu, clazz);
		if (parametreler != null) {
			int i = 0;
			for (R r : parametreler) {
				q.setParameter("param_" + i, r);
				i++;
			}

		}
		return q.uniqueResult();
	}

	public <T extends Serializable, R extends Serializable> List<T> getirListe(Class<T> clazz, String sorgu, boolean maxKayit, @SuppressWarnings("unchecked") R... parametreler) {
		Query<T> q = this.getCurrentSession().createQuery(sorgu, clazz);
		if (parametreler != null) {
			int i = 0;
			for (R r : parametreler) {
				q.setParameter("param_" + i, r);
				i++;
			}

		}
		if(!maxKayit) {
			q.setMaxResults(10);
		}
		return q.list();

	}

	public <T extends Serializable> T getirId(Class<T> clazz, Long id) {
		return this.getCurrentSession().find(clazz, id);
	}

	public <T> void sil(T t) {
		this.getCurrentSession().delete(t);
	}

}
