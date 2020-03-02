package cn.exrick.manager.service.impl;

import com.itranswarp.warpdb.WarpDb;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDbService extends AbstractService {
    /**
     * WarpDb instance.
     */
    @Autowired
    protected WarpDb db;
}
