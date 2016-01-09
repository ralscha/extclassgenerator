/**
 * Copyright 2013-2016 Ralph Schaer <ralphschaer@gmail.com>
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
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.debug ? 1231 : 1237);
		result = prime * result + (this.includeValidation == null ? 0
				: this.includeValidation.hashCode());
		result = prime * result
				+ (this.lineEnding == null ? 0 : this.lineEnding.hashCode());
		result = prime * result
				+ (this.outputFormat == null ? 0 : this.outputFormat.hashCode());
		result = prime * result + (this.surroundApiWithQuotes ? 1231 : 1237);
		result = prime * result + (this.useSingleQuotes ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OutputConfig other = (OutputConfig) obj;
		if (this.debug != other.debug) {
			return false;
		}
		if (this.includeValidation != other.includeValidation) {
			return false;
		}
		if (this.lineEnding != other.lineEnding) {
			return false;
		}
		if (this.outputFormat != other.outputFormat) {
			return false;
		}
		if (this.surroundApiWithQuotes != other.surroundApiWithQuotes) {
			return false;
		}
		if (this.useSingleQuotes != other.useSingleQuotes) {
			return false;
		}
		return true;
	}

}
