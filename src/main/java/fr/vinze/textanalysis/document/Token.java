package fr.vinze.textanalysis.document;

import java.util.Set;

public interface Token {

	Set<Metadata> getMetadata();
	
	public static interface Metadata {}
}
