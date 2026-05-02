package com.brunobs.core.catalog.type.sharestatus;

import com.brunobs.shared.base.BaseEnum;

import java.util.List;

public enum ShareStatusTypeEnum implements BaseEnum<ShareStatusTypeEnum> {

    WAITING_DESTINATION_APPROVAL {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(APPROVED, REJECTED);
        }

        @Override
        public ShareActor allowedActor() {
            return ShareActor.DESTINATION;
        }
    },

    WAITING_SOURCE_APPROVAL {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(APPROVED, REJECTED);
        }

        @Override
        public ShareActor allowedActor() {
            return ShareActor.ORIGIN;
        }
    },

    APPROVED {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(CANCELLED);
        }

        @Override
        public ShareActor allowedActor() {
            return ShareActor.BOTH; // ambos podem cancelar
        }
    },

    REJECTED {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(); // terminal
        }
    },

    CANCELLED {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(); // terminal
        }
    },

    NOT_REQUESTED {
        @Override
        public List<ShareStatusTypeEnum> nextStatus() {
            return List.of(); // não transiciona
        }
    };

    public abstract List<ShareStatusTypeEnum> nextStatus();

    public ShareActor allowedActor() {
        return null; // default
    }

    public boolean canTransitionTo(ShareStatusTypeEnum nextStatus) {
        return nextStatus != null && nextStatus().contains(nextStatus);
    }

    public boolean isWaiting() {
        return this == WAITING_DESTINATION_APPROVAL
                || this == WAITING_SOURCE_APPROVAL;
    }

    // 👇 actor interno ao enum (boa prática)
    public enum ShareActor {
        ORIGIN,
        DESTINATION,
        BOTH
    }
}