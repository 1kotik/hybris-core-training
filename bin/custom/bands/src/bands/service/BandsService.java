/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package bands.service;

public interface BandsService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}
