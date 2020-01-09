package com.sendtomoon.credit.dto;

public class BillInfoDTO {

	
	private String id;
	private String cardName;
	/**0:本月已出账单 1：本月未出账单，未过最后还款日 2：本月未出账单，已过最后还款日*/
	private String type;
	/**最后还款日*/
	private String finalRepayDate;
	/**距最后还款天数*/
	private String finalRepayDays;
	/**账单日*/
	private String billDate;
	/**顺序*/
	private Integer order;
	
	private String nickName;
	
	private String describe;
	
	private String nextBillDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFinalRepayDate() {
		return finalRepayDate;
	}
	public void setFinalRepayDate(String finalRepayDate) {
		this.finalRepayDate = finalRepayDate;
	}
	public String getFinalRepayDays() {
		return finalRepayDays;
	}
	public void setFinalRepayDays(String finalRepayDays) {
		this.finalRepayDays = finalRepayDays;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getNextBillDate() {
		return nextBillDate;
	}
	public void setNextBillDate(String nextBillDate) {
		this.nextBillDate = nextBillDate;
	}
}
