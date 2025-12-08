/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package users.service;

public interface UsersService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
