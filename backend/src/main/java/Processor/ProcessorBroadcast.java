package Processor;

import DTO.BroadcastDTO;

public interface ProcessorBroadcast {
    public interface Processor {
        public BroadcastDTO process();
    }
}
