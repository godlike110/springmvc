package com.wei.springmvc.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.devops.send.Sender;
import com.scgj.message.model.SmsMessage;
import com.scgj.message.service.SmsSender;

/**
 * test
 * @author zhiweiwen
 *
 */
@Controller
public class HomeController {
	
	@Autowired
	SmsSender sender;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/normalmsg", method = RequestMethod.GET)
	@ResponseBody
	public String normal(Locale locale, Model model) throws IOException {		
		SmsMessage sms = new SmsMessage();
		sms.setContent("您的验证码是1234【云片网】");
		sms.setId(1);
		sms.setReceivePhone("13021027911");
		sms.setChannel("wap");
		sms.setCreateTime(new Date().getTime());
		//同步发送 普通短信
		sender.sendMessageSyn(sms);
		return "done!";		
	}
	
	@RequestMapping(value = "/tplSend", method = RequestMethod.GET)
	@ResponseBody
	public String tplSend(Locale locale, Model model) throws IOException {		
		SmsMessage sms = new SmsMessage();
		sms.setTemplateId(1l);
		sms.setTemplateValue("#code#=1234&#company#=人人网");
		sms.setId(1);
		sms.setReceivePhone("13021027911");
		sms.setChannel("app");
		sms.setCreateTime(new Date().getTime());
		//异步发送模板短信
		sender.sendMessageAsy(sms);
		return "done!";		
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}
