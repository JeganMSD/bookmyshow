package com.jrorg.bookmyshow.manager;

import java.util.List;

import com.jrorg.bookmyshow.entity.BaseEntity;
import com.jrorg.bookmyshow.request.BaseRequest;

public interface BaseManager<K extends BaseEntity,V extends BaseRequest> {
	public List<K> getList(int page_no,int limit)throws Exception;
	public K create(V v) throws Exception;
	public List<K> batchCreate(List<V> v) throws Exception;
	public K show(V v) throws Exception;
	public K update(V v) throws Exception;
	public void delete(long id) throws Exception;
}
