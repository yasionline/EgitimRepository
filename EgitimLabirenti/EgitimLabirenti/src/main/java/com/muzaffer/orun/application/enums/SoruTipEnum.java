package com.muzaffer.orun.application.enums;

public enum SoruTipEnum {

	COKDAN_SECMELI("�oktan Se�meli");

	private SoruTipEnum(String tanim) {
		this.tanim = tanim;
	}

	private String tanim;

	public String getTanim() {
		return tanim;
	}



}
