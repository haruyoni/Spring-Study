package com.miniproj.persistence;

import com.miniproj.domain.PointLog;

public interface PointLogDAO {
	void insertPointLog(PointLog pl) throws Exception;
}
