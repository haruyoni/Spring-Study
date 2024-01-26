package com.miniproj.persistence;

import com.miniproj.domain.PointLog;

public interface PointLogDAO {
	int insertPointLog(PointLog pl) throws Exception;
}
