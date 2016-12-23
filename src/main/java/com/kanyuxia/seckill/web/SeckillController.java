package com.kanyuxia.seckill.web;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kanyuxia.seckill.dto.Exposer;
import com.kanyuxia.seckill.dto.SeckillExecution;
import com.kanyuxia.seckill.dto.SeckillResult;
import com.kanyuxia.seckill.entity.Seckill;
import com.kanyuxia.seckill.enums.SeckillStateEnum;
import com.kanyuxia.seckill.exception.RepeatKillException;
import com.kanyuxia.seckill.exception.SeckillCloseException;
import com.kanyuxia.seckill.exception.SeckillException;
import com.kanyuxia.seckill.service.SeckillService;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		// 获取列表页
		List<Seckill> list = seckillService.getSeckillList();
		// ModelAndView = model + list.jsp
		model.addAttribute("list", list);
		return "list";
	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "redirect:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}

	// 从cookie中取值:使用@CookieValue(value="",required = false) 
	// 其中required默认为true.若cookie中没有则抛出异常,所以required设置为false.
	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5, @CookieValue(value = "userPhone", required = false) Long userPhone) {
		// 也可以使用SpringMVC valid方式验证
		if(userPhone == null){
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			System.out.println("seckillExecution"+seckillExecution);
		}catch (RepeatKillException e) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		}catch (SeckillCloseException e) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		}catch (SeckillException e) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<SeckillExecution>(false, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> getSystemTime(){
		Date date = new Date();
		return new SeckillResult<Long>(true, date.getTime());
	}

}
