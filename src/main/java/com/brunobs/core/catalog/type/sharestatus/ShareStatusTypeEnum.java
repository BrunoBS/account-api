package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.shared.base.BaseEnum;

import java.util.List;


public enum ShareStatusTypeEnum implements BaseEnum<ShareStatusTypeEnum> {

    WAITING_DESTINATION_APPROVAL {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(APPROVED, REJECTED);
        }
    },

    APPROVED {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(WAITING_SOURCE_APPROVAL);
        }
    },

    REJECTED {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(WAITING_SOURCE_APPROVAL);
        }
    },

    WAITING_SOURCE_APPROVAL {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(APPROVED, REJECTED);
        }
    },

    NOT_REQUESTED {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of();
        }
    };


    public abstract List<ShareStatusTypeEnum> nextStatus();

    public boolean canTransitionTo(ShareStatusTypeEnum nextStatus) {
        return nextStatus().contains(nextStatus);
    }
}
