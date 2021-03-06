package de.be4.classicalb.core.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CachingDefinitionFileProvider extends PlainFileContentProvider
		implements IDefinitionFileProvider {

	private final Map<String, Definitions> store = new HashMap<String, Definitions>();

	/**
	 * s. {@link PlainFileContentProvider#PlainFileContentProvider()}
	 */
	public CachingDefinitionFileProvider() {
		super();
	}

	/**
	 * s. {@link PlainFileContentProvider#PlainFileContentProvider(File)}
	 * 
	 * @param parentFile
	 */
	public CachingDefinitionFileProvider(final File parentFile) {
		super(parentFile);
	}

	public Definitions getDefinitions(final String filename) {
		return store.get(filename);
	}

	public void storeDefinition(final String filename,
			final Definitions definitions) {
		store.put(filename, definitions);
	}
}
