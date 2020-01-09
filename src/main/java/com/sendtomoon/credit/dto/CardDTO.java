package com.sendtomoon.credit.dto;

import org.springframework.stereotype.Component;

@Component
public class CardDTO {
	private String id;
	private String billDate;
	private String repaymentDays;
	private String repayDayFixed;
	private String nickname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getRepaymentDays() {
		return repaymentDays;
	}

	public void setRepaymentDays(String repaymentDays) {
		this.repaymentDays = repaymentDays;
	}

	public String getRepayDayFixed() {
		return repayDayFixed;
	}

	public void setRepayDayFixed(String repayDayFixed) {
		this.repayDayFixed = repayDayFixed;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
}
