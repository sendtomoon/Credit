package com.sendtomoon.credit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sendtomoon.credit.dto.BillInfoDTO;
import com.sendtomoon.credit.service.CreditService;

@Controller
public class BillsController {

	@Autowired
	private CreditService service;

	@RequestMapping("/index")
	@ResponseBody
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}

	@RequestMapping("/bills")
	@ResponseBody
	public ModelAndView bills() {
		ModelAndView mv = new ModelAndView();
		List<BillInfoDTO> list = service.bills();
		mv.addObject("bills", list);
		mv.setViewName("bills");
		return mv;
	}

	@RequestMapping("/payday")
	@ResponseBody
	public ModelAndView payday() {
		ModelAndView mv = new ModelAndView();
		List<BillInfoDTO> list = service.payday();
		mv.addObject("payday", list);
		mv.setViewName("payday");
		return mv;
	}

	@RequestMapping("/freeday")
	@ResponseBody
	public ModelAndView freeday() {
		ModelAndView mv = new ModelAndView();
		List<BillInfoDTO> list = service.freeday();
		mv.addObject("bills", list);
		mv.setViewName("freeday");
		return mv;
	}
}
