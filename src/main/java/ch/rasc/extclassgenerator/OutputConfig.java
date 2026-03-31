/*
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

import java.util.Objects;

/**
 * Class to configure the output of the {@link ModelGenerator}
 */
public class OutputConfig {

	private IncludeValidation includeValidation;

	private OutputFormat outputFormat;

	private boolean debug;

	private boolean useSingleQuotes;

	private boolean surroundApiWithQuotes;

	private LineEnding lineEnding;

	public IncludeValidation getIncludeValidation() {
		return this.includeValidation;
	}

	public void setIncludeValidation(IncludeValidation includeValidation) {
		this.includeValidation = includeValidation;
	}

	public OutputFormat getOutputFormat() {
		return this.outputFormat;
	}

	public void setOutputFormat(OutputFormat outputFormat) {
		this.outputFormat = outputFormat;
	}

	public boolean isDebug() {
		return this.debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isUseSingleQuotes() {
		return this.useSingleQuotes;
	}

	public void setUseSingleQuotes(boolean useSingleQuotes) {
		this.useSingleQuotes = useSingleQuotes;
	}

	public boolean isSurroundApiWithQuotes() {
		return this.surroundApiWithQuotes;
	}

	public void setSurroundApiWithQuotes(boolean surroundApiWithQuotes) {
		this.surroundApiWithQuotes = surroundApiWithQuotes;
	}

	public LineEnding getLineEnding() {
		return this.lineEnding;
	}

	public void setLineEnding(LineEnding lineEnding) {
		this.lineEnding = lineEnding;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.debug, this.includeValidation, this.lineEnding,
				this.outputFormat, this.surroundApiWithQuotes, this.useSingleQuotes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		OutputConfig other = (OutputConfig) obj;
		if (this.debug != other.debug || this.includeValidation != other.includeValidation
				|| this.lineEnding != other.lineEnding
				|| this.outputFormat != other.outputFormat) {
			return false;
		}
		if ((this.surroundApiWithQuotes != other.surroundApiWithQuotes) || (this.useSingleQuotes != other.useSingleQuotes)) {
			return false;
		}
		return true;
	}

}
