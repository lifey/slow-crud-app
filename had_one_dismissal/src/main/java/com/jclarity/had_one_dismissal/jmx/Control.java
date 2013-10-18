package com.jclarity.had_one_dismissal.jmx;

import org.springframework.stereotype.Component;

import com.jclarity.crud_common.api.ControlMXBean;
import com.jclarity.crud_common.jmx.JMXComponent;

@Component
public class Control extends JMXComponent implements ControlMXBean {
    
    public Control() throws Exception {
        register(ADDRESS);
    }

    @Override
    public void shutdown() {
        System.exit(0);
    }

}
