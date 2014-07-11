package com.iiapk.rest.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("TX")
public class CcbRefundResult {
	
	@XStreamAlias("REQUEST_SN")
	private String orderNo;
	
	@XStreamAlias("CUST_ID")
	private String mechantNo;
	
	@XStreamAlias("TX_CODE")
	private String txCode;
	
	@XStreamAlias("RETURN_CODE")
	private String returnCode;
	
	@XStreamAlias("RETURN_MSG")
	private String returnMessage;
	
	@XStreamAlias("LANGUAGE")
	private String language;
	
	@XStreamAlias("TX_INFO")
	private CcbRefundResultInfo resultInfo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMechantNo() {
		return mechantNo;
	}

	public void setMechantNo(String mechantNo) {
		this.mechantNo = mechantNo;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public CcbRefundResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(CcbRefundResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

}
