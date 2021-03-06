/*******************************************************************************
 * Copyright 2012 Kim Herzig, Sascha Just
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/
package net.ownhero.dev.hiari.settings;

import net.ownhero.dev.hiari.settings.exceptions.ArgumentRegistrationException;
import net.ownhero.dev.hiari.settings.exceptions.ArgumentSetRegistrationException;
import net.ownhero.dev.hiari.settings.exceptions.SettingsParseError;

/**
 * The Interface SettingsProvider.
 * 
 * @author Sascha Just <sascha.just@st.cs.uni-saarland.de>
 */
public interface SettingsProvider {
	
	/**
	 * Inits the.
	 */
	void init();
	
	/**
	 * Provide.
	 * 
	 * @param root
	 *            the root
	 * @return the argument set
	 * @throws ArgumentRegistrationException
	 *             the argument registration exception
	 * @throws ArgumentSetRegistrationException
	 *             the argument set registration exception
	 * @throws SettingsParseError
	 *             the settings parse error
	 */
	ArgumentSet<?, ?> provide(ArgumentSet<?, ?> root) throws ArgumentRegistrationException,
	                                                 ArgumentSetRegistrationException,
	                                                 SettingsParseError;
}
