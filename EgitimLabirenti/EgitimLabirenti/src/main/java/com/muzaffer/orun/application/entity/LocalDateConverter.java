package com.muzaffer.orun.application.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateConverter implements AttributeConverter<java.time.LocalDate, java.sql.Date> {
	@Override
	public java.sql.Date convertToDatabaseColumn(java.time.LocalDate entityValue) {
		return entityValue == null ? null : java.sql.Date.valueOf(entityValue);
	}

	@Override
	public java.time.LocalDate convertToEntityAttribute(java.sql.Date dbValue) {
		return dbValue == null ? null : dbValue.toLocalDate();
	}
}
