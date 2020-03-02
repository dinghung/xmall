package cn.exrick.manager.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService {
    /**
     * Logger instance for access logging system.
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
