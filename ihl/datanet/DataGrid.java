package ihl.datanet;

import ihl.interfaces.IDataNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DataGrid 
{
	public final Set<IDataNode> telist = new HashSet();
	public double energy=0D;
	private IDataNode sink;
	private IDataNode source;
	private double voltage;
	private short frequency=0;
	private double lastVoltage;
	public boolean isGridValid=true;
	private double total20TicksEU;
	private int lastTickCounter=0;
	
	public Set<IDataNode> getListOfExcludedNodes()
	{
		Set<IDataNode> excludedNodes = new HashSet();
		if(this.telist.isEmpty())
		{
			return excludedNodes;
		}
		excludedNodes.addAll(this.telist);
		IDataNode startNode = this.telist.iterator().next();
		this.recursiveGridNodesSearch(excludedNodes, startNode);
		this.telist.removeAll(excludedNodes);
		return excludedNodes;
	}
	
	public void recursiveGridNodesSearch(Set<IDataNode> excludedNodes, IDataNode startNode)
	{
		Iterator<IDataNode> nodei = startNode.getConnectedDataNodes().iterator();
		excludedNodes.remove(startNode);
		while(nodei.hasNext())
		{
			IDataNode node = nodei.next();
			if(excludedNodes.contains(node))
			{
				recursiveGridNodesSearch(excludedNodes, node);
			}
		}
	}

	public boolean isConnectedToContact(Contact emitter, int id) 
	{
		Iterator<IDataNode> it1 = this.telist.iterator();
		while(it1.hasNext())
		{
			IDataNode ate1 = it1.next();
			if(ate1.getInnerId()==id)
			{
				return true;
			}
			else if(emitter!=ate1 && ate1.isIndirectlyConnectedWithContact(id))
			{
				return true;
			}
		}
		return false;
	}
}