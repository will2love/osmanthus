package com.github.wei86609.osmanthus.translator;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.wei86609.osmanthus.node.Flow;
import com.github.wei86609.osmanthus.node.Node;
import com.github.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;

public class XmlFileNodeTranslator extends NodeTranslator{

    private String flowFolder;

    private String flowFolderName="flow";

    private String flowFileExtension=".xml";
    
    private String ruleSetFolder;

    private String ruleSetFolderName="rule";

    private String ruleFileExtension=".xml";

    public String getFlowFolder() {
        return flowFolder;
    }

    public void setFlowFolder(String flowFolder) {
        this.flowFolder = flowFolder;
    }

    public String getFlowFolderName() {
        return flowFolderName;
    }

    public void setFlowFolderName(String flowFolderName) {
        this.flowFolderName = flowFolderName;
    }

    public String getFlowFileExtension() {
        return flowFileExtension;
    }

    public void setFlowFileExtension(String flowFileExtension) {
        this.flowFileExtension = flowFileExtension;
    }

    public String getRuleSetFolder() {
        return ruleSetFolder;
    }

    public void setRuleSetFolder(String ruleSetFolder) {
        this.ruleSetFolder = ruleSetFolder;
    }

    public String getRuleSetFolderName() {
        return ruleSetFolderName;
    }

    public void setRuleSetFolderName(String ruleSetFolderName) {
        this.ruleSetFolderName = ruleSetFolderName;
    }

    public String getRuleFileExtension() {
        return ruleFileExtension;
    }

    public void setRuleFileExtension(String ruleFileExtension) {
        this.ruleFileExtension = ruleFileExtension;
    }

    @Override
    public Map<String, Flow> getFlows() throws Exception {
        Map<String,Flow> flowMaps=new HashMap<String,Flow>();
        File[] rsf = findFile(flowFolder,flowFolderName,flowFileExtension);
        for(int i=0;i<rsf.length;i++){
            Flow flow=xml2Node(rsf[i]);
            if(flow!=null){
                flow.init();
                flowMaps.put(flow.getId(), flow);
            }
        }
        return flowMaps;
    }

    private File[] findFile(final String folder,final String folderName,final String fileExtension) {
        File file=null;
        if(StringUtils.isEmpty(folder)){
            URL url=GeneralRuleSetExecutor.class.getClassLoader().getResource(folderName);
            file = new File(url.getFile());
        }else{
            file=new File(folder);
        }
        return file.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name) {
                return name.endsWith(fileExtension);
            }
        });
    }

    @Override
    public Flow getFlowById(String flowId) throws Exception {
        return null;
    }

    @Override
    public Node getExternalNodeById(String nodeId) throws Exception {
        return null;
    }

    @Override
    public Map<String, Node> getExternalNodes() throws Exception {
        Map<String,Node> ruleMaps=new HashMap<String,Node>();
        File[] rsf = findFile(ruleSetFolder,ruleSetFolderName,ruleFileExtension);
        for(int i=0;i<rsf.length;i++){
            Node node=xml2Rule(rsf[i]);
            if(node!=null){
                ruleMaps.put(node.getId(), node);
            }
        }
        return ruleMaps;
    }

}
