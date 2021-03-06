package com.github.wei86609.osmanthus.rule.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import com.github.wei86609.osmanthus.event.Event;
import com.github.wei86609.osmanthus.event.ParallelEventListener;
import com.github.wei86609.osmanthus.rule.Line;
import com.github.wei86609.osmanthus.rule.Parallel;
import com.github.wei86609.osmanthus.rule.Rule;
import com.github.wei86609.osmanthus.rule.Rule.TYPE;

public class ParallelRuleExecutor implements RuleExecutor{

    private ParallelEventListener parallelEventListener;

    private ExecutorService threadPool;

    public String execute(Event event, Rule rule) throws Exception {
        Parallel parallel=(Parallel)rule;
        List<Line> lines=parallel.getLines();
        ArrayList<FutureTask<String>> tasks=new ArrayList<FutureTask<String>>();
        ArrayList<Event> newEvents=new ArrayList<Event>();
        for(Line line:lines){
            Event newEvent=event.copy();
            tasks.add(executeLine(newEvent,line));
            newEvents.add(newEvent);
        }
        //if is sync, need to wait all threads run its event complete.
        if(parallel.isSync()){
            for(FutureTask<String> task:tasks){
                task.get(parallel.getTimeout(), TimeUnit.SECONDS);
            }
            for(Event eve:newEvents){
                event.addSubEvent(eve);
            }
            return rule.getToRuleId();
        }
        return null;
    }

    private FutureTask<String> executeLine(final Event event,final Line line){
        FutureTask<String> task=new FutureTask<String>(new Callable<String>(){
            public String call() throws Exception {
                if(parallelEventListener!=null){
                    parallelEventListener.startNewEvent(event, line.getToRuleId());
                    return "success";
                }
                return "failure";
            }
        });
        if(threadPool!=null){
            threadPool.submit(task);
        }
        return task;
    }

    public TYPE getType() {
        return TYPE.PARALLEL;
    }

    public void stop(){
        if(threadPool!=null){
            threadPool.shutdown();
        }
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public ParallelEventListener getParallelEventListener() {
        return parallelEventListener;
    }

    public void setParallelEventListener(ParallelEventListener parallelEventListener) {
        this.parallelEventListener = parallelEventListener;
    }

}
