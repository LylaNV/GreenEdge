package com.github.lylanv.greenedge.events;

public class ApplicationStartedEvent {
    private boolean applicationStarted;

    public ApplicationStartedEvent(final boolean applicationStarted) {
        this.applicationStarted = applicationStarted;
    }

    public boolean getApplicationStarted() {
        return applicationStarted;
    }
}
