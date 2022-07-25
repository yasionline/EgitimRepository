package com.muzaffer.orun.application.converter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("request")
public class LocalDateTimeConverter implements Converter, Serializable {
	private static final long serialVersionUID = -6102386067428693718L;
	private static final String LOCALDATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private static final String LOCALDATE_FORMAT = "dd/MM/yyyy";


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof LocalDateTime) {
            return DateTimeFormatter.ofPattern(LOCALDATETIME_FORMAT).format((LocalDateTime) value);
        }
        if (value instanceof LocalDate) {
            return DateTimeFormatter.ofPattern(LOCALDATE_FORMAT).format((LocalDate) value);
        }
        return null;
    }
}
