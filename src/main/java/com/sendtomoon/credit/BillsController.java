package com.sendtomoon.credit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@RequestMapping("/charts")
	@ResponseBody
	public HashMap<String, Object> charts() {
		HashMap<String, Object> map = service.getCharts();
		return map;
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

	@RequestMapping("/down")
	public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
		File file = new File("123.zip");
		byte[] body = null;
		InputStream is = new FileInputStream(file);
		body = new byte[is.available()];
		is.read(body);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement;filename=" + file.getName());
		HttpStatus statusCode = HttpStatus.OK;

		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
		return entity;
	}
}
