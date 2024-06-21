package com.github.lylanv.greenedge.events;

public class BuildSuccessEvent {
    private boolean buildSuccessful;

    public BuildSuccessEvent(final boolean buildSuccessful) {
        this.buildSuccessful = buildSuccessful;
    }

    public boolean getBuildStatus() {
        return buildSuccessful;
    }
}
