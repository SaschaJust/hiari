/***********************************************************************************************************************
 * Copyright 2011 Kim Herzig, Sascha Just
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 **********************************************************************************************************************/
package net.ownhero.dev.hiari.settings;

import java.util.Collection;
import java.util.Properties;

import net.ownhero.dev.hiari.settings.exceptions.ArgumentRegistrationException;
import net.ownhero.dev.hiari.settings.exceptions.ArgumentSetRegistrationException;
import net.ownhero.dev.hiari.settings.exceptions.SettingsParseError;
import net.ownhero.dev.kanuni.annotations.simple.NotNull;

/**
 * The Interface ISettings.
 * 
 * @author Sascha Just <sascha.just@st.cs.uni-saarland.de>
 */
public interface ISettings {
	
	/**
	 * Adds the tool information.
	 * 
	 * @param tool
	 *            the tool
	 * @param information
	 *            the information
	 */
	void addInformation(final String tool,
	                    final String information);
	
	/**
	 * Adds the option.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param <X>
	 *            the generic type
	 * @param <Y>
	 *            the generic type
	 * @param options
	 *            the options
	 * @throws ArgumentRegistrationException
	 *             the argument registration exception
	 * @throws ArgumentSetRegistrationException
	 *             the argument set registration exception
	 */
	<T, X extends IArgument<T, Y>, Y extends IOptions<T, X>> void addOption(@NotNull final Y options) throws ArgumentRegistrationException,
	                                                                                                 ArgumentSetRegistrationException;
	
	/**
	 * Gets the anchor.
	 * 
	 * @param argumentSetTag
	 *            the argument set tag
	 * @return the anchor
	 */
	ArgumentSet<?, ?> getAnchor(final String argumentSetTag);
	
	/**
	 * Gets the argument.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param <X>
	 *            the generic type
	 * @param <Y>
	 *            the generic type
	 * @param argument
	 *            the argument
	 * @return the argument
	 */
	<T, X extends ArgumentOptions<T, Y>, Y extends Argument<T, X>> Y getArgument(final IArgumentOptions<T, Y> argument);
	
	/**
	 * Gets the argument set.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param <X>
	 *            the generic type
	 * @param <Y>
	 *            the generic type
	 * @param option
	 *            the option
	 * @return the argument set
	 */
	<T, X extends ArgumentSetOptions<T, Y>, Y extends ArgumentSet<T, X>> Y getArgumentSet(final IArgumentSetOptions<T, Y> option);
	
	/**
	 * Gets the bug report argument.
	 * 
	 * @return the bug report argument
	 */
	StringArgument getBugReportArgument();
	
	/**
	 * Gets the deny default balues tag.
	 * 
	 * @return the deny default balues tag
	 */
	String getDenyDefaultValuesTag();
	
	/**
	 * Gets the handle.
	 * 
	 * @return the handle
	 */
	String getHandle();
	
	/**
	 * Return the help string that will contain all possible command line arguments.
	 * 
	 * @return the help string
	 */
	String getHelpString();
	
	/**
	 * Gets the tool information.
	 * 
	 * @return the tool information
	 */
	String getInformation();
	
	/**
	 * Gets the logger arguments.
	 * 
	 * @return the logger arguments
	 */
	ArgumentSet<Boolean, LoggerOptions> getLoggerArguments();
	
	/**
	 * Gets the mail arguments.
	 * 
	 * @return the mail arguments
	 */
	ArgumentSet<Properties, MailOptions> getMailArguments();
	
	/**
	 * Gets the no default value arg.
	 * 
	 * @return the no default value arg
	 */
	BooleanArgument getNoDefaultValueArg();
	
	/**
	 * Gets the property.
	 * 
	 * @param name
	 *            the name
	 * @return the property
	 */
	String getProperty(String name);
	
	/**
	 * Gets the raw argument set.
	 * 
	 * @param option
	 *            the option
	 * @return the raw argument set
	 */
	@SuppressWarnings ("rawtypes")
	ArgumentSet getRawArgumentSet(final IArgumentSetOptions option);
	
	/**
	 * Gets the root argument set.
	 * 
	 * @return the rootArgumentSet
	 */
	ArgumentSet<Boolean, ?> getRoot();
	
	/**
	 * @param klass
	 * @return some T
	 */
	@SuppressWarnings ("rawtypes")
	<T extends ArgumentSetOptions> T getSetOptions(final Class<T> klass);
	
	/**
	 * Checks for setting.
	 * 
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	boolean hasSetting(final String name);
	
	/**
	 * Help requested.
	 * 
	 * @return true, if successful
	 */
	public boolean helpRequested();
	
	/**
	 * Checks if is crash email disabled.
	 * 
	 * @return true, if is crash email disabled
	 */
	boolean isCrashEmailEnabled();
	
	/**
	 * Load by class.
	 * 
	 * @param providerClass
	 *            the provider class
	 * @param anchorSet
	 *            the anchor set
	 * @return the argument set
	 * @throws ArgumentRegistrationException
	 *             the argument registration exception
	 * @throws ArgumentSetRegistrationException
	 *             the argument set registration exception
	 * @throws SettingsParseError
	 *             the settings parse error
	 */
	ArgumentSet<?, ?> loadByClass(final Class<? extends SettingsProvider> providerClass,
	                              final ArgumentSet<?, ?> anchorSet) throws ArgumentRegistrationException,
	                                                                ArgumentSetRegistrationException,
	                                                                SettingsParseError;
	
	/**
	 * Load by entity.
	 * 
	 * @param provider
	 *            the provider
	 * @param anchorSet
	 *            the anchor set
	 * @return the argument set
	 * @throws ArgumentRegistrationException
	 *             the argument registration exception
	 * @throws ArgumentSetRegistrationException
	 *             the argument set registration exception
	 * @throws SettingsParseError
	 *             the settings parse error
	 */
	ArgumentSet<?, ?> loadByEntity(final SettingsProvider provider,
	                               final ArgumentSet<?, ?> anchorSet) throws ArgumentRegistrationException,
	                                                                 ArgumentSetRegistrationException,
	                                                                 SettingsParseError;
	
	/**
	 * Load by inheritance.
	 * 
	 * @param pakkage
	 *            the pakkage
	 * @param anchorSet
	 *            the anchor set
	 * @return the collection
	 * @throws ArgumentRegistrationException
	 *             the argument registration exception
	 * @throws ArgumentSetRegistrationException
	 *             the argument set registration exception
	 * @throws SettingsParseError
	 *             the settings parse error
	 */
	Collection<ArgumentSet<?, ?>> loadByInheritance(final Package pakkage,
	                                                final ArgumentSet<?, ?> anchorSet) throws ArgumentRegistrationException,
	                                                                                  ArgumentSetRegistrationException,
	                                                                                  SettingsParseError;
	
}
