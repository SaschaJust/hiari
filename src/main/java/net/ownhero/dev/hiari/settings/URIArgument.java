/*******************************************************************************
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
 ******************************************************************************/
package net.ownhero.dev.hiari.settings;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import net.ownhero.dev.hiari.settings.exceptions.ArgumentRegistrationException;
import net.ownhero.dev.hiari.settings.requirements.Requirement;
import net.ownhero.dev.kanuni.annotations.simple.NotNull;
import net.ownhero.dev.kanuni.annotations.string.NotEmptyString;
import net.ownhero.dev.kisa.Logger;
import net.ownhero.dev.regex.Regex;

/**
 * The Class URIArgument.
 * 
 * @author Kim Herzig <herzig@cs.uni-saarland.de>
 */
public class URIArgument extends Argument<URI, URIArgument.Options> {
	
	/**
	 * The Class Options.
	 */
	public static class Options extends ArgumentOptions<URI, URIArgument> {
		
		/**
		 * Instantiates a new options.
		 * 
		 * @param argumentSet
		 *            the argument set
		 * @param name
		 *            the name
		 * @param description
		 *            the description
		 * @param defaultValue
		 *            the default value
		 * @param requirements
		 *            the requirements
		 */
		public Options(@NotNull final ArgumentSet<?, ?> argumentSet, @NotNull @NotEmptyString final String name,
		        @NotNull @NotEmptyString final String description, final URI defaultValue,
		        @NotNull final Requirement requirements) {
			super(argumentSet, name, description, defaultValue, requirements);
		}
		
	}
	
	/**
	 * This is similar to FileArgument but requires the file to be a directory.
	 * 
	 * @param options
	 *            the options
	 * @throws ArgumentRegistrationException
	 *             the argument registration exception
	 * @see de.unisaarland.cs.st.reposuite.settings.RepoSuiteArgument
	 */
	protected URIArgument(@NotNull final Options options) throws ArgumentRegistrationException {
		super(options);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.ownhero.dev.andama.settings.AndamaArgument#init()
	 */
	@Override
	protected final boolean init() {
		boolean ret = false;
		
		try {
			if (!validStringValue()) {
				if (required()) {
					if (Logger.logError()) {
						Logger.error("Argument required but doesn't have a valid string value (from options '%s').",
						             getOptions());
					}
				} else {
					if (Logger.logWarn()) {
						Logger.warn("Optional argument is not set: %s", getTag());
					}
					setCachedValue(null);
					ret = true;
				}
			} else {
				
				URI uri = null;
				// see RFC2396
				// String lowalpha = "a-z";
				// String upalpha = "A-Z";
				// String alpha = lowalpha + upalpha;
				// String digit = "0-9";
				// String alphanum = alpha + digit;
				// String reserved = ";/?:@&=+$,";
				// String mark = "-_.!Z*'()";
				// String unreserved = alphanum + mark;
				// String hex = digit + "A-Fa-f";
				// String escaped = "%" + "[" + hex + "]{2}";
				// String delims = "<>#%\"";
				// String unwise = "{}|\\\\^\\[\\]`";
				//
				// String reg_name = "[" + unreserved + escaped +
				// "$,;:@&=+]+";
				// String userinfo = unreserved + escaped +
				// ";:&=+$,";
				// String port = "[" + digit + "]*";
				// String domainlabel = "([" + alphanum + "]|[" +
				// alphanum +
				// "][" +
				// alphanum + "-]*["+ alphanum + "])";
				// String toplabel = "([" + alpha + "]|[" + alpha +
				// "]["
				// +
				// alphanum +
				// "-]*["+ alphanum + "])";
				// String hostname = "(" + domainlabel + "\\.)*" +
				// toplabel+
				// "\\.?";
				// String ipv4address = "[" + digit + "]+\\." + "["
				// +
				// digit
				// + "]+\\." +
				// "[" + digit + "]+\\." + "[" + digit + "]+";
				// String host = "({host}" + hostname + "|" +
				// ipv4address +
				// ")";
				// String hostport = host + "(:" + port +")?";
				// String server = "[" + userinfo +"]?" + hostport;
				//
				// String authority = "({authority}" + server +
				// reg_name
				// +
				// ")";
				// String scheme = "({scheme}[" + alpha + "]" + "["
				// +
				// alpha
				// + digit +
				// "+-." + "])";
				// String path = "({path}" + abs_path + "|" +
				// opaque_part +
				// ")";
				// String query = "({query})";
				//
				// Regex uriRegex = new Regex(scheme + ":"
				// +authority+path+"\\?" +query
				// );
				
				String err = null;
				final Regex uriRegex = new Regex(
				                                 "^(({scheme}[^:/?#]+):)?(//({authority}[^/?#]*))?({path}[^?#]*)(\\?({query}[^#]*))?(#({fragment}.*))?");
				try {
					if (uriRegex.find(getStringValue()) == null) {
						err = "URI does not match regex: " + uriRegex.getPattern();
					} else {
						if (uriRegex.getGroup("scheme") == null) {
							
							if (Logger.logWarn()) {
								Logger.warn("Scheme missing when parsing URI:" + getStringValue()
								        + " Guessing scheme: file.");
							}
							
							if (uriRegex.getGroup("authority") == null) {
								if (uriRegex.getGroup("path") != null) {
									// guess file
									final File file = new File(uriRegex.getGroup("path"));
									if (file.exists() && file.canRead()) {
										
										if (Logger.logInfo()) {
											Logger.info("Found readable " + (file.isDirectory()
											                                                   ? "directory"
											                                                   : "file")
											        + " at location: " + file.getAbsolutePath());
										}
										uri = new URI("file", null, file.getAbsolutePath(), null);
									} else {
										err = "Local path does not reference an existing, readable file/dir: "
										        + file.getAbsolutePath();
									}
								} else {
									err = "`path` part of the URI is not set: " + getStringValue();
								}
							} else {
								err = "`authority` part of the URI is set, but scheme is missing: " + getStringValue();
							}
						} else {
							uri = new URI(getStringValue());
						}
					}
					
					if (err != null) {
						if (Logger.logError()) {
							Logger.error("When parsing URI string `" + getStringValue() + "` for argument `"
							        + getName() + "`, the following error occurred: " + err);
						}
					} else {
						setCachedValue(uri);
						ret = true;
					}
				} catch (final URISyntaxException e) {
					if (Logger.logError()) {
						Logger.error("When parsing URI string `" + getStringValue() + "` for argument `" + getName()
						        + "`, the following error occurred: " + e.getMessage());
					}
				}
			}
			
			return ret;
		} finally {
			__initPostCondition(ret);
		}
	}
}
