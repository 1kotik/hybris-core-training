/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package bands.controller;

import static bands.constants.BandsConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bands.service.BandsService;


@Controller
public class BandsHelloController
{
	@Autowired
	private BandsService bandsService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", bandsService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
