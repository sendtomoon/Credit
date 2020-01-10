package com.sendtomoon.credit.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.sendtomoon.credit.DateHelper;
import com.sendtomoon.credit.DateTimeUtils;
import com.sendtomoon.credit.dao.CreditDAO;
import com.sendtomoon.credit.dto.BillInfoDTO;
import com.sendtomoon.credit.dto.CardDTO;
import com.sendtomoon.credit.dto.CardListDTO;

@Component
public class CreditService {

	@Autowired
	CardListDTO list;

	@Autowired
	CreditDAO dao;

	public HashMap<String, Object> getCharts() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<CardDTO> cards = dao.findAll();
		List<String> nameList = new ArrayList<String>();
		for (CardDTO card : cards) {
			nameList.add(card.getNickname());
		}
		map.put("cards", nameList.toArray());
		return map;
	}

	public List<BillInfoDTO> freeday() {
		List<CardDTO> cards = dao.findAll();
		List<BillInfoDTO> bills = new ArrayList<BillInfoDTO>();
		for (CardDTO card : cards) {
			BillInfoDTO billDTO = new BillInfoDTO();
			billDTO.setNickName(card.getNickname());
			Integer billDay = Integer.valueOf(card.getBillDate());
			Integer nowDay = Integer.valueOf(this.getNowDay());
			Calendar cal = Calendar.getInstance();
			String daysCount = "";
			if (nowDay > billDay) {// 本月已出账单(当前日大于账单日)
				cal.add(Calendar.MONTH, 1);

			} else if (nowDay < billDay) {// 本月未出账单(当前日小于账单日)
				cal.set(Calendar.DAY_OF_MONTH, billDay);
				daysCount = this.calcDays(cal.getTime());
			} else {
				daysCount = "-1";
			}

			Date billDate = DateTimeUtils.indentDate(cal.getTime());
			Date repayDate = this.getRepayDate(card.getRepayDayFixed(), card.getRepaymentDays(), billDate);
			daysCount = this.calcDays(repayDate);
			billDTO.setFinalRepayDate(DateHelper.date2Str(repayDate, DateHelper.DATE_FMT_SHORT_CN));

			billDTO.setOrder(Integer.valueOf(daysCount));
			billDTO.setDescribe("最长免息日" + daysCount + "天");
			bills.add(billDTO);
		}
		Collections.sort(bills, new Comparator<BillInfoDTO>() {
			@Override
			public int compare(BillInfoDTO o1, BillInfoDTO o2) {
				try {
					return (o1.getOrder() - o2.getOrder()) * -1;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		return bills;
	}

	public List<BillInfoDTO> bills() {
		List<CardDTO> cards = dao.findAll();
		List<BillInfoDTO> bills = new ArrayList<BillInfoDTO>();
		for (CardDTO card : cards) {
			BillInfoDTO billDTO = new BillInfoDTO();
			billDTO.setNickName(card.getNickname());

			Integer billDay = Integer.valueOf(card.getBillDate());
			Integer nowDay = Integer.valueOf(this.getNowDay());
			Calendar cal = Calendar.getInstance();
			String daysCount = "";
			if (nowDay > billDay) {// 本月已出账单(当前日大于账单日)
				cal.add(Calendar.MONTH, 1);
				cal.set(Calendar.DAY_OF_MONTH, billDay);
				daysCount = this.calcDays(cal.getTime());
			} else if (nowDay < billDay) {// 本月未出账单(当前日小于账单日)
				cal.set(Calendar.DAY_OF_MONTH, billDay);
				daysCount = this.calcDays(cal.getTime());
			} else {
				daysCount = "-1";
			}
			billDTO.setOrder(Integer.valueOf(daysCount));
			billDTO.setDescribe("距下次账单日" + daysCount + "天");
			bills.add(billDTO);
		}
		Collections.sort(bills, new Comparator<BillInfoDTO>() {
			@Override
			public int compare(BillInfoDTO o1, BillInfoDTO o2) {
				try {
					return (o1.getOrder() - o2.getOrder()) * -1;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		return bills;
	}

	public List<BillInfoDTO> payday() {
		List<CardDTO> cards = dao.findAll();
		List<BillInfoDTO> bills = new ArrayList<BillInfoDTO>();
		for (CardDTO card : cards) {
			BillInfoDTO billDTO = new BillInfoDTO();
			billDTO.setNickName(card.getNickname());
			Integer billDay = Integer.valueOf(card.getBillDate());
			Integer nowDay = Integer.valueOf(this.getNowDay());
			if (nowDay > billDay) {// 本月已出账单(当前日大于账单日)
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, billDay);
				Date billDate = DateTimeUtils.indentDate(cal.getTime());
				Date repayDate = this.getRepayDate(card.getRepayDayFixed(), card.getRepaymentDays(), billDate);
				billDTO.setBillDate(DateHelper.date2Str(billDate, DateHelper.DATE_FMT_SHORT_CN));
				billDTO.setFinalRepayDate(DateHelper.date2Str(repayDate, DateHelper.DATE_FMT_SHORT_CN));
				billDTO.setType("0");
				billDTO.setDescribe("距最后还款日" + this.calcDays(repayDate) + "天");
				billDTO.setOrder(Integer.valueOf(this.calcDays(repayDate)));
				billDTO.setNextBillDate(nextBillDate(billDay));
				billDTO.setFinalRepayDays(this.calcDays(repayDate));
			}

			if (nowDay < billDay) {// 本月未出账单(当前日小于账单日)
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DAY_OF_MONTH, billDay);
				cal.add(Calendar.MONTH, -1);
				Date billDate = DateTimeUtils.indentDate(cal.getTime());// 上月账单date
				Date repayDate = this.getRepayDate(card.getRepayDayFixed(), card.getRepaymentDays(), billDate);// 上月账单的最后还款日
				Calendar repayCal = Calendar.getInstance();
				repayCal.setTime(repayDate);
				if (DateTimeUtils.indentDate(new Date()).getTime() < DateTimeUtils.indentDate(repayDate).getTime()) {// 上次还款日在今天之后，说明最后还款日还没到
					billDTO.setBillDate(DateHelper.date2Str(billDate, DateHelper.DATE_FMT_SHORT_CN));
					billDTO.setFinalRepayDate(DateHelper.date2Str(repayDate, DateHelper.DATE_FMT_SHORT_CN));
					billDTO.setType("1");
					billDTO.setDescribe("距最后还款日" + this.calcDays(repayDate) + "天");
					billDTO.setOrder(Integer.valueOf(this.calcDays(repayDate)));
					billDTO.setFinalRepayDays(this.calcDays(repayDate));
					billDTO.setOrder(Integer.valueOf(this.calcDays(repayDate)));
				} else if (DateTimeUtils.indentDate(new Date()).getTime() > DateTimeUtils.indentDate(repayDate)
						.getTime()) {// 上次还款日在今天之前
					Calendar billCal = Calendar.getInstance();
					billCal.set(Calendar.DAY_OF_MONTH, billDay);
					billDTO.setBillDate(DateHelper.date2Str(billCal.getTime(), DateHelper.DATE_FMT_SHORT_CN));
					billDTO.setFinalRepayDate("");
					billDTO.setType("2");
					billDTO.setDescribe("已过最后还款日");
					billDTO.setOrder(-2);
					billDTO.setFinalRepayDays("");
				} else {// 今天就是还款日
					billDTO.setOrder(-1);
					billDTO.setDescribe("今天是还款日");
				}
			}

			if (nowDay == billDay) {
				billDTO.setOrder(999);
				billDTO.setDescribe("今天是账单日");
			}

			bills.add(billDTO);
		}
		Collections.sort(bills, new Comparator<BillInfoDTO>() {
			@Override
			public int compare(BillInfoDTO o1, BillInfoDTO o2) {
				try {
					return o1.getOrder() - o2.getOrder();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		return bills;
	}

	private String getNowDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(new Date());
	}

	private Date getRepayDate(String repayDayFixed, String repayDay, Date billDate) {
		if (repayDayFixed.equals("0")) {// 固定日期
			Calendar billCalendar = Calendar.getInstance();
			billCalendar.setTime(billDate);
			if (billCalendar.get(Calendar.DAY_OF_MONTH) > Integer.valueOf(repayDay)) {// 账单日在还款日之后
				if (this.isCommonMonth(billCalendar)) {// 账单日在当前月
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(repayDay));
					calendar.add(Calendar.MONTH, +1);
					return DateTimeUtils.indentDate(calendar.getTime());
				} else {// 账单日不在当前月
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(repayDay));
					return DateTimeUtils.indentDate(calendar.getTime());
				}
			} else {// 账单日在还款日之前，极少出现，仍然覆盖
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(repayDay));
				return DateTimeUtils.indentDate(calendar.getTime());
			}
		}

		if (repayDayFixed.equals("1")) {// 浮动日期
			Calendar cal = Calendar.getInstance();
			cal.setTime(billDate);
			cal.add(Calendar.DATE, Integer.valueOf(repayDay));
			return DateTimeUtils.indentDate(cal.getTime());
		}
		return null;
	}

	/** 计算今天到最后还款日天数 */
	private String calcDays(Date finalDate) {
		long d1 = DateTimeUtils.indentDate(finalDate).getTime();
		long d2 = DateTimeUtils.indentDate(new Date()).getTime();
		long days = (d1 - d2) / 60 / 60 / 24 / 1000l;
		return String.valueOf(days);
	}

	private String nextBillDate(Integer billDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(billDate));
		calendar.add(Calendar.MONTH, +1);
		return DateHelper.date2Str(calendar.getTime(), DateHelper.DATE_FMT_SHORT_CN);
	}

	private boolean isCommonMonth(Calendar compareCal) {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH) == compareCal.get(Calendar.MONTH);
	}

}
