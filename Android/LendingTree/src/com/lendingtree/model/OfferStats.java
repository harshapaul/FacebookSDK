package com.lendingtree.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("OfferStats")
public class OfferStats implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
