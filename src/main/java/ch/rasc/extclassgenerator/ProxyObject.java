/**
 * Copyright the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.extclassgenerator;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@SuppressWarnings("unused")
public class ProxyObject {

	private final String type = "direct";

	private String idParam;

	private Object pageParam = null;

	private Object startParam = null;

	private Object limitParam = null;

	private String directFn;

	private ApiObject api;

	private Map<String, Object> reader;

	private Map<String, Object> writer;

	protected ProxyObject(ModelBean model, OutputConfig config) {
		if (StringUtils.hasText(model.getIdProperty())
				&& !model.getIdProperty().equals("id")) {
			this.idParam = model.getIdProperty();
		}

		if (model.isDisablePagingParameters()) {
			Object value;
			if (config.getOutputFormat() == OutputFormat.EXTJS4) {
				value = "undefined";
			}
			else if (config.getOutputFormat() == OutputFormat.EXTJS5) {
				value = "";
			}
			else {
				value = Boolean.FALSE;
			}
			this.pageParam = value;
			this.startParam = value;
			this.limitParam = value;
		}

		Map<String, Object> readerConfigObject = new LinkedHashMap<String, Object>();

		if (StringUtils.hasText(model.getReader()) && !"json".equals(model.getReader())) {
			readerConfigObject.put("type", model.getReader());
		}

		String rootPropertyName = config.getOutputFormat() == OutputFormat.EXTJS4 ? "root"
				: "rootProperty";

		if (StringUtils.hasText(model.getRootProperty())) {
			readerConfigObject.put(rootPropertyName, model.getRootProperty());
		}
		else if (model.isPaging()) {
			readerConfigObject.put(rootPropertyName, "records");
		}

		if (StringUtils.hasText(model.getMessageProperty())) {
			readerConfigObject.put("messageProperty", model.getMessageProperty());
		}

		if (StringUtils.hasText(model.getTotalProperty())) {
			readerConfigObject.put("totalProperty", model.getTotalProperty());
		}

		if (StringUtils.hasText(model.getSuccessProperty())) {
			readerConfigObject.put("successProperty", model.getSuccessProperty());
		}

		if (!readerConfigObject.isEmpty()) {
			this.reader = readerConfigObject;
		}

		Map<String, Object> writerConfigObject = new LinkedHashMap<String, Object>();

		if (StringUtils.hasText(model.getWriter()) && !"json".equals(model.getWriter())) {
			writerConfigObject.put("type", model.getWriter());
		}

		if (model.getWriteAllFields() != null && (config
				.getOutputFormat() == OutputFormat.EXTJS5 && model.getWriteAllFields()
				|| !model.getWriteAllFields()
						&& (config.getOutputFormat() == OutputFormat.EXTJS4
								|| config.getOutputFormat() == OutputFormat.TOUCH2))) {
			writerConfigObject.put("writeAllFields", model.getWriteAllFields());
		}

		if (config.getOutputFormat() == OutputFormat.EXTJS5
				&& model.isClientIdPropertyAddToWriter()
				&& StringUtils.hasText(model.getClientIdProperty())) {
			writerConfigObject.put("clientIdProperty", model.getClientIdProperty());
		}

		if (config.getOutputFormat() == OutputFormat.EXTJS5) {
			if (model.getAllDataOptions() != null
					&& model.getAllDataOptions().hasAnyProperties()) {
				writerConfigObject.put("allDataOptions", model.getAllDataOptions());
			}

			if (model.getPartialDataOptions() != null
					&& model.getPartialDataOptions().hasAnyProperties()) {
				writerConfigObject.put("partialDataOptions",
						model.getPartialDataOptions());
			}
		}

		if (!writerConfigObject.isEmpty()) {
			this.writer = writerConfigObject;
		}

		boolean hasApiMethods = false;
		ApiObject apiObject = new ApiObject();

		if (StringUtils.hasText(model.getCreateMethod())) {
			hasApiMethods = true;
			apiObject.create = model.getCreateMethod();
		}
		if (StringUtils.hasText(model.getUpdateMethod())) {
			hasApiMethods = true;
			apiObject.update = model.getUpdateMethod();
		}
		if (StringUtils.hasText(model.getDestroyMethod())) {
			hasApiMethods = true;
			apiObject.destroy = model.getDestroyMethod();
		}

		if (StringUtils.hasText(model.getReadMethod())) {
			if (hasApiMethods) {
				apiObject.read = model.getReadMethod();
			}
			else {
				this.directFn = model.getReadMethod();
			}
		}

		if (hasApiMethods) {
			this.api = apiObject;
		}
	}

	public boolean hasContent() {
		return this.api != null || this.directFn != null || this.reader != null
				|| this.writer != null && !(this.writer.size() == 1
						&& this.writer.containsKey("writeAllFields"))
				|| this.idParam != null;
	}
}
