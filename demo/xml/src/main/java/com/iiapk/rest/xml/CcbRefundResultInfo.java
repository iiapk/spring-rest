package com.iiapk.rest.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("TX_INFO")
public class CcbRefundResultInfo {
	
	@XStreamAlias("ORDER_NUM")
	private String orginalOrderNo;

	@XStreamAlias("PAY_AMOUNT")
	private String orginalAmount;
	
	@XStreamAlias("AMOUNT")
	private String refundAmount;
	
	@XStreamOmitField
	private String REM1;
	
	@XStreamOmitField
	private String REM2;

	public String getOrginalOrderNo() {
		return orginalOrderNo;
	}

	public void setOrginalOrderNo(String orginalOrderNo) {
		this.orginalOrderNo = orginalOrderNo;
	}

	public String getOrginalAmount() {
		return orginalAmount;
	}

	public void setOrginalAmount(String orginalAmount) {
		this.orginalAmount = orginalAmount;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

}
