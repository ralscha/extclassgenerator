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

public class JsCacheKeyTest {

	@Test
	public void verifyEquals() {
		EqualsVerifier.forClass(JsCacheKey.class).verify();
	}

	@Test
	public void testPutAndGet() {
		JsCacheKey key1 = new JsCacheKey(new ModelBean(), null);

		OutputConfig config = new OutputConfig();
		config.setOutputFormat(OutputFormat.EXTJS4);
		JsCacheKey key2 = new JsCacheKey(new ModelBean(), config);

		ModelBean m = new ModelBean();
		m.setName("name");
		JsCacheKey key3 = new JsCacheKey(m, null);

		config = new OutputConfig();
		config.setOutputFormat(OutputFormat.TOUCH2);
		JsCacheKey key4 = new JsCacheKey(m, config);

		config = new OutputConfig();
		config.setOutputFormat(OutputFormat.EXTJS4);
		JsCacheKey key5 = new JsCacheKey(m, config);

		Map<JsCacheKey, String> map = new ConcurrentHashMap<>();
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
