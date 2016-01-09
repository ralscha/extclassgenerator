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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ModelCacheKeyTest {

	@Test
	public void verifyEquals() {
		EqualsVerifier.forClass(ModelCacheKey.class).verify();
	}

	@Test
	public void testPutAndGet() {
		ModelCacheKey key1 = new ModelCacheKey(null, null);
		ModelCacheKey key2 = new ModelCacheKey("name", null);

		OutputConfig outputConfig1 = new OutputConfig();
		outputConfig1.setIncludeValidation(IncludeValidation.ALL);
		ModelCacheKey key3 = new ModelCacheKey(null, outputConfig1);

		OutputConfig outputConfig2 = new OutputConfig();
		outputConfig2.setIncludeValidation(IncludeValidation.BUILTIN);
		ModelCacheKey key4 = new ModelCacheKey("name", outputConfig2);

		OutputConfig outputConfig3 = new OutputConfig();
		outputConfig3.setIncludeValidation(IncludeValidation.NONE);
		ModelCacheKey key5 = new ModelCacheKey("name", outputConfig3);

		Map<ModelCacheKey, String> map = new ConcurrentHashMap<>();
		map.put(key1, "one");
		map.put(key2, "two");
		map.put(key3, "three");
		map.put(key4, "four");

		assertThat(map.get(key5)).isNull();
		assertThat(map.get(key1)).isEqualTo("one");
		assertThat(map.get(key2)).isEqualTo("two");
		assertThat(map.get(key3)).isEqualTo("three");
		assertThat(map.get(key4)).isEqualTo("four");
	}
}
