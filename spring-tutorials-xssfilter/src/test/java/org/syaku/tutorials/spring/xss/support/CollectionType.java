package org.syaku.tutorials.spring.xss.support;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 25.
 */
@ToString
@Data
public class CollectionType<T, K, V> {
	private List<T> list;
	private Set<T> se;
	private Map<K,V> map;
	private T[] array;
}
