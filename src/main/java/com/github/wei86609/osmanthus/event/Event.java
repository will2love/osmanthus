package com.github.wei86609.osmanthus.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Event {

    public enum MODEL{
        FIRST,LAST,WEIGHT
    }

    private final Map<String, Object> variables = new HashMap<String, Object>();

    private final List<Event> subEvents=new ArrayList<Event>();

    private String eventId;

    private String flowId;

    private String flowName;

    private long startTime;

    private long endTime;

    private MODEL model=MODEL.FIRST;

    private boolean error;

    private String currentRuleId;

    public List<Event> getSubEvents() {
        return subEvents;
    }

    public void addSubEvent(Event subEvent){
        subEvents.add(subEvent);
    }

    public boolean canRunNextNode(){
        return (MODEL.FIRST.equals(model) && error)?false:true;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public MODEL getModel() {
        return model;
    }

    public void setModel(MODEL model) {
        this.model = model;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void addVar(String key, Object value) {
        if (!StringUtils.isEmpty(key)) {
            variables.put(key, value);
        }
    }

    public Object getVar(String key) {
        if (!StringUtils.isEmpty(key)) {
            return variables.get(key);
        }
        return null;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public String getCurrentRuleId() {
        return currentRuleId;
    }

    public void setCurrentRuleId(String currentRuleId) {
        this.currentRuleId = currentRuleId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    @Override
    public String toString() {
        return "Event [variables=" + variables + ", subEvents=" + subEvents
                + ", eventId=" + eventId + ", flowId=" + flowId + ", flowName="
                + flowName + ", startTime=" + startTime + ", endTime="
                + endTime + ", model=" + model + ", error=" + error
                + ", currentRuleId=" + currentRuleId + "]";
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Event copy(){
        Event event=new Event();
        event.setEventId(this.getEventId());
        event.setModel(this.getModel());
        event.setFlowId(this.getFlowId());
        event.setCurrentRuleId(this.getCurrentRuleId());
        event.setStartTime(this.startTime);
        event.setEndTime(this.endTime);
        event.setFlowName(this.flowName);
        event.getVariables().putAll(this.getVariables());
        return event;
    }
}
