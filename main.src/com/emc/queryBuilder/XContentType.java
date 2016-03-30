package com.emc.queryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public enum XContentType {

	/**
	 * A JSON based content type.
	 */
	JSON(0) {
		@Override
		protected String mediaTypeWithoutParameters() {
			return "application/json";
		}

		@Override
		public String mediaType() {
			return "application/json; charset=UTF-8";
		}

		@Override
		public String shortName() {
			return "json";
		}

		@Override
		public XContent xContent() {
			return JsonXContent.jsonXContent;
		}
	};

	public static XContentType fromMediaTypeOrFormat(String mediaType) {
		if (mediaType == null) {
			return null;
		}
		for (XContentType type : values()) {
			if (isSameMediaTypeAs(mediaType, type)) {
				return type;
			}
		}
		if (mediaType.toLowerCase(Locale.ROOT).startsWith("application/*")) {
			return JSON;
		}

		return null;
	}

	private static boolean isSameMediaTypeAs(String stringType,
			XContentType type) {
		return type.mediaTypeWithoutParameters().equalsIgnoreCase(stringType)
				|| stringType.toLowerCase(Locale.ROOT).startsWith(
						type.mediaTypeWithoutParameters().toLowerCase(
								Locale.ROOT)
								+ ";")
				|| type.shortName().equalsIgnoreCase(stringType);
	}

	private int index;

	XContentType(int index) {
		this.index = index;
	}

	public int index() {
		return index;
	}

	public String mediaType() {
		return mediaTypeWithoutParameters();
	}

	public abstract String shortName();

	public abstract XContent xContent();

	protected abstract String mediaTypeWithoutParameters();

	public static XContentType readFrom(InputStream in) throws IOException {
		int index = in.read();
		for (XContentType contentType : values()) {
			if (index == contentType.index) {
				return contentType;
			}
		}
		throw new IllegalStateException("Unknown XContentType with index ["
				+ index + "]");
	}

	public static void writeTo(XContentType contentType, OutputStream out)
			throws IOException {
		// out.writeVInt(contentType.index);
		out.write(contentType.index);
	}

}
