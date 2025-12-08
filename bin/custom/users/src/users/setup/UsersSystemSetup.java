/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package users.setup;

import static users.constants.UsersConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import users.constants.UsersConstants;
import users.service.UsersService;


@SystemSetup(extension = UsersConstants.EXTENSIONNAME)
public class UsersSystemSetup
{
	private final UsersService usersService;

	public UsersSystemSetup(final UsersService usersService)
	{
		this.usersService = usersService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		usersService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return UsersSystemSetup.class.getResourceAsStream("/users/sap-hybris-platform.png");
	}
}
